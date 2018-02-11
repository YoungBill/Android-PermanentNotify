package com.android.permanentnotify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.widget.RemoteViews;
import android.widget.Toast;


/**
 * 常驻通知管理
 * Created by chentao on 2018/2/11.
 */

public class PermanentNotifyManager {

    public static final String ACTION_TOOLBAR = "com.notify.intent.action.toolbar";
    private static final int NOTIFICATION_TORCH_ID = 10002;
    private static final int NOTIFICATION_DATA_ID = 10003;
    private static final int NOTIFICATION_WIFI_ID = 10004;
    private static final int NOTIFICATION_LOGO_ID = 10005;
    private static final int NOTIFICATION_CLEAN_ID = 10006;
    private static final int NOTIFICATION_BOOST_ID = 10007;
    private static final int NOTIFICATION_ID = 123456;
    public static final String TAG_TOOLBAR_ID = "toolbar_id";


    /**
     * 创建常驻的notication
     */
    public static void createPermanentNotify(Context context) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.layout_notify_permanent);
        remoteView.setOnClickPendingIntent(R.id.ll_boost, getActivityPendingIntentForId(context, R.id.ll_boost, NOTIFICATION_BOOST_ID));
        remoteView.setOnClickPendingIntent(R.id.ll_clean, getActivityPendingIntentForId(context, R.id.ll_clean, NOTIFICATION_CLEAN_ID));
        remoteView.setOnClickPendingIntent(R.id.iv_icon, getActivityPendingIntentForId(context, R.id.iv_icon, NOTIFICATION_LOGO_ID));
        remoteView.setOnClickPendingIntent(R.id.ll_gprs, getActivityPendingIntentForId(context, R.id.ll_gprs, NOTIFICATION_DATA_ID));
        remoteView.setOnClickPendingIntent(R.id.ll_wifi, getActivityPendingIntentForId(context, R.id.ll_wifi, NOTIFICATION_WIFI_ID));
        remoteView.setOnClickPendingIntent(R.id.ll_torch, getActivityPendingIntentForId(context, R.id.ll_torch, NOTIFICATION_TORCH_ID));

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_background)
                //important
                .setOngoing(true)
                .setAutoCancel(false)
                .setContent(remoteView);
        Notification notification;
        if (Build.VERSION.SDK_INT >= 16) {
            builder.setPriority(Notification.PRIORITY_MAX);
            notification = builder.build();
        } else {
            notification = builder.getNotification();
            notification.contentView = remoteView;
        }
        manager.notify(NOTIFICATION_ID, notification);
    }

    public static void removeNotify(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(NOTIFICATION_ID);
    }

    private static PendingIntent getActivityPendingIntentForId(Context context, int id, int TAG) {
        Intent intent = null;
        switch (id) {
            case R.id.iv_icon:
                intent = new Intent();
                intent.setAction(ACTION_TOOLBAR);
                intent.putExtra(TAG_TOOLBAR_ID, TAG);
                break;

            case R.id.ll_clean:
                intent = new Intent();
                intent.setAction(ACTION_TOOLBAR);
                intent.putExtra(TAG_TOOLBAR_ID, TAG);
                break;

            case R.id.ll_boost:
                intent = new Intent();
                intent.setAction(ACTION_TOOLBAR);
                intent.putExtra(TAG_TOOLBAR_ID, TAG);
                break;

            case R.id.ll_gprs:
                intent = new Intent();
                intent.setAction(ACTION_TOOLBAR);
                intent.putExtra(TAG_TOOLBAR_ID, TAG);
                break;

            case R.id.ll_wifi:
                intent = new Intent();
                intent.setAction(ACTION_TOOLBAR);
                intent.putExtra(TAG_TOOLBAR_ID, TAG);
                break;

            case R.id.ll_torch:
                intent = new Intent();
                intent.setAction(ACTION_TOOLBAR);
                intent.putExtra(TAG_TOOLBAR_ID, TAG);
                break;

        }

        PendingIntent pendIntent = PendingIntent.getBroadcast(context, TAG, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        return pendIntent;
    }

    public static void registerToolbarBroadcastReceiver(final Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_TOOLBAR);
        context.registerReceiver(new ToolbarClickBroadcastReceiver(context), intentFilter);
    }

    private static class ToolbarClickBroadcastReceiver extends BroadcastReceiver {

        Context mContext;

        public ToolbarClickBroadcastReceiver(Context context) {
            super();
            mContext = context;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_TOOLBAR.equals(action)) {
                int toolbarId = intent.getIntExtra(TAG_TOOLBAR_ID, 0);
                try {
                    switch (toolbarId) {
                        case NOTIFICATION_LOGO_ID:
                            if (mContext != null) {
                                Intent logoIntent = new Intent();
                                logoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                logoIntent.setClassName(mContext.getPackageName(), "com.android.permanentnotify.MainActivity");
                                context.startActivity(logoIntent);
                            }

                            break;
                        case NOTIFICATION_CLEAN_ID:
                            if (mContext != null) {
                                Toast.makeText(mContext, "click clean", Toast.LENGTH_SHORT).show();
                            }
                            break;

                        case NOTIFICATION_BOOST_ID:
                            if (mContext != null) {
                                Toast.makeText(mContext, "click boost", Toast.LENGTH_SHORT).show();
                            }
                            break;

                        case NOTIFICATION_TORCH_ID:
                            if (null != mContext) {
                                Toast.makeText(mContext, "click torch", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case NOTIFICATION_WIFI_ID:
                            if (null != mContext) {
                                Toast.makeText(mContext, "click wifi", Toast.LENGTH_SHORT).show();
                            }
                            break;

                        case NOTIFICATION_DATA_ID:
                            if (null != mContext) {
                            }
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                }

            }
        }
    }
}
