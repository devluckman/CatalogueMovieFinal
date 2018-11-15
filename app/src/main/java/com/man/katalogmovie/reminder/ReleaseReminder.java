package com.man.katalogmovie.reminder;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.man.katalogmovie.DetailActivity;
import com.man.katalogmovie.R;
import com.man.katalogmovie.model.ModelRespone;
import com.man.katalogmovie.model.Results;
import com.man.katalogmovie.utils.ApiClient;
import com.man.katalogmovie.utils.Language;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReleaseReminder extends BroadcastReceiver {
    private final int ID_RELEASE = 2;
    public static final String EXTRA_MESSAGE_RELEASE = "messageRelease";
    public static final String EXTRA_TYPE_RELEASE = "typeRelease";
    private Call<ModelRespone> apiCall;
    private ApiClient apiClient = new ApiClient();
    public ReleaseReminder() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        getUpcomingMovie(context);
    }

    private void getUpcomingMovie (final Context context){
        apiCall = apiClient.getApiInterface().MovieUpcoming(Language.getLanguage());
        apiCall.enqueue(new Callback<ModelRespone>() {
            @Override
            public void onResponse(Call<ModelRespone> call, Response<ModelRespone> response) {
                if (response.isSuccessful()) {
                    List<Results> list = response.body().getResults();
                    int index = new Random().nextInt(list.size());

                    Results results = list.get(index);
                    String title = list.get(index).getTitle();
                    String message = list.get(index).getOverview();
                    int notifId = 200;
                    showNotification(context,title, message, notifId, results);
                }else {
                    Toast.makeText(context, "Failed to Load Data", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ModelRespone> call, Throwable t) {
                Log.d("getUpComingMovie", "onFailure: " + t.toString());
            }
        });
    }

    public void setReminder(Context context, String type, String time, String message){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminder.class);
        intent.putExtra(EXTRA_MESSAGE_RELEASE, message);
        intent.putExtra(EXTRA_TYPE_RELEASE, type);
        String timeArr[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArr[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArr[1]));
        calendar.set(Calendar.SECOND, 0);

        int requestCode = ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode,intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
                pendingIntent);
        Toast.makeText(context, R.string.remindSave, Toast.LENGTH_SHORT).show();
    }

    private void showNotification(Context context, String title, String message, int notifId, Results results) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.KEY, new Gson().toJson(results));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }

    public void cancelReminder (Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminder.class);
        int requestCode = ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, R.string.remindCancel, Toast.LENGTH_SHORT).show();
    }
}
