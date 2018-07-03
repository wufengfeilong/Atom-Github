package sdwxwx.com.letter.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.View;
import cn.emay.sdk.util.json.gson.Gson;
import cn.emay.sdk.util.json.gson.reflect.TypeToken;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import sdwxwx.com.R;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.bean.UserBean;
import sdwxwx.com.databinding.ActivityLetterSettingBinding;
import sdwxwx.com.letter.contract.LetterSettingContract;
import sdwxwx.com.letter.presenter.LetterSettingPresenter;
import sdwxwx.com.login.model.LoginModel;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.util.StringUtil;

import java.util.ArrayList;
import java.util.List;


public class LetterSettingActivity extends BaseActivity<ActivityLetterSettingBinding, LetterSettingPresenter> implements LetterSettingContract.View {
    //粉丝的用户id
    private String memberId;
    //粉丝的环信id
    private String emId;
    private boolean flag_DND = false;
    List<String> mlist = new ArrayList<>();

    @Override
    protected LetterSettingPresenter createPresenter() {
        return new LetterSettingPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        isOn();
        setSettingListener();
        // 调用获取会员的详情信息接口
        memberId = getIntent().getStringExtra("target_member_id");
        emId = getIntent().getStringExtra("target_em_id");
        LoginModel.getMemberInfo(LoginHelper.getInstance().getUserId(), memberId, new BaseCallback<UserBean>() {
            // 如果成功则更新画面标识内容
            @Override
            public void onSuccess(UserBean data) {
                // 头像设定
                if (StringUtil.isNotEmpty(data.getAvatar_url())) {
                    RequestOptions options = new RequestOptions().error(R.drawable.default_header_image);
                    Glide.with(getContext()).load(data.getAvatar_url()).apply(options).into(
                            mDataBinding.letterSettingHead);
                }
                // 昵称设定
                mDataBinding.letterNickName.setText(data.getNickname());
            }

            // 如果错误，则提示错误信息
            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });
        defaultDND();
    }


    //面打扰开关切换逻辑
    public void isOn() {
        mDataBinding.letterSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果DNDlist没有数据 把当前用户存入
                if (isDND().size() == 0) {
                    mDataBinding.letterSwitch.setImageResource(R.drawable.letter_switch_btn_on);
                    //保存当前面打扰用户id到sp
                    saveDND();

                } else {
                    //如果DNDlist有数据 开始匹配当前用户
                    for (int i = 0; i < isDND().size(); i++) {
                        //匹配到面打扰用户 flag设置为true；
                        if ((memberId.equals(isDND().get(i)))) {
                            flag_DND = true;
                        }
                    }
                    if (flag_DND) {
                        // 匹配到免打扰用户 点击后开关切换为关闭
                        mDataBinding.letterSwitch.setImageResource(R.drawable.letter_switch_btn_off);
                        //从sp移除当前用户id
                        deleteDND();
                    } else {
                        // 非面打扰用户 点击后开关切换为开启
                        mDataBinding.letterSwitch.setImageResource(R.drawable.letter_switch_btn_on);
                        //保存当前面打扰用户id到sp
                        saveDND();
                    }

                }
            }
        });

    }

    //init 面打扰开关状态
    public void defaultDND() {
        //如果DNDlist没有存入id 默认设置开关关闭
        if (isDND().size() == 0) {
            mDataBinding.letterSwitch.setImageResource(R.drawable.letter_switch_btn_off);
        } else {
            matchDNDUser();
        }
    }

    //匹配免打扰用户 并设置开关状态
    public void matchDNDUser() {
        for (int i = 0; i < isDND().size(); i++) {
            //匹配到面打扰用户 flag设置为true；
            if ((memberId.equals(isDND().get(i)))) {
                flag_DND = true;
            }
        }
        if (flag_DND)
            mDataBinding.letterSwitch.setImageResource(R.drawable.letter_switch_btn_on);
        else
            mDataBinding.letterSwitch.setImageResource(R.drawable.letter_switch_btn_off);
    }

    //获取设置了免打扰用户id 的list
    public List isDND() {
        List<String> DND_List = new ArrayList<>();
        SharedPreferences sp = getSharedPreferences("SP_DND_List", Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
        String DNDListJson = sp.getString("KEY_DND_LIST_DATA", "");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
        if (DNDListJson != "")  //防空判断
        {
            Gson gson = new Gson();
            DND_List = gson.fromJson(DNDListJson, new TypeToken<List<String>>() {
            }.getType()); //将json字符串转换成List集合
        }
        return DND_List;
    }

    public void saveDND() {
        //把设置面打扰的用户id存入本地list
        SharedPreferences sp = getSharedPreferences("SP_DND_List", Activity.MODE_PRIVATE);//创建sp对象
        mlist.add(memberId);
        Gson gson = new Gson();
        String jsonDND = gson.toJson(mlist); //将List转换成Json
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("KEY_DND_LIST_DATA", jsonDND); //存入json串
        editor.commit();  //提交
    }

    public void deleteDND() {
        List<String> DND_List = new ArrayList<>();
        SharedPreferences sp = getSharedPreferences("SP_DND_List", Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
        String DNDListJson = sp.getString("KEY_DND_LIST_DATA", "");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
        if (DNDListJson != "")  //防空判断
        {
            Gson gson = new Gson();
            DND_List = gson.fromJson(DNDListJson, new TypeToken<List<String>>() {
            }.getType()); //将json字符串转换成List集合

            for (int i = 0; i < DND_List.size(); i++) {
                //匹配到面打扰用户 在list中移除此项
                if ((memberId.equals(DND_List.get(i)))) {
                    DND_List.remove(i);
                    String jsonStr = gson.toJson(DND_List); //4.将删除完的List转换成Json
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("KEY_DND_LIST_DATA", jsonStr); //存入json串
                    editor.commit();  //提交
                }
            }
        }

    }


    public void setSettingListener() {
        //删除对话
        mDataBinding.deleteConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(LetterSettingActivity.this);
                builder.setMessage("确定删除对话?");
                builder.setTitle("提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除和某个user会话，保留聊天记录，传false
                        EMClient.getInstance().chatManager().deleteConversation(emId, true);
//                        //发送广播 重新获取记录 刷新显示
                        Intent intent = new Intent("com.sdwxwx.broadcast.close_chat");
                        intent.putExtra("removeMemberId",memberId);
                        sendBroadcast(intent);
                        finish();
                    }
                });

                //添加AlertDialog.Builder对象的setNegativeButton()方法
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }
                );
                builder.create().show();


            }
        });
        //清空消息记录，不删除对话
        mDataBinding.emptyConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LetterSettingActivity.this);
                builder.setMessage("确定清空聊天记录?");
                builder.setTitle("提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除和某个user会话，保留聊天记录，传false
                        EMClient.getInstance().chatManager().deleteConversation(emId, true);
                        EMMessage message = EMMessage.createTxtSendMessage(" ", emId);
                        message.setChatType(EMMessage.ChatType.Chat);
                        // 增加自己特定的属性
                        message.setAttribute("memberId", LoginHelper.getInstance().getUserId());
                        message.setAttribute("toMemberId", memberId);
                        //发送消息
                        EMClient.getInstance().chatManager().sendMessage(message);
                        finish();
                        //跳转回聊天页面
                       actionStartActivity(LetterChatActivity.class);
                    }
                });

                //添加AlertDialog.Builder对象的setNegativeButton()方法
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }
                );
                builder.create().show();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_letter_setting;
    }

    @Override
    public void finishThis() {
        finish();
    }

    /**
     * 获取会员ID
     *
     * @return
     */
    public String getMemberId() {
        return memberId;
    }
}
