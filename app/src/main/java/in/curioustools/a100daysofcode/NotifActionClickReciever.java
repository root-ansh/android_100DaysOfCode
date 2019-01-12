package in.curioustools.a100daysofcode;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotifActionClickReciever extends BroadcastReceiver {
    public static final String ACTION_YES = "ACTION_YES";
    public static final String ACTION_NO = "ACTION_NO";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.e("MyReceiver", "MyAction received!");

        if (intent.getAction() != null && intent.getAction().equals(ACTION_YES)) {
            SharedPreferences sp =
                    context.getSharedPreferences(Statics.SP_NAME, Context.MODE_PRIVATE);

            int currStreak = sp.getInt(Statics.CURRENT_STREAK_COUNT_r_int, 0);
            sp.edit()
                    .putInt(Statics.CURRENT_STREAK_COUNT_r_int, currStreak + 1)
                    .putString(Statics.LAST_CHECKIN_DATE_r_str, Statics.getTodayString())
                    .apply();
        }
        NotificationManager manager =
                (NotificationManager) context.getSystemService((NOTIFICATION_SERVICE));
        manager.cancelAll();

    }
}
