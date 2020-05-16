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
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.LinkedList;
import java.util.Random;

import in.curioustools.a100daysofcode.RecyclerViewFiles.RvMainData;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.support.constraint.Constraints.TAG;

public class TempChecker {
    private Context c;

    public TempChecker(Context context) {
        this.c = context;
    }

    public Context getApplicationContext() {
        return c;
    }

    public void doWork() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences(Statics.SP_NAME, Context.MODE_PRIVATE);
        String lastcheckin = sp.getString(Statics.LAST_CHECKIN_DATE_r_str, Statics.NOT_SET);

        createNotificationWithButtons(sp);

    }


    private void createNotificationWithButtons(SharedPreferences sp) {
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService((NOTIFICATION_SERVICE));

        if (manager != null) {

            String channel_ID = "100DaysOfCode_ID";
            setNotificationChannel(manager, channel_ID);
            showNotif(manager, channel_ID, sp);
        }

    }

    private void setNotificationChannel(NotificationManager notifManager, String channel_ID) {
        Log.e(TAG, "setNotificationChannel: called");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e(TAG, "mobile is greater than android O");

            int notif_Importance = NotificationManager.IMPORTANCE_HIGH;
            String channelName = "100DaysOfCode";
            String channelDesc = "Recieves periodic notifications about current streak";

            NotificationChannel notifChannel;
            notifChannel = notifManager.getNotificationChannel(channel_ID);

            if (notifChannel == null) {
                notifChannel = new NotificationChannel(channel_ID, channelName, notif_Importance);
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

        String title = " Day : " + (sp.getInt(Statics.CURRENT_STREAK_COUNT_r_int, 0) + 1) + " / " + (sp.getInt(Statics.MAX_STREAK_COUNT_r_int, 100));
        String subtitle = " Have you coded for at least 1 hour Today?";
        String tickerData = subtitle;
        int notifID = 103;
        Bitmap largeIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_launcher);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channel_ID);
        builder.setContentTitle(title).setContentText(subtitle).setTicker(tickerData).setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(largeIcon).setColor(getApplicationContext().getResources().getColor(R.color.colorPrimary)).setColorized(true).setContentIntent(getOnClickIndents(PI.NOTIF)).addAction(new NotificationCompat.Action(0, "YES", getOnClickIndents(PI.ACTION_YES))).addAction(new NotificationCompat.Action(0, "NO", getOnClickIndents(PI.ACTION_NO)))
        ;

        Notification notif = builder.build();
        notifManager.notify(notifID, notif);
    }

    private enum PI {NOTIF, ACTION_YES, ACTION_NO}

    private PendingIntent getOnClickIndents(PI source) {
        PendingIntent resultPI = null;
        int notifRequestCode = 147;

        if (source == PI.NOTIF) {
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            resultPI = PendingIntent.getActivity(getApplicationContext(), notifRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
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


    public void temp() {
        LinkedList<RvMainData> origData = new LinkedList<>();
        origData.add(new RvMainData("Any sufficiently advanced technology is indistinguishable from magic.","Arthur C. Clarke"));
        origData.add(new RvMainData("I have not failed. I’ve just found 10,000 ways that won’t work.","Thomas Edison"));
        origData.add(new RvMainData("Innovation is the outcome of a habit, not a random act.","Sukant Ratnakar"));
        origData.add(new RvMainData("It’s not that we use technology, we live technology.","Godfrey Reggio"));
        origData.add(new RvMainData("Let’s go invent tomorrow instead of worrying about what happened yesterday.","Steve Jobs"));
        origData.add(new RvMainData("Modern technology has become a total phenomenon for civilization, the defining force of a new social order in which efficiency is no longer an option but a necessity imposed on all human activity.","Jacques Ellul"));
        origData.add(new RvMainData("Once a new technology rolls over you, if you’re not part of the steamroller, you’re part of the road.","Stewart Brand"));
        origData.add(new RvMainData("Our business is about technology, yes. But it’s also about operations and customer relationships.","Michael Dell"));
        origData.add(new RvMainData("Technology is a useful servant but a dangerous master.","Christian Lous Lange"));
        origData.add(new RvMainData("Technology is best when it brings people together.","Matt Mullenweg"));
        origData.add(new RvMainData("Technology is just a tool. In terms of getting the kids working together and motivating them, the teacher is the most important.","Bill Gates"));
        origData.add(new RvMainData("Technology is nothing. What’s important is that you have a faith in people, that they’re basically good and smart, and if you give them tools, they’ll do wonderful things with them.","Steve Jobs"));
        origData.add(new RvMainData("Technology like art is a soaring exercise of the human imagination.","Daniel Bell"));
        origData.add(new RvMainData("Technology should improve your life… not become your life.","Billy Cox"));
        origData.add(new RvMainData("The great growling engine of change – technology.","Alvin Toffler"));
        origData.add(new RvMainData("The real problem is not whether machines think but whether men do.","B.F. Skinner"));
        origData.add(new RvMainData("The technology you use impresses no one. The experience you create with it is everything.","Sean Gerety"));
        origData.add(new RvMainData("What new technology does is create new opportunities to do a job that customers want done.","Tim O’Reilly"));
        origData.add(new RvMainData("I have not failed. I've just found 10,000 ways that won't work.", "Thomas A. Edison"));
        origData.add(new RvMainData("Imperfection is beauty, madness is genius and it's better to be absolutely ridiculous than absolutely boring.", "Marilyn Monroe"));
        origData.add(new RvMainData("The opposite of love is not hate, it's indifference. The opposite of art is not ugliness, it's indifference. The opposite of faith is not heresy, it's indifference. And the opposite of life is not death, it's indifference.", "Elie Wiesel"));
        origData.add(new RvMainData("There are only two ways to live your life. One is as though nothing is a miracle. The other is as though everything is a miracle.", "Albert Einstein"));
        origData.add(new RvMainData("This life is what you make it. No matter what, you're going to mess up sometimes, it's a universal truth. But the good part is you get to decide how you're going to mess it up.", "Marilyn Monroe"));
        origData.add(new RvMainData("To the well-organized mind, death is but the next great adventure.", "J.K.Rowling"));
        origData.add(new RvMainData("A person's a person, no matter how small.", "Dr. Seuss"));
        origData.add(new RvMainData("Life isn't about finding yourself. Life is about creating yourself.", "George Bernard Shaw"));
        origData.add(new RvMainData("You may say I'm a dreamer, but I'm not the only one. I hope someday you'll join us. And the world will live as one.", "John Lennon"));


        LinkedList<RvMainData> result= new LinkedList<>();

        for(int i=0;i<=10;i++){
            long idx =System.currentTimeMillis() + new Random().nextInt()*200;

            RvMainData d =origData.get((int) (idx%origData.size()));
            origData.remove(d);

            result.add(d);
            }


    }
}