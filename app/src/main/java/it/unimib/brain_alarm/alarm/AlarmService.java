package it.unimib.brain_alarm.alarm;

import android.Manifest;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.PowerManager;

import androidx.core.app.ActivityCompat;
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
        } else {
            // Aggiungi qui la logica per fermare il suono dell'allarme
            stopAlarm();
        }
    }

    private void showAlarmNotification() {
        Intent stopIntent = new Intent(this, AlarmReceiver.class);
        stopIntent.setAction("STOP_ALARM");
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Sveglia!")
                .setContentText("È ora di alzarsi!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setAutoCancel(true)
                .addAction(R.drawable.pausa, "Ferma", stopPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());

        // Aggiungi qui la logica per iniziare a suonare l'allarme
        startAlarm();
    }

    private void startAlarm() {
        // Logica per suonare l'allarme
    }

    private void stopAlarm() {
        // Logica per fermare il suono dell'allarme
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancel(NOTIFICATION_ID);
    }
}