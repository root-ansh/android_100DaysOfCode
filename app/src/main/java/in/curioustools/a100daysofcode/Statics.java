package in.curioustools.a100daysofcode;


import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Random;

import in.curioustools.a100daysofcode.RecyclerViewFiles.RvMainData;

public class  Statics {
    public static final String SP_NAME = "StreakData";

    //psfs PREF_KEY_r_dtype :the key which should be used for clling and the returntye of the value which the key will return
    public static final String START_DATE_r_str = "START_DATE";
    public static final String LAST_CHECKIN_DATE_r_str = "LAST_CHECKIN_DATE";

    public static final String CURRENT_STREAK_COUNT_r_int = "CURR_STREAK_COUNT";
    public static final String MAX_STREAK_COUNT_r_int = "TOT_STREAK_COUNT";
    public static final String MISSED_COUNT_r_int = "TOT_MISSED_COUNT";

    public static final String STREAK_NAME_r_str ="STREAK_NAME" ;

    public static final String NOTIFY_SETTINGS_r_int012 ="NOTIFY_SETTINGS";
    public static final String NOTIFY_TIME_r_str = "NOTIFY_TIME";
    public static final String NOT_SET ="not set" ;


    public static String getTodayString() {
        //      IMP: pattern here is "MMM dd, yyyy." .
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy.", Locale.getDefault());
        return df.format(c);
    }
    public static String getCurrentTime() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("hh:mm", Locale.getDefault());
        return df.format(c);
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

    public static LinkedList<RvMainData> getQuotes(){
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

        return result;


    }


}
