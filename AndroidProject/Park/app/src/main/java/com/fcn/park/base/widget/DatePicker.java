package com.fcn.park.base.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.fcn.park.R;

import java.lang.reflect.Field;

/**
 * Created by 860116042 on 2018/4/19.
 * 类描述：
 */

public class DatePicker extends NumberPicker {


    public DatePicker(Context context) {
        super(context);
    }

    public DatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    public void updateView(View view) {
        if (view instanceof EditText) {
            //这里修改字体的属性
            ((EditText) view).setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorAccent));
            ((EditText) view).setTextSize(16);
            view.setEnabled(false);
            view.setFocusable(false);
            view.setFocusableInTouchMode(false);
        }
    }

    public static void setNumberPickerDividerColor(NumberPicker numberPicker) {
        NumberPicker picker = numberPicker;
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        boolean[] isChangeOver = new boolean[2];
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    pf.set(picker, new ColorDrawable(ContextCompat.getColor(numberPicker.getContext(), R.color.colorBlue)));
                } catch (IllegalArgumentException | Resources.NotFoundException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                isChangeOver[0] = true;
                continue;
            }
            if (pf.getName().equals("mSelectionDividerHeight")) {
                pf.setAccessible(true);
                try {
                    int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, numberPicker.getContext().getResources().getDisplayMetrics());
                    //设置分割线的高度
                    pf.set(picker, height);
                } catch (IllegalArgumentException | Resources.NotFoundException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                isChangeOver[1] = true;
                continue;
            }
            if (isChangeOver[0] && isChangeOver[1]) {
                break;
            }
        }
    }

}
