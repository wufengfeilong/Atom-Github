package com.fcn.park.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fcn.park.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by 860115001 on 2018/04/10.
 */

public class InputIconLayout extends LinearLayout{

    @IntDef({ACTION_NEXT, ACTION_DONE, ACTION_SEARCH})
    @Retention(RetentionPolicy.SOURCE)
    /**
     * 输入法的配置
     */
    public @interface ImeOptions {
    }

    public static final int ACTION_NEXT = 0x00000000;
    public static final int ACTION_DONE = 0x00000001;
    public static final int ACTION_SEARCH = 0x00000002;

    @IntDef({INPUT_TEXT, INPUT_NUM, INPUT_PASSWORD})
    @Retention(RetentionPolicy.SOURCE)
    /**
     * 输入框的类型
     */
    public @interface InputType {
    }

    public static final int INPUT_PASSWORD = 0;
    public static final int INPUT_TEXT = 1;
    public static final int INPUT_NUM = 2;


    private static final int DEFAULT_VALUE_INPUT_SIZE = 14;//sp
    private static final int DEFAULT_VALUE_INPUT_MAX_EMS = -1;//数量
    private static final float DEFAULT_VALUE_LEFT_ICON_SIZE = 24.0F;//dp
    private static final float DEFAULT_VALUE_LEFT_IMAGE_LEFT_MARGIN = 15.0F;//dp
    private static final float DEFAULT_VALUE_INPUT_LEFT_MARGIN = 10.0F;//dp
    private static final float DEFAULT_VALUE_INPUT_TOP_PADDING = 15.0F;//dp
    private static final float DEFAULT_VALUE_INPUT_BOTTOM_PADDING = 15.0F;//dp

    private Drawable mLeftIcon;//左侧的图标
    private float mLeftIconSize;//图片的大小

    private CharSequence mInputHint;//提示文本
    @InputType
    private int mInputType;//输入的类型
    private float mInputSize;//字体的大小
    private int mInputMaxLength;//字体的输入上限制

    private
    @ImeOptions
    int mImeOptions;//输入法的配置

    private ImageView mIvLeftIcon;//左侧显示图标的ImageView
    private EditText mEditInput;//用户输入的输入框


    public InputIconLayout(Context context) {
        this(context, null);
    }

    public InputIconLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputIconLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public EditText getInputEditView() {
        return mEditInput;
    }

    /**
     * 设置输入法的配置
     *
     * @param imeOptions {@link ImeOptions}
     */
    public void setImeOptions(@ImeOptions int imeOptions) {
        mImeOptions = imeOptions;
        int action = imeOptions;
        switch (imeOptions) {
            case ACTION_NEXT:
                action = EditorInfo.IME_ACTION_NEXT;
                break;
            case ACTION_DONE:
                action = EditorInfo.IME_ACTION_DONE;
                break;
            case ACTION_SEARCH:
                action = EditorInfo.IME_ACTION_SEARCH;
                break;
        }
        mEditInput.setImeOptions(action);
    }

    /**
     * 设置输入框的输入类型
     *
     * @param inputType {@link InputType}
     */
    public void setInputType(@InputType int inputType) {
        mInputType = inputType;
        int type = inputType;
        switch (type) {
            case INPUT_TEXT:
                type = android.text.InputType.TYPE_CLASS_TEXT;
                break;
            case INPUT_NUM:
                type = android.text.InputType.TYPE_CLASS_NUMBER;
                break;
            case INPUT_PASSWORD:
                type = android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
                break;
        }

        mEditInput.setInputType(type);
    }

    public void setLeftIcon(Drawable drawable) {
        if (drawable != null && mIvLeftIcon != null) {
            mIvLeftIcon.setImageDrawable(drawable);
        }
        mLeftIcon = drawable;
    }

    public void setLeftIconSize(float iconSize) {
        mLeftIconSize = iconSize;
        LayoutParams lp = (LayoutParams) mIvLeftIcon.getLayoutParams();
        if (lp != null) {
            lp.height = (int) iconSize;
            lp.width = (int) iconSize;
            mIvLeftIcon.setLayoutParams(lp);
        }
    }

    /**
     * 设置输入框的文字
     *
     * @param text
     */
    public void setInputText(CharSequence text) {
        if (text != null && mEditInput != null)
            mEditInput.setText(text);
    }

    /**
     * 设置输入框的提示文字
     *
     * @param hint
     */
    public void setInputHint(CharSequence hint) {
        if (hint != null && mEditInput != null)
            mEditInput.setHint(hint);
        mInputHint = hint;
    }


    /**
     * 设置输入框的文字大小
     *
     * @param inputSize
     */
    private void setInputSize(float inputSize) {
        if (mEditInput != null) {
            mEditInput.getPaint().setTextSize(inputSize);
            //mEditInput.setTextSize(inputSize);
        }
        mInputSize = inputSize;
    }

