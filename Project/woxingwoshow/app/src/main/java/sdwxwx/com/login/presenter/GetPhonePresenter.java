package sdwxwx.com.login.presenter;

import android.text.TextUtils;

import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.home.contract.ContactBean;
import sdwxwx.com.home.model.SearchUserModel;
import sdwxwx.com.login.bean.ChildEntity;
import sdwxwx.com.login.bean.GetPhoneBean;
import sdwxwx.com.login.contract.GetPhoneContract;
import sdwxwx.com.util.ContactUtil;
import sdwxwx.com.util.LoginUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 丁胜胜 on 2018/05/29.
 */

public class GetPhonePresenter extends BasePresenter<GetPhoneContract.View> implements GetPhoneContract.Presenter {

    SearchUserModel mModel;

    List<GetPhoneBean> mList = new ArrayList<>();

    @Override
    public void childEntityData() {

        //后台：获取通讯录已注册账号
     mModel.memberVerify(ContactUtil.getContacts(getView().getContext()), LoginUtil.getStrUserInfo(Constant.SP_MEMBER_ID, getView().getContext()), new BaseCallback<List<ContactBean>>() {

     @Override
         public void onSuccess(List<ContactBean> data) {

         GetPhoneBean getPhoneBean = new GetPhoneBean();
         List<ChildEntity> childEntityList = new ArrayList<>();
         List<ChildEntity> childEntityTwoList = new ArrayList<>();

         //根据是否关注，保存到不同的list
         for (int i = 0; i < data.size(); i++) {
             if (data.get(i).isIs_followed()==0) {
                 ChildEntity childEntity = new ChildEntity();
                 childEntity.setNickname(data.get(i).getNickname());
                 childEntity.setMobile(data.get(i).getMobile());
                 childEntity.setAvatar_url(data.get(i).getAvatar_url());
//                 childEntity.setIs_followed(data.get(i).isIs_followed());
                 childEntity.setId(data.get(i).getId());
                 childEntityList.add(childEntity);
             } else {
                 ChildEntity childEntity = new ChildEntity();
                 childEntity.setNickname(data.get(i).getNickname());
                 childEntity.setMobile(data.get(i).getMobile());
                 childEntity.setAvatar_url(data.get(i).getAvatar_url());
//                 childEntity.setIs_followed(data.get(i).isIs_followed());
                 childEntity.setId(data.get(i).getId());
                 childEntityTwoList.add(childEntity);
             }
         }
         getPhoneBean.setChildren(childEntityList);
         mList.add(getPhoneBean);
         getPhoneBean.setChildrenTwo(childEntityTwoList);
         mList.add(getPhoneBean);
         if (getView() == null) {
             return;
         }
         getView().bindListData(mList);
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
     @Override
     public void onClickBack() {
        getView().closeActivity();
    }

    @Override
    public void loadListData() {
        // 如果手机通讯录不为空，则调用接口进行查询
        if (!TextUtils.isEmpty(ContactUtil.getContacts(getView().getContext()))) {
            childEntityData();
        }
    }

}
