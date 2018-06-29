package com.fcn.park.getui_push;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.fcn.park.R;
import com.fcn.park.message.MessageInfoActivity;
import com.fcn.park.message.bean.SystemMessageBean;

import java.util.Random;

/**
 * Created by 860115001 on 2018/04/11.
 */

public class NewMessageNotification {

    private static final String NOTIFICATION_TAG = "NewMessage";

    public static void notify(final Context context,
                              SystemMessageBean.MessageListBean bean) {
        final Resources res = context.getResources();

        // This image is used as the notification's large icon (thumbnail).
        // TODO: Remove this if your notification has no relevant thumbnail.
        final Bitmap picture = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);

        final String title = bean.getTitle();
        final String text = bean.getMessage();
        Intent intent = new Intent(context, MessageInfoActivity.class);
        intent.putExtra(MessageInfoActivity.INFO_BEAN, bean);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLargeIcon(picture)
                .setTicker(title)
                .setSmallIcon(R.drawable.ic_vector_send)
                .setContentIntent(
                        PendingIntent.getActivity(
                                context,
                                0,
                                intent,
                                PendingIntent.FLAG_CANCEL_CURRENT))
                .setAutoCancel(true);

        notify(context, builder.build());
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static void notify(final Context context, final Notification notification) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Random random = new Random();
        long notifyId = 0;
        try {
            notifyId =Long.decode(System.currentTimeMillis() +""+ random.nextInt(10));
        } catch (Exception e) {
            e.printStackTrace();
        }
        nm.notify(NOTIFICATION_TAG, (int) notifyId, notification);
    }
}
