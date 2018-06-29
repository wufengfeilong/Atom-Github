package com.fcn.park.base.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fcn.park.R;

/**
 * Created by 860115001 on 2018/04/10.
 */

public class NumProgressLayout extends LinearLayout {

    private static final float DEFAULT_VALUE_IMAGE_SIZE = 28.0f;
    private static final float DEFAULT_VALUE_BOTTOM_TOP_MARGIN = 15.0f;
    private static final float DEFAULT_VALUE_BOTTOM_TEXT_SIZE = 15.0f;
    private static final float DEFAULT_VALUE_NUM_TEXT_SIZE = 15.0f;

    private static final float DEFAULT_VALUE_LINES_HEIGHT = 5.0f;

    private float mImageSize;
    private float mBottomTopMargin;

    private String mBottomText;
    private String mNumText;

    private float mBottomTextSize;
    private float mNumTextSize;

    private ColorStateList mBottomTextColor;
    private ColorStateList mNumTextColor;

    private RelativeLayout mImageLayout;

    private TextView mBottomTextView;
    private TextView mNumTextView;
    private ImageView mImageView;

    private Drawable mImageBackground;
    private Drawable mImageRes;

    private ColorStateList mLineBackgroundColor;
    private View mLeftLinesView;
    private View mRightLinesView;

    private float mLinesHeight;

    private boolean isShowLinesLeft;
    private boolean isShowLinesRight;
    private LinearLayout mLinesLayout;

    private boolean isSelect;

    public NumProgressLayout(Context context) {
        this(context, null);
    }

