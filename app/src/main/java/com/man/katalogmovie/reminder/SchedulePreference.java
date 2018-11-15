package com.man.katalogmovie.reminder;

import android.content.Context;
import android.content.SharedPreferences;

public class SchedulePreference {
    public static final String NAME_PREF = "reminderPreference";
    public static final String KEY = "keyTime";
    public static final String KEY_MESSAGE_DAILY = "keyMessageDaily";
    public static final String KEY_MESSAGE_RELEASE = "keyMessageDaily";


    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;

    public SchedulePreference(Context context) {
        preferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setDailyTime(String time){
        editor.putString(KEY, time);
        editor.commit();
    }

    public void setDailyMessage(String message){
        editor.putString(KEY_MESSAGE_DAILY, message);
    }

    public void setReleaseTime(String time){
        editor.putString(KEY, time);
        editor.commit();
    }

    public void setReleaseMessage(String message){
        editor.putString(KEY_MESSAGE_RELEASE, message);
    }

}
