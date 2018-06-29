package com.fcn.park.base.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fcn.park.R;
import com.fcn.park.base.utils.InputMethodUtils;
import com.fcn.park.base.utils.RECheckUtils;

/**
 * Created by lvzp on 2017/3/17.
 * 类描述：
 */

public class InputDataPopWindow extends TextTitlePopWindow {

    public static final int INPUT_TYPE_ZIP_CODE = 1;
    public static final int INPUT_TYPE_PHONE = 2;
    public static final int INPUT_TYPE_EMAIL = 3;
    public static final int INPUT_TYPE_WEB = 4;
    public static final int INPUT_TYPE_TEL = 5;
    public static final int INPUT_TYPE_TEXT = -1;

    @IntDef({INPUT_TYPE_EMAIL, INPUT_TYPE_PHONE, INPUT_TYPE_ZIP_CODE, INPUT_TYPE_WEB, INPUT_TYPE_TEL, INPUT_TYPE_TEXT})
    public @interface InputType {
    }

    protected EditText mInputEdit;
    protected int mInputType = -1;
    protected int mMaxInputLength;
    private boolean isCheckMaxInputLength;
    private TextView mTvMaxInputNum;
    private Context mContext;
    protected boolean isSingleLine;

    /**
     * 初始化一个PopupWindow
     *
     * @param context 上下文对象
     */
    public InputDataPopWindow(final Activity context) {
        super(context, R.layout.layout_pop_input_data);

        mContext = context;

        mInputEdit = (EditText) getContentView().findViewById(R.id.et_input_content);
        mTvMaxInputNum = (TextView) getContentView().findViewById(R.id.tv_max_input_num);

        mRightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickSaveCallback != null) {
                    String inputText = mInputEdit.getText().toString().trim();
                  /*  if (TextUtils.isEmpty(inputText)) {
                        return;
                    }*/
                    if (isCheckMaxInputLength && mInputEdit.getText() != null && mInputEdit.getText().toString().length() > mMaxInputLength) {
                        Toast.makeText(context, "输入的长度超长", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mInputType > 0) {
                        switch (mInputType) {
                            case INPUT_TYPE_ZIP_CODE:
//                                if (!RECheckUtils.checkPostCode(inputText)) { //邮编为非必填项
//                                    Toast.makeText(context, "对不起，邮编格式不合法", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
                                break;
                            case INPUT_TYPE_EMAIL:
                                if (!RECheckUtils.checkPersionEmail(inputText)) {
                                    Toast.makeText(context, "对不起，邮箱格式不合法", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                break;
                            case INPUT_TYPE_PHONE:
                                if (!RECheckUtils.checkPhoneNum(inputText) && !RECheckUtils.checkLandline(inputText)) {
                                    Toast.makeText(context, "对不起，请输入正确的联系电话", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                break;
                            case INPUT_TYPE_WEB:
                                if (!inputText.equals("") && !RECheckUtils.checkUrl(inputText)) {
                                    Toast.makeText(context, "对不起，网址格式不合法", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                break;
                            case INPUT_TYPE_TEL:
                                if (!RECheckUtils.checkLandline(inputText)) {
                                    Toast.makeText(context, "对不起，座机号格式不合法", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                break;
                        }
                    }
                    mClickSaveCallback.onSaveCallback(inputText);
                }
                dismiss();
            }
        });
    }

    public void showPopupWindow(String inputText, View parent) {
        InputMethodUtils.openInputMethod(parent.getContext());
        checkInputType();
        mInputEdit.setText(inputText);
        //将游标移至设置的index位置
        mInputEdit.setSelection(mInputEdit.length());
        if (isSingleLine) {
            mInputEdit.setSingleLine();
        }
        super.showPopupWindow(parent);
    }

    public void setCheckInputMaxLength(int maxLength) {
        mMaxInputLength = maxLength;
        isCheckMaxInputLength = true;
        mTvMaxInputNum.setVisibility(View.VISIBLE);
        mInputEdit.addTextChangedListener(mTextWatcher);
    }

    @Override
    public void dismiss() {
        //在页面关闭的时候，重置输入框中的所有状态
        mInputType = -1;
        mTvMaxInputNum.setVisibility(View.GONE);
        mInputEdit.removeTextChangedListener(mTextWatcher);
        isCheckMaxInputLength = false;
        super.dismiss();
    }

    public void showPopupWindow(String inputText, @InputType int inputType, View parent) {
        mInputType = inputType;
        showPopupWindow(inputText, parent);
    }

    public void setInputType(@InputType int inputType) {
        mInputType = inputType;
        checkInputType();
    }

    private void checkInputType() {
        int inputLength;
        switch (mInputType) {
            case INPUT_TYPE_WEB:
                mInputEdit.setInputType(EditorInfo.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
                inputLength = 100;
                break;
            case INPUT_TYPE_EMAIL:
                inputLength = 50;
                mInputEdit.setInputType(EditorInfo.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
                break;
            case INPUT_TYPE_TEL:
                mInputEdit.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                inputLength = 20;
                break;
            case INPUT_TYPE_PHONE:
                mInputEdit.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                inputLength = 20;
                break;
            case INPUT_TYPE_ZIP_CODE:
                mInputEdit.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                inputLength = 6;
                break;
            default:
                mInputEdit.setInputType(EditorInfo.TYPE_CLASS_TEXT);
                mInputEdit.setLines(7);
                mInputEdit.setMaxLines(7);
                //这两行解决破popupwindow中的edittext不换行的问题(wq)
                mInputEdit.setSingleLine(false);
                mInputEdit.setHorizontallyScrolling(false);

                mInputEdit.setFilters(new InputFilter[0]);
                return;
        }
        mInputEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(inputLength)});
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > mMaxInputLength) {
                if (mTvMaxInputNum.getTextColors().getDefaultColor() == ContextCompat.getColor(mContext, R.color.colorTextBlack)) {
                    mTvMaxInputNum.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
                }
            } else {
                if (mTvMaxInputNum.getTextColors().getDefaultColor() == ContextCompat.getColor(mContext, R.color.colorRed)) {
                    mTvMaxInputNum.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextBlack));
                }
            }
            mTvMaxInputNum.setText(mContext.getString(R.string.max_input_length, s.length(), mMaxInputLength));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
