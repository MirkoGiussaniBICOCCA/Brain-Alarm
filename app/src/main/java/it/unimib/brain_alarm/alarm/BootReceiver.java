package it.unimib.brain_alarm.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Reimposta l'allarme qui
            // Puoi salvare l'orario dell'allarme in SharedPreferences e reimpostarlo
        }
    }
}


