package sdwxwx.com.recycler_listener;

import android.content.Context;
import android.os.Vibrator;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * create by 860115039
 * date      2018/5/8
 * time      9:00
 */
public abstract class OnRecyclerItemListener implements RecyclerView.OnItemTouchListener {
    private GestureDetectorCompat mGestureDetector;
    private RecyclerView recyclerView;
    private Vibrator mVibrator;

    public OnRecyclerItemListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {

        //点击事件
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null && child.isEnabled()) {
                RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                onItemClick(vh);
            }
            return true;
        }

        //长点击事件
        @Override
        public void onLongPress(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                if (!child.dispatchTouchEvent(e)) {
                    RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                    if (onItemLongClick(vh)) {
                        getVibrator().vibrate(70);
                        Log.d("longClick", "长按事件触发了");
                    }
                }
            }
        }


    }

    public abstract void onItemClick(RecyclerView.ViewHolder vh);

    public abstract boolean onItemLongClick(RecyclerView.ViewHolder vh);

    private Vibrator getVibrator() {
        if (mVibrator == null)
            mVibrator = (Vibrator) recyclerView.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        return mVibrator;
    }
}
