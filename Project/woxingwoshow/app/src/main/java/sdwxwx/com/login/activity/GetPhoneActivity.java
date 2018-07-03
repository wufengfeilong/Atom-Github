package sdwxwx.com.login.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import sdwxwx.com.R;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.databinding.ActivityGroupListBinding;
import sdwxwx.com.login.bean.ChildEntity;
import sdwxwx.com.login.bean.GetPhoneBean;
import sdwxwx.com.login.contract.GetPhoneContract;
import sdwxwx.com.login.presenter.GetPhonePresenter;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.message.activity.FansHomeActivity;
import sdwxwx.com.message.model.FansHomeModel;
import sdwxwx.com.play.model.PlayVideoFragmentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 丁胜胜 on 2018/05/29.
 * 类描述：获取手机通讯录界面
 */
public class GetPhoneActivity
        extends BaseActivity<ActivityGroupListBinding,GetPhonePresenter>
        implements GetPhoneContract.View,GroupedRecyclerViewAdapter.OnChildClickListener {

    GroupedListTestAdapter mGroupedListAdapter;

    List<GetPhoneBean> mGroups = new ArrayList<>();

    @Override
    protected void initViews() {
        mDataBinding.setGetPhonePresenter(mPresenter);
        initRecyclerView();
        RequestContacts();
    }

    @Override
    protected GetPhonePresenter createPresenter() {
        return new GetPhonePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_list;
    }

    @Override
    public void initRecyclerView() {
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(this));
        mGroupedListAdapter = new GroupedListTestAdapter(this);
        mDataBinding.rvList.setAdapter(mGroupedListAdapter);
        mGroupedListAdapter.setOnChildClickListener(this);
    }

    @Override
    public void bindListData(List<GetPhoneBean> beanList) {
        mGroups = beanList;
        mGroupedListAdapter.changeDataSet();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.rvList;
    }

    @Override
    public void onChildClick(GroupedRecyclerViewAdapter groupedRecyclerViewAdapter, BaseViewHolder baseViewHolder, int i, int i1) {

        if(i==0){
            paramStartActivity(FansHomeActivity.class, String.valueOf(mGroups.get(0).getChildren().get(i1).getId()));
        }
        if(i==1){
            paramStartActivity(FansHomeActivity.class, String.valueOf(mGroups.get(1).getChildrenTwo().get(i1).getId()));
        }
    }

    //通讯录权限取得
    private void RequestContacts() {
        // RxPermissions询问通讯录的使用权限
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_CONTACTS)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Boolean value) {
                        // 如果已经给予相机的使用权限
                        if(value){
                            mPresenter.loadListData();
                        }else {
                            showNormalDialog("手机通讯录");
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 对话框
     */
    private void showNormalDialog(String message){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(GetPhoneActivity.this);
        normalDialog.setTitle("权限申请");
        normalDialog.setMessage("未获得" + message +"权限，是否去设置中授予我行我秀该权限？");
        normalDialog.setPositiveButton("知道了",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeActivity();
                    }
                });
        normalDialog.setNegativeButton("去设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri packageURI = Uri.parse("package:sdwxwx.com");
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        startActivity(intent);
                    }
                });
        // 显示
        normalDialog.show();
    }

    public class GroupedListTestAdapter extends GroupedRecyclerViewAdapter {

        private static final int TYPE_1 = 1;
        private static final int TYPE_2 = 2;
        private static final int TYPE_CHILD_1 = 3;
        private static final int TYPE_CHILD_2 = 4;

        public GroupedListTestAdapter(Context context) {
            super(context);

        }
        @Override
        public int getGroupCount() {
            return mGroups == null ? 0 : mGroups.size();
        }

        @Override
        public int getChildrenCount(int i) {
            GetPhoneBean getPhoneBean = mGroups.get(i);
            int count = 0;

            switch (i) {
                case 0:
                    count = getPhoneBean.getChildren().size();
                    break;
                case 1:
                    count = getPhoneBean.getChildrenTwo().size();
                    break;
            }
            return count;
        }
        @Override
        public boolean hasHeader(int i) {
            GetPhoneBean getPhoneBean = mGroups.get(i);
            if (i == TYPE_1) {
                if(getPhoneBean.getChildrenTwo().size()==0){
                    return false;
                }else {
                    return true;
                }
            } else {
                if(getPhoneBean.getChildren().size()==0){
                    return false;
                }else {
                    return true;
                }
            }
        }

        @Override
        public boolean hasFooter(int i) {
            return false;
        }

        @Override
        public int getHeaderLayout(int i) {
                return R.layout.adapter_header;
        }

        @Override
        public int getFooterLayout(int i) {
            return R.layout.adapter_footer;
        }

        @Override
        public int getChildLayout(int viewType) {
                return R.layout.item_message_friend_fans;
        }

        @Override
        public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {

            GetPhoneBean getPhoneBean = mGroups.get(groupPosition);
            int viewType = getHeaderViewType(groupPosition);
            if (viewType == TYPE_1) {
                holder.setText(R.id.tv_header, getPhoneBean.getChildren().size()+"个好友待关注");
            } else if (viewType == TYPE_2) {
                holder.setText(R.id.tv_header, getPhoneBean.getChildrenTwo().size()+"个好友已关注");
            }
        }

        @Override
        public void onBindFooterViewHolder(BaseViewHolder baseViewHolder, int i) {
        }

        @Override
        public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, final int childPosition) {

            final GetPhoneBean getPhoneBean = mGroups.get(groupPosition);
            int viewType = getChildViewType(groupPosition, childPosition);
            if (viewType == TYPE_CHILD_1) {

                final ChildEntity childEntity = getPhoneBean.getChildren().get(childPosition);
                holder.setText(R.id.nickname, childEntity.getNickname());
                holder.setText(R.id.signature, childEntity.getMobile());
                // 设定关注图片
                if (!childEntity.getMobile().equals(LoginHelper.getInstance().getUserBean().getMobile())) {
                    holder.setImageResource(R.id.is_followed, R.drawable.message_attention);
                }
                RequestOptions options = new RequestOptions().error(R.drawable.default_header_image);
                Glide.with(GetPhoneActivity.this).load(childEntity.getAvatar_url()).apply(options).into((ImageView) holder.get(R.id.fans_avatar_url));
                ((ImageView) holder.get(R.id.is_followed)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        PlayVideoFragmentModel.followUser(getContext(),LoginHelper.getInstance().getUserId()
                                , childEntity.getId() + "", new BaseCallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        childEntity.setIs_followed(true);
                                        mGroups.get(1).getChildrenTwo().add(childEntity);
                                        getPhoneBean.getChildren().remove(childPosition);
                                        mGroupedListAdapter.changeDataSet();
                                    }

                                    @Override
                                    public void onFail(String msg) {
                                        showToast(msg);
                                    }
                                });

                    }
                  });
            } else if (viewType == TYPE_CHILD_2) {
                final ChildEntity childEntityTwo = getPhoneBean.getChildrenTwo().get(childPosition);
                holder.setText(R.id.nickname, childEntityTwo.getNickname());
                holder.setText(R.id.signature, childEntityTwo.getMobile());
                if (TextUtils.isEmpty(childEntityTwo.getMobile())) {
                    holder.setVisible(R.id.signature, View.GONE);
                } else {
                    // 设置签名
                    holder.setText(R.id.signature, childEntityTwo.getMobile());
                    holder.setVisible(R.id.signature, View.VISIBLE);
                }
//                holder.setImageResource(R.id.is_followed, R.drawable.mutual_follow);
                holder.setImageResource(R.id.is_followed, R.drawable.already_follow);
                RequestOptions options = new RequestOptions().error(R.drawable.default_header_image);
                Glide.with(GetPhoneActivity.this).load(childEntityTwo.getAvatar_url()).apply(options).into((ImageView) holder.get(R.id.fans_avatar_url));
                ((ImageView) holder.get(R.id.is_followed)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            final AlertDialog.Builder normalDialog =
                                    new AlertDialog.Builder(getContext());
                            normalDialog.setMessage("确定不再关注此人?");
                            normalDialog.setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            FansHomeModel.cancelFollow(mContext,LoginHelper.getInstance().getUserId()
                                                    , childEntityTwo.getId()+"", new BaseCallback<String>() {
                                                        @Override
                                                        public void onSuccess(String data) {
                                                            childEntityTwo.setIs_followed(false);
                                                            mGroups.get(0).getChildren().add(childEntityTwo);
                                                            getPhoneBean.getChildrenTwo().remove(childPosition);
                                                            mGroupedListAdapter.changeDataSet();
                                                        }

                                                        @Override
                                                        public void onFail(String msg) {
                                                            showToast(msg);
                                                        }
                                                    });

                                        }
                                    });
                            normalDialog.setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 不做任何处理
                                        }
                                    });
                            // 显示
                            normalDialog.show();

                    }
                });
            }
        }

        @Override
        public int getHeaderViewType(int groupPosition) {

            if (groupPosition % 2 == 0) {
                return TYPE_1;
            } else {
                return TYPE_2;
            }
        }

        @Override
        public int getChildViewType(int groupPosition, int childPosition) {

            if (groupPosition % 2 == 0) {
                return TYPE_CHILD_1;
            } else {
                return TYPE_CHILD_2;
            }
        }

    }
}