package sdwxwx.com.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.liaoinstan.springview.utils.DensityUtil;
import sdwxwx.com.R;

/**
 * Created by 860115025 on 2018/06/13.
 */

public class ToastUtil {

    /** 消息对象 */
    private static Toast toast=null;

    /**
     * 显示提示
     * @param context
     * @param toastContent
     */
    public static void ToastShow(Context context, String toastContent) {
        // 获取控件
        View view = LayoutInflater.from(context).inflate(R.layout.toast_common, null);
        TextView text = (TextView) view.findViewById(R.id.textToast);
        // 设置显示文字
        text.setText(toastContent);
        if (toast == null) {
            toast = new Toast(context);
        }
        // Toast显示的位置
        toast.setGravity(Gravity.FILL_HORIZONTAL|Gravity.TOP, 0, DensityUtil.dp2px(54));
        // Toast显示的时间
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
