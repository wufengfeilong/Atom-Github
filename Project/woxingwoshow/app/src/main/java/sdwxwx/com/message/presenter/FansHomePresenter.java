package sdwxwx.com.message.presenter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.bean.UserBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.home.bean.CityEntity;
import sdwxwx.com.home.bean.PlayVideoBean;
import sdwxwx.com.home.model.HomeCategoryVideoModel;
import sdwxwx.com.home.model.PickCityModel;
import sdwxwx.com.letter.activity.LetterChatActivity;
import sdwxwx.com.login.model.LoginModel;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.message.activity.MessageFriendFansListActivity;
import sdwxwx.com.message.activity.MessageMyTeamActivity;
import sdwxwx.com.message.activity.MessageWealthActivity;
import sdwxwx.com.message.contract.FansHomeContract;
import sdwxwx.com.message.model.FansHomeModel;
import sdwxwx.com.play.activity.PlayVideoActivity;
import sdwxwx.com.play.model.PlayVideoFragmentModel;
import sdwxwx.com.util.LoginUtil;

import java.util.List;

/**
 * Created by 860117073 on 2018/5/16.
 */

public class FansHomePresenter extends BasePresenter<FansHomeContract.View> implements FansHomeContract.Presenter {
    LoginModel mModel;
    FansHomeModel meModel;
    PlayVideoFragmentModel nModel;
    HomeCategoryVideoModel mfansModel;
    /** 弹窗 */
    private AlertDialog.Builder mDialog;

    /**
     * 画面初期化获得会员详情
     */
    @Override
    public void loadListData(String page) {
        mfansModel.getVideoList(LoginHelper.getInstance().getUserId(),
                "0", "0","0",getView().getMemberId(),page, new BaseCallback<List<PlayVideoBean>>() {
                    @Override
                    public void onSuccess(List<PlayVideoBean> data) {
                        if (getView() == null) {
                            return;
                        }
                        getView().bindListData(data);
                    }

                    @Override
                    public void onFail(String msg) {
                        if (getView() == null) {
                            return;
                        }
                        getView().showToast(msg);
                        getView().getSpringView().onFinishFreshAndLoad();
                        Intent intent = new Intent("com.sdwxwx.load.video.list.end");
                        getView().getContext().sendBroadcast(intent);
                    }
                });
        //调用获取会员信息接口
        mModel.getMemberInfo(LoginUtil.getStrUserInfo(Constant.SP_MEMBER_ID, getView().getContext()), getView().getMemberId(), new BaseCallback<UserBean>() {
            @Override
            public void onSuccess(final UserBean bean) {
                // 调用城市接口，取得所有城市
                PickCityModel.getCities(new BaseCallback<List<CityEntity>>() {
                    @Override
                    public void onSuccess(List<CityEntity> data) {
                        if (!TextUtils.isEmpty(bean.getCity_id()) && !"0".equals(bean.getCity_id()) && data != null && data.size() > 0) {
                            for (CityEntity cityEntity : data) {
                                if (String.valueOf(cityEntity.getId()).equals(bean.getCity_id())) {
                                    bean.setCity_id(cityEntity.getName());
                                    break;
                                }
                            }
                        } else {
                            bean.setCity_id("未知城市");
                        }
                        if (getView() == null) {
                            return;
                        }
                        getView().bindFansInfor(bean);
                    }
                    @Override
                    public void onFail(String msg) {
                        if (getView() == null) {
                            return;
                        }
                        bean.setCity_id("未知城市");
                        getView().bindFansInfor(bean);
                    }
                });
            }
            @Override
            public void onFail(String msg) {
                if (getView() == null) {
                    return;
                }
                getView().showToast(msg);
            }
        });
    }

