package it.unimib.brain_alarm.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("STOP_ALARM".equals(action)) {
            Intent serviceIntent = new Intent(context, AlarmService.class);
            context.stopService(serviceIntent);
        } else {
            Intent serviceIntent = new Intent(context, AlarmService.class);
            serviceIntent.putExtra("notification", true);
            context.startService(serviceIntent);
        }
    }
}


