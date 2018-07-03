package sdwxwx.com.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import me.kaelaela.verticalviewpager.transforms.DefaultTransformer;

/**
 * create by 860115039
 * date      2018/5/21
 * time      17:00
 */
public class VerticalViewPager extends ViewPager {
    float startX,endX,startY,endY;
    public interface OnSwipeListener{
        void onLeftSwipe();
        void onRightSwipe();
    }

    private OnSwipeListener onSwipeListener;
    public void setOnSwipeListener(OnSwipeListener listener) {
        this.onSwipeListener = listener;
    }

    public VerticalViewPager(Context context) {
        this(context, (AttributeSet)null);
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setPageTransformer(true, new DefaultTransformer());
    }

    private MotionEvent swapTouchEvent(MotionEvent event) {
        float width = (float)this.getWidth();
        float height = (float)this.getHeight();
        float swappedX = event.getY() / height * width;
        float swappedY = event.getX() / width * height;
        event.setLocation(swappedX, swappedY);
        return event;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        this.swapTouchEvent(event);
        return super.onInterceptTouchEvent(this.swapTouchEvent(event));
    }

    public boolean onTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                endX = ev.getX();
                endY = ev.getY();
                if (startX-endX>300) {
                    if (onSwipeListener!=null) {
                        onSwipeListener.onLeftSwipe();
                    }
                } else if (endX-startX>300) {
                    if (onSwipeListener!=null) {
                        onSwipeListener.onRightSwipe();
                    }
                }
                break;
        }
        return super.onTouchEvent(this.swapTouchEvent(ev));
    }
}
