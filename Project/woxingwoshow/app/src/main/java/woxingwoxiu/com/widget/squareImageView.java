package woxingwoxiu.com.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by 860115025 on 2018/05/17.
 */

public class squareImageView extends ImageView {
    public squareImageView(Context context) {
        super(context);
    }

    public squareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public squareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //传入参数widthMeasureSpec、heightMeasureSpec
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