    public NumProgressLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumProgressLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void setSelect(boolean isSelect) {
        if (isSelect) {
            mBottomTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlue));
        } else {
            mBottomTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGray));
        }
        this.isSelect = isSelect;
    }

    public boolean getSelect() {
        return isSelect;
    }

    public boolean isShowLinesLeft() {
        return isShowLinesLeft;
    }

    public void setShowLinesLeft(boolean showLinesLeft) {
        if (mLeftLinesView != null) {
            if (showLinesLeft) {
                mLeftLinesView.setVisibility(VISIBLE);
            } else {
                mLeftLinesView.setVisibility(INVISIBLE);
            }
        }
        isShowLinesLeft = showLinesLeft;
    }

    public boolean isShowLinesRight() {
        return isShowLinesRight;
    }

    public void setShowLinesRight(boolean showLinesRight) {
        if (mRightLinesView != null) {
            if (showLinesRight) {
                mRightLinesView.setVisibility(VISIBLE);
            } else {
                mRightLinesView.setVisibility(INVISIBLE);
            }
        }
        isShowLinesRight = showLinesRight;
    }

    public void setBottomText(String bottomText) {
        if (mBottomTextView != null && !TextUtils.isEmpty(bottomText)) {
            mBottomTextView.setText(bottomText);
            mBottomText = bottomText;
        }
    }

    public void setNumText(String numText) {
        if (mNumTextView != null && !TextUtils.isEmpty(numText)) {
            mNumTextView.setText(numText);
            mNumText = numText;
        }
    }

    public void setBottomTextSize(float bottomTextSize) {
        setBottomTextSize(TypedValue.COMPLEX_UNIT_SP, bottomTextSize);
    }

    public void setBottomTextSize(int unit, float bottomTextSize) {
        bottomTextSize = TypedValue.applyDimension(unit, bottomTextSize, getResources().getDisplayMetrics());
        setBottomTextSizeSp(bottomTextSize);
    }

    private void setBottomTextSizeSp(float bottomTextSize) {
        if (mBottomTextView != null) {
            mBottomTextView.getPaint().setTextSize(bottomTextSize);
            mBottomTextSize = bottomTextSize;
        }

    }

    public void setNumTextSize(float numTextSize) {
        setBottomTextSize(TypedValue.COMPLEX_UNIT_SP, numTextSize);
    }

    public void setNumTextSize(int unit, float numTextSize) {
        numTextSize = TypedValue.applyDimension(unit, numTextSize, getResources().getDisplayMetrics());
        setNumTextSizeSp(numTextSize);
    }

    private void setNumTextSizeSp(float numTextSize) {
        if (mNumTextView != null) {
            mNumTextView.getPaint().setTextSize(numTextSize);
            mNumTextSize = numTextSize;
        }

    }

    public void setBottomTopMargin(float bottomTopMargin) {
        setBottomTopMargin(TypedValue.COMPLEX_UNIT_DIP, bottomTopMargin);
    }

    public void setBottomTopMargin(int unit, float bottomTopMargin) {
        bottomTopMargin = TypedValue.applyDimension(unit, bottomTopMargin, getResources().getDisplayMetrics());
        setBottomTopMarginDip(bottomTopMargin);
    }

    private void setBottomTopMarginDip(float bottomTopMargin) {
        if (mBottomTextView != null) {
            LayoutParams lp = (LayoutParams) mBottomTextView.getLayoutParams();
            lp.setMargins(0, (int) bottomTopMargin, 0, 0);
            mBottomTextView.setLayoutParams(lp);
            mBottomTopMargin = bottomTopMargin;
        }
    }

    public void setLinesHeight(float linesHeight) {
        setLinesHeight(TypedValue.COMPLEX_UNIT_DIP, linesHeight);
    }

    public void setLinesHeight(int unit, float linesHeight) {
        linesHeight = TypedValue.applyDimension(unit, linesHeight, getResources().getDisplayMetrics());
        setLinesHeightDip(linesHeight);
    }


    private void setLinesHeightDip(float linesHeight) {
        if (mRightLinesView != null && mLeftLinesView != null) {

            ViewGroup.LayoutParams leftLp = mLeftLinesView.getLayoutParams();
            ViewGroup.LayoutParams rightLp = mRightLinesView.getLayoutParams();

            if (leftLp == null && rightLp == null) {
                leftLp = new LayoutParams(0, (int) linesHeight);
                rightLp = new LayoutParams(0, (int) linesHeight);
                ((LayoutParams) leftLp).weight = 1;
                ((LayoutParams) rightLp).weight = 1;
            } else if (leftLp == null) {
                leftLp = new LayoutParams(0, (int) linesHeight);
                ((LayoutParams) leftLp).weight = 1;
            } else if (rightLp == null) {
                rightLp = new LayoutParams(0, (int) linesHeight);
                ((LayoutParams) rightLp).weight = 1;
            } else {
                leftLp.height = (int) linesHeight;
                rightLp.height = (int) linesHeight;
            }
            mRightLinesView.setLayoutParams(rightLp);
            mLeftLinesView.setLayoutParams(leftLp);
            mLinesHeight = linesHeight;
        }
    }

    public void setImageSize(float imageSize) {
        setImageSize(TypedValue.COMPLEX_UNIT_DIP, imageSize);
    }

    public void setImageSize(int unit, float imageSize) {
        imageSize = TypedValue.applyDimension(unit, imageSize, getResources().getDisplayMetrics());
        setImageSizeDip(imageSize);
    }


    private void setImageSizeDip(float imageSize) {
        if (mImageView != null) {
            ViewGroup.LayoutParams lp = mImageView.getLayoutParams();
            if (lp == null) {
                lp = new RelativeLayout.LayoutParams((int) imageSize, (int) imageSize);
            } else {
                lp.width = (int) imageSize;
                lp.height = (int) imageSize;
            }

            mImageView.setLayoutParams(lp);
            mImageSize = imageSize;
        }
    }

    public void setNumTextColor(@ColorInt int color) {
        if (mNumTextView != null) {
            mNumTextColor = ColorStateList.valueOf(color);
            mNumTextView.setTextColor(mNumTextColor);
        }

    }

    public void setNumTextColor(ColorStateList colors) {
        if (mNumTextView != null && colors != null) {
            mNumTextView.setTextColor(colors);
            mNumTextColor = colors;
        }
    }

    public void setBottomTextColor(@ColorInt int color) {
        if (mBottomTextView != null) {
            mBottomTextColor = ColorStateList.valueOf(color);
            mBottomTextView.setTextColor(mBottomTextColor);
        }

    }

    public void setBottomTextColor(ColorStateList colors) {
        if (mBottomTextView != null && colors != null) {
            mBottomTextView.setTextColor(colors);
            mBottomTextColor = colors;
        }
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initDefault();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NumProgressLayout, defStyleAttr, 0);

        int indexCount = ta.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = ta.getIndex(i);
            if (index == R.styleable.NumProgressLayout_numText) {
                mNumText = ta.getString(index);
            } else if (index == R.styleable.NumProgressLayout_numTextColor) {
                mNumTextColor = ta.getColorStateList(index);
            } else if (index == R.styleable.NumProgressLayout_numTextSize) {
                mNumTextSize = ta.getDimensionPixelSize(index, (int) mNumTextSize);
            } else if (index == R.styleable.NumProgressLayout_bottomText) {
                mBottomText = ta.getString(index);
            } else if (index == R.styleable.NumProgressLayout_bottomTextColor) {
                mBottomTextColor = ta.getColorStateList(index);
            } else if (index == R.styleable.NumProgressLayout_bottomTextSize) {
                mBottomTextSize = ta.getDimensionPixelSize(index, (int) mBottomTextSize);
            } else if (index == R.styleable.NumProgressLayout_bottomTopMargin) {
                mBottomTopMargin = ta.getDimensionPixelSize(index, (int) mBottomTopMargin);
            } else if (index == R.styleable.NumProgressLayout_imageSize) {
                mImageSize = ta.getDimensionPixelSize(index, (int) mImageSize);
            } else if (index == R.styleable.NumProgressLayout_imageBackground) {
                mImageBackground = ta.getDrawable(index);
            } else if (index == R.styleable.NumProgressLayout_imageSrc) {
                mImageRes = ta.getDrawable(index);
            } else if (index == R.styleable.NumProgressLayout_showLeftLines) {
                isShowLinesLeft = ta.getBoolean(index, false);
            } else if (index == R.styleable.NumProgressLayout_showRightLines) {
                isShowLinesRight = ta.getBoolean(index, false);
            } else if (index == R.styleable.NumProgressLayout_linesHeight) {
                mLinesHeight = ta.getDimensionPixelSize(index, (int) mLinesHeight);
            } else if (index == R.styleable.NumProgressLayout_linesColor) {
                mLineBackgroundColor = ta.getColorStateList(index);
            }

        }

        ta.recycle();

        setBottomTextColor(mBottomTextColor == null ? ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorBlue)) : mBottomTextColor);
        setBottomText(mBottomText);
        setBottomTextSizeSp(mBottomTextSize);

        setNumTextColor(mNumTextColor == null ? ColorStateList.valueOf(0xFFFFFFFF) : mNumTextColor);
        setNumText(mNumText);
        setNumTextSizeSp(mNumTextSize);

        setBottomTopMarginDip(mBottomTopMargin);
        setImageSizeDip(mImageSize);
        setLinesHeightDip(mLinesHeight);

        setImageBackground(mImageBackground == null ? ContextCompat.getDrawable(getContext(), R.drawable.bg_light_gray_round) : mImageBackground);
        setImageRes(mImageRes == null ? ContextCompat.getDrawable(getContext(), R.drawable.bg_blue_round) : mImageRes);
        setLinesBackgroundColor(mLineBackgroundColor == null ? ColorStateList.valueOf(0xFFFFFFFF) : mLineBackgroundColor);
        setShowLinesLeft(isShowLinesLeft);
        setShowLinesRight(isShowLinesRight);
    }

    private void setLinesBackgroundColor(ColorStateList linesBackgroundColor) {
        if (mLeftLinesView != null && mRightLinesView != null) {
            mLeftLinesView.setBackgroundColor(linesBackgroundColor.getDefaultColor());
            mRightLinesView.setBackgroundColor(linesBackgroundColor.getDefaultColor());
            mLineBackgroundColor = linesBackgroundColor;
        }
    }

    private void setImageBackground(Drawable imageBackground) {
        if (mImageView != null && imageBackground != null) {
            ViewCompat.setBackground(mImageView, imageBackground);
            mImageBackground = imageBackground;
        }
    }

    private void setImageRes(Drawable imageRes) {
        if (mImageView != null && imageRes != null) {
            mImageView.setImageDrawable(imageRes);
            mImageRes = imageRes;
        }
    }

    private void initDefault() {

        setOrientation(VERTICAL);

        mImageSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_VALUE_IMAGE_SIZE, getResources().getDisplayMetrics());
        mBottomTopMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_VALUE_BOTTOM_TOP_MARGIN, getResources().getDisplayMetrics());

        mBottomTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_VALUE_BOTTOM_TEXT_SIZE, getResources().getDisplayMetrics());
        mNumTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_VALUE_NUM_TEXT_SIZE, getResources().getDisplayMetrics());

        mLinesHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_VALUE_LINES_HEIGHT, getResources().getDisplayMetrics());

        /**
         * 最外层的两个布局，一个是RelativeLayout用来控制圆形的位置和数字进度的位置
         * 另外一个是底部的TextView,用来描述当前的操作
         */
        mImageLayout = new RelativeLayout(getContext());
        LayoutParams ivLayoutLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mImageLayout.setLayoutParams(ivLayoutLp);

        mBottomTextView = new TextView(getContext());
        LayoutParams bottomTextLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomTextLp.gravity = Gravity.CENTER_HORIZONTAL;
        mBottomTextView.setLayoutParams(bottomTextLp);
        mBottomTextView.setSingleLine();

        addView(mImageLayout);
        addView(mBottomTextView);

        /**
         * RelativeLayout中的三个子控件，ImageView展示两个圆环，TextView显示当前的进度数字
         * View显示直线进度
         */
        mImageView = new ImageView(getContext());
        mImageView.setScaleType(ImageView.ScaleType.CENTER);
        RelativeLayout.LayoutParams ivLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivLp.addRule(RelativeLayout.CENTER_IN_PARENT);
        mImageView.setLayoutParams(ivLp);

        mNumTextView = new TextView(getContext());
        RelativeLayout.LayoutParams tvLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvLp.addRule(RelativeLayout.CENTER_IN_PARENT);
        mNumTextView.setLayoutParams(tvLp);

        mLinesLayout = new LinearLayout(getContext());
        RelativeLayout.LayoutParams linesLayoutLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linesLayoutLp.addRule(RelativeLayout.CENTER_VERTICAL);
        mLinesLayout.setOrientation(HORIZONTAL);
        mLinesLayout.setLayoutParams(linesLayoutLp);

        mLeftLinesView = new View(getContext());
        mRightLinesView = new View(getContext());
        LayoutParams leftLp = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftLp.weight = 1.0f;
        mLeftLinesView.setLayoutParams(leftLp);
        LayoutParams rightLp = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightLp.weight = 1.0f;
        mRightLinesView.setLayoutParams(rightLp);

        mLinesLayout.addView(mLeftLinesView);
        mLinesLayout.addView(mRightLinesView);

        mImageLayout.addView(mLinesLayout);
        mImageLayout.addView(mImageView);
        mImageLayout.addView(mNumTextView);

    }
}
