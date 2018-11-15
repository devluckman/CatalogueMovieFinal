package com.man.katalogmovie;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;

import com.man.katalogmovie.reminder.DailyReminder;
import com.man.katalogmovie.reminder.ReleaseReminder;
import com.man.katalogmovie.reminder.SchedulePreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.dailyReminder)
    Switch dailyReminder;
    @BindView(R.id.relaseReminder)
    Switch relaseReminder;

    public static final String KEY_UPCOMING_REMIND = "checkedUpcoming";
    public static final String KEY_DAILY_REMIND = "checkedDaily";

    public static final String TYPE_REMINDER_RELEASE = "reminderRelase";
    public static final String TYPE_REMINDER_DAILY = "reminderDaily";

    public static final String HEADER_UPCOMING = "upcomingReminder";
    public static final String HEADER_DAILY = "upcomingDaily";

    public DailyReminder dailyReminderReceiver;
    public ReleaseReminder releaseReminderReceiver;
    public SchedulePreference schedulePreference;
    public SharedPreferences sReminderRelease, sReminderDaily;
    public SharedPreferences.Editor editorReminderRelease, editorReminderDaily;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        dailyReminderReceiver = new DailyReminder();
        releaseReminderReceiver = new ReleaseReminder();
        schedulePreference = new SchedulePreference(this);
        setPreference();

    }


    @OnCheckedChanged(R.id.dailyReminder)
    public void setDailyReminder(boolean isChecked){
        editorReminderDaily = sReminderDaily.edit();
        if (isChecked) {
            editorReminderDaily.putBoolean(KEY_DAILY_REMIND, true);
            editorReminderDaily.commit();
            dailyReminderOn();
        }else {
            editorReminderDaily.putBoolean(KEY_DAILY_REMIND, false);
            editorReminderDaily.commit();
            dailyReminderOff();
        }
    }

    @OnCheckedChanged(R.id.relaseReminder)
    public void setRelaseReminder(boolean isChecked){
        editorReminderRelease = sReminderRelease.edit();
        if (isChecked) {
            editorReminderRelease.putBoolean(KEY_UPCOMING_REMIND, true);
            editorReminderRelease.commit();
            releaseReminderOn();
        }else {
            editorReminderRelease.putBoolean(KEY_UPCOMING_REMIND, false);
            editorReminderRelease.commit();
            releaseReminderOff();
        }
    }


    private void setPreference (){
        sReminderRelease = getSharedPreferences(HEADER_UPCOMING, MODE_PRIVATE);
        sReminderDaily = getSharedPreferences(HEADER_DAILY, MODE_PRIVATE);
        boolean checkSwRelease = sReminderRelease.getBoolean(KEY_UPCOMING_REMIND, false);
        relaseReminder.setChecked(checkSwRelease);
        boolean checkSwDaily = sReminderDaily.getBoolean(KEY_DAILY_REMIND, false);
        dailyReminder.setChecked(checkSwDaily);

    }

    private void releaseReminderOn(){
        String time = "08:00";
        String message = "Release Movie, come back soon";
        schedulePreference.setDailyTime(time);
        schedulePreference.setDailyMessage(message);
        dailyReminderReceiver.setReminder(SettingActivity.this, TYPE_REMINDER_RELEASE, time, message );
    }
    private void releaseReminderOff(){
        dailyReminderReceiver.cancelReminder(SettingActivity.this);
    }

    private void dailyReminderOn(){
        String time = "07:00";
        String message = "Daily Movie Not Available, come back soon";
        schedulePreference.setDailyTime(time);
        schedulePreference.setDailyMessage(message);
        dailyReminderReceiver.setReminder(SettingActivity.this, TYPE_REMINDER_DAILY, time, message );
    }
    private void dailyReminderOff(){
        dailyReminderReceiver.cancelReminder(SettingActivity.this);
    }
}
