package pace.cs389.team2.quickfitness.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


public class NotificationBuilder {

    public static final int NOTIFICATION_ID = 100;
    static NotificationCompat.Builder builder;
    static NotificationManager manager;
    static PendingIntent piOpenNotification;
    static PendingIntent piOpenMap;
    static PendingIntent piDismiss;

    public static void buildNotification(Context context, int iconResId,
                                         String title, String msg) {

        builder = new NotificationCompat.Builder(context);

        builder.setSmallIcon(iconResId);
        builder.setContentTitle(title);
        builder.setContentText(msg);
        builder.setDefaults(Notification.DEFAULT_ALL);

        builder.setAutoCancel(true);

        Intent intent = new Intent();
        piOpenNotification = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(piOpenNotification);

        Intent intentMap = new Intent();
        intent.setAction("com.android.training.mynotificationapp.openmap");
        piOpenMap = PendingIntent.getActivity(context, 0, intentMap,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentDismiss = new Intent();
        intent.setAction("com.android.training.mynotificationapp.dismiss");
        piDismiss = PendingIntent.getActivity(context, 0, intentDismiss,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(msg));

        //builder.addAction(R.drawable.ic_place_grey600_24dp, "Map", piOpenMap);

        //builder.addAction(R.drawable.ic_stat_dismiss, "Dismiss", piDismiss);

        manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(NOTIFICATION_ID, builder.build());

    }
}
