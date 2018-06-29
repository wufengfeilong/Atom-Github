package com.fcn.park;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.fcn.park.base.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by 860116042 on 2018/4/19.
 * 类描述：
 */

public class DateSelectDialog extends Dialog implements NumberPicker.OnValueChangeListener {


    private OnClickListener mOnClickListener;
    private DatePicker mYearPicker;
    private DatePicker mMonthPicker;
    private TextView mCurrentTime;

    private int mCurrentYearValue;
    private int mCurrentMonthValue;

    private Calendar mCalendar;
    private boolean isNow = true;
    private boolean isOutnumberCurrentDate;//判断选择的日期是否可以大于当前的日期

    public DateSelectDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog_date_pickper);
        mCalendar = Calendar.getInstance();
        mCurrentYearValue = mCalendar.get(Calendar.YEAR);
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(android.R.style.Animation_Dialog);
        }
        init();
    }

    /**
     * 设置当前选中的时间
     *
     * @param currentTime
     */
    @SuppressLint("WrongConstant")
    public void setCurrentTime(long currentTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTime);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        mCurrentYearValue = year;
        mCurrentMonthValue = month;
        updateTitle();
        mYearPicker.setValue(mCurrentYearValue);
        mMonthPicker.setValue(mCurrentMonthValue);
    }

    private void init() {

        mYearPicker = (DatePicker) findViewById(R.id.num_year);
        mMonthPicker = (DatePicker) findViewById(R.id.num_month);
        mCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        TextView tvCancel = (TextView) findViewById(R.id.tv_cancel);
        TextView tvConfirm = (TextView) findViewById(R.id.tv_confirm);

        mCurrentMonthValue = mCalendar.get(Calendar.MONTH) + 1;

        mYearPicker.setMinValue(1970);
        mYearPicker.setMaxValue(mCurrentYearValue);
        mYearPicker.setValue(mCalendar.get(Calendar.YEAR));
        mMonthPicker.setMinValue(1);
        mMonthPicker.setMaxValue(12);
        mMonthPicker.setValue(mCurrentMonthValue);
        mYearPicker.setWrapSelectorWheel(false);

        mYearPicker.setOnValueChangedListener(this);
        mMonthPicker.setOnValueChangedListener(this);

        DatePicker.setNumberPickerDividerColor(mYearPicker);
        DatePicker.setNumberPickerDividerColor(mMonthPicker);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOutnumberCurrentDate) {
                    if (mCurrentYearValue >= mCalendar.get(Calendar.YEAR)) {
                        if (mCurrentMonthValue > mCalendar.get(Calendar.MONTH) + 1) {
                            Toast.makeText(getContext(), "不可超越当前日期", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(String.valueOf(mCurrentYearValue), String.valueOf(mCurrentMonthValue), isNow);
                }
                dismiss();
                //reset();
            }
        });
    }

    private void reset() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        mCurrentYearValue = year;
        mCurrentMonthValue = month;
        mYearPicker.setValue(year);
        mMonthPicker.setValue(month);
        mCurrentTime.setText("至今");
        isNow = true;
    }

    public DateSelectDialog setOnClickListener(OnClickListener listener) {
        mOnClickListener = listener;
        return this;
    }

    /**
     * 设置当前的选择的时间是否可以超出当前的时间
     *
     * @param outnumberCurrentDate 如果为true，那么在初始化数据的时候，年份就会加10年;如果为 false 那么在选择数据的时候就会判断是否大于当前的时间
     * @return
     */
    public DateSelectDialog setOutnumberCurrentDate(boolean outnumberCurrentDate) {
        isOutnumberCurrentDate = outnumberCurrentDate;
        if (isOutnumberCurrentDate) {
            mYearPicker.setMaxValue(mCurrentYearValue + 10);
        }
        return this;
    }

    public boolean isNow() {
        return isNow;
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        switch (picker.getId()) {
            case R.id.num_year:
                mCurrentYearValue = newVal;
                break;
            case R.id.num_month:
                mCurrentMonthValue = newVal;
                break;
        }
        updateTitle();
    }

    private void updateTitle() {
        String currentTime = mCurrentYearValue + "-" + mCurrentMonthValue;
        if (mCurrentYearValue == Math.abs(mCalendar.get(Calendar.YEAR)) && mCurrentMonthValue == mCalendar.get(Calendar.MONTH) + 1) {
            isNow = true;
            currentTime = "至今";
        } else {
            isNow = false;
        }
        mCurrentTime.setText(currentTime);
    }


    public interface OnClickListener {
        /**
         * 当点击确认时的回调方法
         * @param year
         * @param month
         * @param isNow
         */
        void onClick(String year, String month, boolean isNow);
    }

}
