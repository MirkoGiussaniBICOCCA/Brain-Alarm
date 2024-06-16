package it.unimib.brain_alarm.alarm;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import it.unimib.brain_alarm.R;
import it.unimib.brain_alarm.ui.MainActivity;

public class AlarmService extends IntentService {
    private static final int NOTIFICATION_ID = 1;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        boolean showNotification = intent.getBooleanExtra("notification", false);

        if (showNotification) {
            showAlarmNotification();
        }
    }

    private void showAlarmNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Sveglia!")
                .setContentText("È ora di alzarsi!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
