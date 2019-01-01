package in.curioustools.a100daysofcode;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

class Statics {

    public static final String SP_FILENAME = "StreakData";
    public static final String START_DATE_str = "START_DATE";
    public static final String LAST_CHECKIN_DATE_str = "LAST_CHECKIN_DATE";
    public static final String CURRENT_STREAK_COUNT_str = "CURR_STREAK_COUNT";
    public static final String TOTAL_STREAK_COUNT_str = "TOT_STREAK_COUNT";
    public static final String IS_TODAY_UPDATED_str = "IS_TODAY_UPDATED";

    public static String getToday() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy.", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }
}
