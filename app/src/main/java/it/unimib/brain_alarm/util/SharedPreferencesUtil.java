package it.unimib.brain_alarm.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.activity.result.ActivityResultCallback;

import it.unimib.brain_alarm.AggiungiFragment;


public class SharedPreferencesUtil {

    private final Context context;


    public SharedPreferencesUtil(Context context) {
        this.context = context;
    }


    public void writeStringData(String sharedPreferencesFileName, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesFileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String readStringData(String sharedPreferencesFileName, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesFileName,
                Context.MODE_PRIVATE);
        return sharedPref.getString(key, null);
    }

    public void writeSveglie(String sharedPreferencesFileName, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesFileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.apply();
    }

    public String readSveglie(String sharedPreferencesFileName, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesFileName,
                Context.MODE_PRIVATE);
        return sharedPref.getString(key, null);
    }


}


