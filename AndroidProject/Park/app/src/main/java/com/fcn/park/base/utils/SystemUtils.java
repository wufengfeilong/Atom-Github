package com.fcn.park.base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;

/**
 * Created by dongjunkun on 2015/11/24.
 */
public class SystemUtils {


    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | IllegalArgumentException | SecurityException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    public static float getDip(Context context, int px) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics());
    }


}
