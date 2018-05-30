package woxingwoxiu.com.login.utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import woxingwoxiu.com.R;

/**
 * Created by 丁胜胜 on 2018/05/15.
 * 类描述：验证码输入框
 */

public class VeriftyEditText  extends AppCompatEditText implements TextWatcher {

    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;
    private Drawable mClearDrawable;
    private EditText mEditInput;//用户输入的输入框

    public VeriftyEditText(Context context) {
        super(context);
        init();
    }

    public VeriftyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VeriftyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mClearDrawable = getResources().getDrawable(R.drawable.clear_button);
    }



    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisible(focused && length() > 0);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
        setClearIconVisible(hasFocus() && s.length() > 0);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Drawable drawable = getCompoundDrawables()[DRAWABLE_RIGHT];
                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                        && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                    setText("");
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 获取输入框的内容
     *
     * @return
     */
    public Editable getInputText() {
        return mEditInput.getText();
    }
    private void setClearIconVisible(boolean visible) {
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[DRAWABLE_LEFT], getCompoundDrawables()[DRAWABLE_TOP],
                visible ? mClearDrawable : null, getCompoundDrawables()[DRAWABLE_BOTTOM]);
    }

}
