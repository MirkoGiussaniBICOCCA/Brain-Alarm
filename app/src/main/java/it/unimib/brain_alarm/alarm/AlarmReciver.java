package it.unimib.brain_alarm.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent serviceIntent = new Intent(context, AlarmService.class);
            serviceIntent.putExtra("notification", true);
            context.startService(serviceIntent);
        }
}


