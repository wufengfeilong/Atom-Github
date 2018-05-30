package woxingwoxiu.com.login.activity;

import android.Manifest;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityGroupListBinding;
import woxingwoxiu.com.login.bean.ChildEntity;
import woxingwoxiu.com.login.bean.ChildEntityTwo;
import woxingwoxiu.com.login.bean.GetPhoneBean;
import woxingwoxiu.com.login.contract.GetPhoneContract;
import woxingwoxiu.com.login.presenter.GetPhonePresenter;

/**
 * Created by 丁胜胜 on 2018/05/29.
 * 类描述：获取手机通讯录界面
 */
public class GetPhoneActivity
        extends BaseActivity<ActivityGroupListBinding,GetPhonePresenter>
        implements GetPhoneContract.View,GroupedRecyclerViewAdapter.OnChildClickListener {

    private TextView tvTitle;
    private RecyclerView rvList;
    GroupedListTestAdapter mGroupedListAdapter;
    List<GetPhoneBean> mBeanList = new ArrayList<>();

    int flag_a = 1;

    /**
     * 获取库Phone表字段
     **/
     private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Photo.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};

    /**
     * 联系人显示名称
     **/
    private static final int PHONES_DISPLAY_NAME = 0;

    /**
     * 电话号码
     **/
    private static final int PHONES_NUMBER = 1;

    private static final int SUCCESSCODE = 100;

    @Override
    protected void initViews() {
        mDataBinding.setGetPhonePresenter(mPresenter);
        initRecyclerView();
//        mPresenter.loadListData();
        RequestContacts();

    }

    @Override
    protected GetPhonePresenter createPresenter() {
        return new GetPhonePresenter(getContext());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_list;
    }


    @Override
    public void initRecyclerView() {
        mDataBinding.rvList.setLayoutManager(new LinearLayoutManager(this));
        mGroupedListAdapter = new GroupedListTestAdapter(this);
        mDataBinding.rvList.addItemDecoration(new DividerItemDecoration(GetPhoneActivity.this, DividerItemDecoration.VERTICAL));
        mDataBinding.rvList.setAdapter(mGroupedListAdapter);
        mGroupedListAdapter.setOnChildClickListener(this);
    }

    @Override
    public void bindListData(List<GetPhoneBean> beanList) {
        mBeanList = beanList;

        mGroupedListAdapter.changeDataSet();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.rvList;
    }

    @Override
    public void onChildClick(GroupedRecyclerViewAdapter groupedRecyclerViewAdapter, BaseViewHolder baseViewHolder, int i, int i1) {

    }

    //通讯录权限取得
    private void RequestContacts() {
        PermissionGen.with(GetPhoneActivity.this)
                .addRequestCode(SUCCESSCODE)
                .permissions(
                        Manifest.permission.READ_CONTACTS
                )
                .request();

    }

    //方法的重写
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    //权限申请成功
    @PermissionSuccess(requestCode = SUCCESSCODE)
    public void doSomething() {
        mPresenter.loadListData();
    }

    @PermissionFail(requestCode = SUCCESSCODE)
    public void doFailSomething() {
    }

    public class GroupedListTestAdapter extends GroupedRecyclerViewAdapter {


        private static final int TYPE_1 = 0;
        private static final int TYPE_2 = 1;


        public GroupedListTestAdapter(Context context) {
            super(context);
        }

        @Override
        public int getGroupCount() {
            return mBeanList == null ? 0 : mBeanList.size();
        }

        @Override
        public int getChildrenCount(int i) {
            GetPhoneBean getPhoneBean = mBeanList.get(i);

            int count = 0;

            switch (i) {
                case TYPE_1:
                    count = getPhoneBean.getChildren().size();
                    break;
                case TYPE_2:
                    count = getPhoneBean.getChildrenTwo().size();
                    break;
            }
            return count;
        }

        @Override
        public boolean hasHeader(int i) {
            if (i == TYPE_1) {
                return true;
            } else {
                return true;
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
        public int getChildLayout(int i) {
            if (i == TYPE_1) {
                return R.layout.adapter_child;
            } else {
                return R.layout.adapter_child_two;
            }
        }


        @Override
        public void onBindHeaderViewHolder(BaseViewHolder holder, int i) {

            if (flag_a <= 2) {
                switch (i) {
                    case TYPE_1:
                        holder.setText(R.id.tv_header, "未关注用户");
                        break;
                    case TYPE_2:
                        holder.setText(R.id.tv_header, "已关注用户");
                }
            }
            flag_a++;
        }

        @Override
        public void onBindFooterViewHolder(BaseViewHolder baseViewHolder, int i) {

        }

        @Override
        public void onBindChildViewHolder(BaseViewHolder holder, int i, int childPosition) {

            GetPhoneBean getPhoneBean = mBeanList.get(i);

            switch (i) {
                case TYPE_1:
                    ChildEntity childEntity = getPhoneBean.getChildren().get(childPosition);
                    holder.setText(R.id.tv_phone_name, childEntity.getPhoneName());
                    holder.setText(R.id.tv_phone_number, childEntity.getPhoneNumber());
                    break;
                case TYPE_2:
                    ChildEntityTwo childEntityTwo = getPhoneBean.getChildrenTwo().get(childPosition);
                    holder.setText(R.id.tv_phone_name_two, childEntityTwo.getPhoneName());
                    holder.setText(R.id.tv_phone_number_two, childEntityTwo.getPhoneNumber());
                    break;
            }
        }

        @Override
        public int getHeaderViewType(int groupPosition) {
            return mBeanList.get(groupPosition).getType();
        }

        @Override
        public int getChildViewType(int groupPosition, int childPosition) {
            return mBeanList.get(groupPosition).getType();
        }

    }
}



