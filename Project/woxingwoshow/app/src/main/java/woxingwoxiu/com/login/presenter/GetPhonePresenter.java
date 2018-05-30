package woxingwoxiu.com.login.presenter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.login.bean.ChildEntity;
import woxingwoxiu.com.login.bean.ChildEntityTwo;
import woxingwoxiu.com.login.bean.GetPhoneBean;
import woxingwoxiu.com.login.contract.GetPhoneContract;

/**
 * Created by 丁胜胜 on 2018/05/29.
 */

public class GetPhonePresenter extends BasePresenter<GetPhoneContract.View> implements GetPhoneContract.Presenter {

    List<GetPhoneBean> mList = new ArrayList<>();
    /** 获取库Phone表字段 **/
    private static final String[] PHONES_PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Photo.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID };

    /** 联系人显示名称 **/
    private static final int PHONES_DISPLAY_NAME = 0;

    /** 电话号码 **/
    private static final int PHONES_NUMBER = 1;

    private static final int SUCCESSCODE = 100;

    private Context context;
    public GetPhonePresenter(Context context){
        this.context = context;
    }

    @Override
    public void childEntityData() {
        GetPhoneBean getPhoneBean = new GetPhoneBean();
        List<ChildEntity> childEntityList = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            ChildEntity childEntity = new ChildEntity();
//            childEntity.setPhoneName("刘德华" + i);
//            childEntity.setPhoneNumber("18552555555" + i);
//            childEntityList.add(childEntity);
//        }
//        getPhoneBean.setType(0);
//        getPhoneBean.setChildren((ArrayList<ChildEntity>) childEntityList);
//        mList.add(getPhoneBean);

        ContentResolver resolver = getView().getContext().getContentResolver();
        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PHONES_PROJECTION, null, null, null);

        // 不为空
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                ChildEntity childEntity = new ChildEntity();
                // 得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER);
                // 当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                // 得到联系人名称
                String phoneName = phoneCursor.getString(PHONES_DISPLAY_NAME);
                childEntity.setPhoneName(phoneName);
                childEntity.setPhoneNumber(phoneNumber);
                childEntityList.add(childEntity);
            }
            getPhoneBean.setType(0);
            getPhoneBean.setChildren((ArrayList<ChildEntity>) childEntityList);
            mList.add(getPhoneBean);
        }
    }


    @Override
    public void childEntityTwoData() {
        GetPhoneBean getPhoneBean = new GetPhoneBean();
        List<ChildEntityTwo> childEntityTwoList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ChildEntityTwo childEntityTwo = new ChildEntityTwo();
            childEntityTwo.setPhoneName("周星驰" + i);
            childEntityTwo.setPhoneNumber("18552555555" + i);
            childEntityTwoList.add(childEntityTwo);
        }
        getPhoneBean.setType(1);
        getPhoneBean.setChildrenTwo((ArrayList<ChildEntityTwo>) childEntityTwoList);
        mList.add(getPhoneBean);
    }

    @Override
    public void loadListData() {
        childEntityData();
        childEntityTwoData();
        getView().bindListData(mList);

    }
}