    /**
     * 关注会员
     */
    @Override
    public void getAttention() {
        getView().showLoading();
        nModel.followUser(getView().getContext(),LoginHelper.getInstance().getUserId(),getView().getMemberId(), new BaseCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (getView() == null) {
                    return;
                }
                //关注成功后的操作
                //关注按钮隐藏，取消关注按钮显示
                getView().getAttention().setVisibility(View.GONE);
                getView().cancelAttention().setVisibility(View.VISIBLE);
                getView().hideLoading();

            }

            @Override
            public void onFail(String msg) {
                if (getView() == null) {
                    return;
                }
                getView().showToast(msg);
                getView().hideLoading();
            }
        });
    }

    /**
     * 解除关注关系
     */
    @Override
    public void cancelAttention() {
        mDialog = new AlertDialog.Builder(getView().getContext());
        mDialog.setMessage("确定不再关注此人?");
        mDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 调用清除好友接口
                        getView().showLoading();
                        meModel.cancelFollow(getView().getContext(),LoginUtil.getStrUserInfo(Constant.SP_MEMBER_ID, getView().getContext()),getView().getMemberId(), new BaseCallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                if (getView() == null) {
                                    return;
                                }
                                //取消关注成功的操作
                                //关注按钮显示，取消关注按钮隐藏
                                getView().getAttention().setVisibility(View.VISIBLE);
                                getView().cancelAttention().setVisibility(View.GONE);
                                getView().hideLoading();
                            }
                            @Override
                            public void onFail(String msg) {
                                if (getView() == null) {
                                    return;
                                }
                                getView().showToast(msg);
                                getView().hideLoading();
                            }
                        });
                    }
                });
        mDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 不做任何处理
                    }
                });
        // 显示
        mDialog.show();
    }

    /**
     * 返回
     */
    public void onBack() {
        // 关闭当前的Activity，返回上一个画面
        getView().closeActivity();
    }

    // 点击进行分享
    @Override
    public void onClickFansShare() {
        getView().onShare();
    }

    /**
     * 跳转到关注列表
     */
    @Override
    public void toFansAttention() {
        Intent intent = new Intent();
        // 跳转到好友资料画面
        intent.setClass(getView().getContext(), MessageFriendFansListActivity.class);
        // 传递类型
        intent.putExtra("type", "2");
        // 传递会员ID
        intent.putExtra("member_id", getView().getMemberId());
        getView().getContext().startActivity(intent);
    }

    /**
     * 跳转到粉丝列表
     */
    @Override
    public void toFansList() {
        Intent intent = new Intent();
        // 跳转到好友资料画面
        intent.setClass(getView().getContext(), MessageFriendFansListActivity.class);
        // 传递类型
        intent.putExtra("type", "1");
        // 传递会员ID
        intent.putExtra("member_id", getView().getMemberId());
        getView().getContext().startActivity(intent);
    }

    /**
     * 跳转到财富值
     */
    @Override
    public void toMoney() {
        Intent i = new Intent(getView().getContext(), MessageWealthActivity.class);
        i.putExtra(Constant.INTENT_PARAM, getView().getMemberId());
        i.putExtra(Constant.INTENT_PARAM_ONE, getView().getFansHeadUrl());
        getView().getContext().startActivity(i);

    }

    /**
     * 跳转到团队
     */
    @Override
    public void toTeam() {
        getView().paramStartActivity(MessageMyTeamActivity.class,getView().getMemberId());


    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {

        Intent i = new Intent(getView().getContext(), PlayVideoActivity.class);
        i.putExtra(Constant.INTENT_PARAM, position + "");
        i.putExtra(Constant.INTENT_PARAM_ONE, 5);
        i.putExtra(Constant.INTENT_PARAM_TWO,10);
        getView().getContext().startActivity(i);
    }

    /**
     * 跳转到发送私信画面
     */
    @Override
    public void sendMessage() {
        Intent intent = new Intent(getView().getContext(),LetterChatActivity.class);
        intent.putExtra("EmId",getView().getEmId());
        intent.putExtra("FansHeadUrl",getView().getFansHeadUrl());
        intent.putExtra("memberId",getView().getMemberId());
        intent.putExtra("Nickname",getView().getNickname());
        getView().getContext().startActivity(intent);
    }

    @Override
    public void onClickHeaderImg() {
        getView().clickHederImg();
    }

    @Override
    public void loadListData() {

    }
}
