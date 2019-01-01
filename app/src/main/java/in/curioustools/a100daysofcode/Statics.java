package in.curioustools.a100daysofcode;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class  Statics {
    public static final String SP_FILENAME = "StreakData";
    public static final String START_DATE_str = "START_DATE";
    public static final String LAST_CHECKIN_DATE_str = "LAST_CHECKIN_DATE";
    public static final String CURRENT_STREAK_COUNT_str = "CURR_STREAK_COUNT";
    public static final String TOTAL_STREAK_COUNT_str = "TOT_STREAK_COUNT";

    public static String getToday() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy.", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public static int compareDateStrings(String dateStr1, String dateStr2) {
//      IMP: pattern here is "MMM dd, yyyy." .
        Date date1 = null;
        Date date2 = null;

        try {
            date1 = new SimpleDateFormat("MMM dd, yyyy.", Locale.ENGLISH).parse(dateStr1);
            date2 = new SimpleDateFormat("MMM dd, yyyy.", Locale.US).parse(dateStr2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Long.compare(date1.getTime(), date2.getTime()); //returns +1,-1 or 0
    }

}
