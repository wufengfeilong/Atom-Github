package com.fcn.park.manager.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;

import com.fcn.park.R;

/**
 * Created by lvzp on 2017/3/29.
 * 类描述：
 */

public class DialogUtils {

    public static AlertDialog.Builder buildAlertDialogWithCancel(Context context, String title, String message) {
        return new AlertDialog
                .Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    public static AlertDialog.Builder builderAlertDialog(Context context, String title, String message) {
        return new AlertDialog
                .Builder(context)
                .setTitle(title)
                .setMessage(message);
    }

    public static Dialog builderEmptyDialog(View contentView) {
        Dialog dialog = new Dialog(contentView.getContext());
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);/**/
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.bg_solid_white);
        }
        dialog.setContentView(contentView);
        return dialog;
    }

    public static void showSimpleDialog(Context context, String message, DialogInterface.OnClickListener listener) {
        buildAlertDialogWithCancel(context, "温馨提示", message)
                .setPositiveButton("确认", listener).show();
    }

}
