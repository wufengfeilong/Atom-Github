package sdwxwx.com.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * create by 860115039
 * date      2018/6/8
 * time      11:13
 */
public class ContactUtil {
    /** 获取库Phone表字段 **/
    private static final String[] PHONES_PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            , ContactsContract.CommonDataKinds.Phone.NUMBER
            , ContactsContract.CommonDataKinds.Photo.PHOTO_ID
            , ContactsContract.CommonDataKinds.Phone.CONTACT_ID };
    /** 电话号码 **/
    private static final int PHONES_NUMBER = 1;


    public static String getContacts(Context context) {
//        String[] phoneArr= new String[]{};
        ArrayList<String> strArray = new ArrayList<String> ();
//        int i = 0 ;
        ContentResolver resolver = context.getContentResolver();
        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PHONES_PROJECTION, null, null, null);

        // 不为空
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                // 得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER);
                // 当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber)) {
                    continue;
                }
//                phoneArr[i] = phoneNumber;
                strArray.add(phoneNumber);

//                i++;
            }
            phoneCursor.close();
        }
        String phones = strArray.toString().replace(" ","");
        return phones.substring(1,phones.length()-1);
    }
}