    /**
     * 设置输入框中最大输入的值
     *
     * @param maxLength
     */
    public void setInputMaxLength(int maxLength) {
        if (maxLength <= -1)
            return;
        mInputMaxLength = maxLength;
        mEditInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        // mEditInput.setMaxEms(maxEms);
    }

    /**
     * 获取输入框的内容
     *
     * @return
     */
    public Editable getInputText() {
        return mEditInput.getText();
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        //进行一些默认设置
        initDefaultValue(context);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.InputIconLayout, defStyleAttr, 0);

        int indexCount = ta.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = ta.getIndex(i);
            if (index == R.styleable.InputIconLayout_inputHint) {
                mInputHint = ta.getString(index);
            } else if (index == R.styleable.InputIconLayout_inputSize) {
                mInputSize = ta.getDimensionPixelSize(index, (int) mInputSize);
            } else if (index == R.styleable.InputIconLayout_inputType) {
                int inputType = ta.getInteger(index, mInputType);
                switch (inputType) {
                    case INPUT_TEXT:
                        mInputType = INPUT_TEXT;
                        break;
                    case INPUT_NUM:
                        mInputType = INPUT_NUM;
                        break;
                    case INPUT_PASSWORD:
                        mInputType = INPUT_PASSWORD;
                        break;
                }
            } else if (index == R.styleable.InputIconLayout_leftIcon) {
                mLeftIcon = ta.getDrawable(index);
            } else if (index == R.styleable.InputIconLayout_leftIconSize) {
                mLeftIconSize = ta.getDimensionPixelSize(index, (int) mLeftIconSize);
            } else if (index == R.styleable.InputIconLayout_imeOptions) {
                int imeOptions = ta.getInteger(index, mImeOptions);
                switch (imeOptions) {
                    case ACTION_NEXT:
                        mImeOptions = ACTION_NEXT;
                        break;
                    case ACTION_SEARCH:
                        mImeOptions = ACTION_SEARCH;
                        break;
                    case ACTION_DONE:
                        mImeOptions = ACTION_DONE;
                        break;
                }
            } else if (index == R.styleable.InputIconLayout_inputMaxLength) {
                mInputMaxLength = ta.getInteger(index, mInputMaxLength);
            }
        }

        ta.recycle();

        setImeOptions(mImeOptions);
        setLeftIcon(mLeftIcon);
        setLeftIconSize(mLeftIconSize);

        setInputMaxLength(mInputMaxLength);
        setInputHint(mInputHint);
        setInputType(mInputType);
        setInputSize(mInputSize);
    }


    /**
     * 设置初始值
     *
     * @param context
     */
    private void initDefaultValue(Context context) {
        if (getBackground() == null) {
            setBackgroundColor(ContextCompat.getColor(context, R.color.windowBackground));
        }
        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(HORIZONTAL);

        mIvLeftIcon = new ImageView(context);
        mEditInput = new EditText(context);

        mEditInput.setSingleLine();
        mEditInput.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        ViewCompat.setBackground(mEditInput, new ColorDrawable());

        mInputType = INPUT_TEXT;
        mImeOptions = ACTION_NEXT;

        mInputSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_VALUE_INPUT_SIZE, getResources().getDisplayMetrics());
        mInputMaxLength = DEFAULT_VALUE_INPUT_MAX_EMS;
        mLeftIconSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_VALUE_LEFT_ICON_SIZE, getResources().getDisplayMetrics());

        float imageLeftMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_VALUE_LEFT_IMAGE_LEFT_MARGIN, getResources().getDisplayMetrics());
        float editInputLeftMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_VALUE_INPUT_LEFT_MARGIN, getResources().getDisplayMetrics());
        float editInputBottomPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_VALUE_INPUT_BOTTOM_PADDING, getResources().getDisplayMetrics());
        float editInputTopPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_VALUE_INPUT_TOP_PADDING, getResources().getDisplayMetrics());

        mEditInput.setPadding(0, (int) editInputTopPadding, 0, (int) editInputBottomPadding);
        LayoutParams editLp = (LayoutParams) mEditInput.getLayoutParams();
        if (editLp == null) {
            editLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        editLp.width = LayoutParams.MATCH_PARENT;
        editLp.weight = 1;
        editLp.setMargins((int) editInputLeftMargin, 0, 0, 0);
        mEditInput.setLayoutParams(editLp);
        LayoutParams ivLp = (LayoutParams) mIvLeftIcon.getLayoutParams();
        if (ivLp == null) {
            ivLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        ivLp.setMargins((int) imageLeftMargin, 0, 0, 0);
        mIvLeftIcon.setLayoutParams(ivLp);

        addView(mIvLeftIcon);
        addView(mEditInput);
    }
}
