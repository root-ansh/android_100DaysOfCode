package in.curioustools.a100daysofcode;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static android.content.Context.NOTIFICATION_SERVICE;

public class PeriodicNotifyWorker extends Worker {
    private static final String TAG = "PeriodicNotifyWorker";

    public PeriodicNotifyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        Log.e(TAG, "PeriodicNotifyWorker: constructor called" );
    }

    @NonNull
    @Override
    public Result doWork() {
//        Log.e(TAG, "doWork: called" );

        SharedPreferences sp =
                getApplicationContext().getSharedPreferences(Statics.SP_NAME, Context.MODE_PRIVATE);
        String lastcheckin = sp.getString(Statics.LAST_CHECKIN_DATE_r_str, Statics.NOT_SET);
//        Log.e(TAG, "doWork: checking shared preferences for last checkin:"+lastcheckin );

        if (Statics.compareDateStrings(lastcheckin, Statics.getTodayString()) == -1) {
            Log.e(TAG, "doWork: last checkin is smaller than today's date, so calling creating notification" );
            return createNotificationWithButtons(sp);
        }
        else {
            Log.e(TAG, "doWork: last checkin is bigger than today's date, so no need for notif" );
            return Result.success();
        }
    }


    private Result createNotificationWithButtons(SharedPreferences sp) {
        NotificationManager manager =
                (NotificationManager) getApplicationContext().getSystemService((NOTIFICATION_SERVICE));
        String channel_ID = "100DaysOfCode_ID";
        if (manager != null) {
            setNotificationChannel(manager,channel_ID);
            showNotif(manager, channel_ID, sp);
            return Result.success();
        }
        else {
            return Result.failure();
        }
    }


    private void setNotificationChannel(NotificationManager notifManager, String channel_ID) {
        Log.e(TAG, "setNotificationChannel: called");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e(TAG, "mobile is greater than android O");

            int notif_Importance = NotificationManager.IMPORTANCE_HIGH;
            String channelName = "DailyStreakNotif";
            String channelDesc = "Recieves periodic notifications about current streak";

            NotificationChannel notifChannel;
            notifChannel = notifManager.getNotificationChannel(channel_ID);

            if (notifChannel == null) {
                notifChannel = new NotificationChannel(channel_ID, channelName, notif_Importance);
                notifChannel.setBypassDnd(true);
                notifChannel.setDescription(channelDesc);
                notifChannel.enableLights(true);
                notifChannel.setLightColor(Color.BLUE);
                notifChannel.enableVibration(true);
                notifChannel.setVibrationPattern(new long[]{100, 200});
            } else {
                Log.e(TAG, "setNotificationChannel: already channel present, returnig old channel: " + notifChannel);
            }
            notifManager.createNotificationChannel(notifChannel);
        }
    }

    private void showNotif(NotificationManager notifManager, String channel_ID, SharedPreferences sp) {
        notifManager.cancelAll();

        String title = " Day : " +
                (sp.getInt(Statics.CURRENT_STREAK_COUNT_r_int, 0) + 1) + " / " +
                (sp.getInt(Statics.MAX_STREAK_COUNT_r_int, 100));
        String subtitle = " Have you coded for at least 1 hour Today?";
        String tickerData = subtitle;
        int notifID = 103;
        Bitmap largeIcon = BitmapFactory.decodeResource(
                getApplicationContext().getResources(), R.mipmap.ic_launcher);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channel_ID);
        builder
                .setContentTitle(title)
                .setContentText(subtitle)
                .setTicker(tickerData)
                .setColor(getApplicationContext().getResources().getColor(R.color.colorPrimary))
                .setColorized(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(largeIcon)
                .setContentIntent(getOnClickIndents(PI.NOTIF))
                .addAction(new NotificationCompat.Action(0, "YES", getOnClickIndents(PI.ACTION_YES)))
                .addAction(new NotificationCompat.Action(0, "NO", getOnClickIndents(PI.ACTION_NO)))
        ;


        Notification notif = builder.build();
        notifManager.notify(notifID, notif);
    }

    private enum PI {NOTIF, ACTION_YES, ACTION_NO}

    private PendingIntent getOnClickIndents(PI source) {
        PendingIntent resultPI = null;
        int notifRequestCode = 147;

        if (source == PI.NOTIF) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            resultPI = PendingIntent.getActivity(getApplicationContext(), notifRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        } else if (source == PI.ACTION_YES) {

            Intent intent = new Intent(getApplicationContext(), NotifActionClickReciever.class);
            intent.setAction(NotifActionClickReciever.ACTION_YES);
            resultPI = PendingIntent.getBroadcast(getApplicationContext(), notifRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        } else if (source == PI.ACTION_NO) {

            Intent intent = new Intent(getApplicationContext(), NotifActionClickReciever.class);
            intent.setAction(NotifActionClickReciever.ACTION_NO);
            resultPI = PendingIntent.getBroadcast(getApplicationContext(), notifRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        return resultPI;

    }


}

