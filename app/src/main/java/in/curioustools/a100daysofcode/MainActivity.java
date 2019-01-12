package in.curioustools.a100daysofcode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import in.curioustools.a100daysofcode.RecyclerViewFiles.RvAdapter;

import static in.curioustools.a100daysofcode.Statics.NOT_SET;

public class MainActivity extends AppCompatActivity {
    //todo convert this app to Habit maker app: adda plus button for every new habit tracker and connect with a database
    //todo ignore first we will just add swipeable quotes with recycler view for now
    //todo make a screen saying info , giving info about the 100 days of code
    //todo in the long run convert this app to "habitmaker" by introducing a screen saying
    //todo "ready to make a habit? with a 'youtube logo'-like button, which onclicking will rise a bottomsheet

    TextView tvStreakTitle, tvLastCheckIn, tvStartDate, tvCurrentCounter, tvMissedCounter;
    Button btCodedYes, btCodedNo, btShare;
    ImageButton btMissedInc, btMissedDec;
    LinearLayout llAskUserResp, llShare, llbottomsheet;
    RecyclerView rvQuotes;
    CoordinatorLayout layoutMain;

    private String temp_lastCheckInDate = "";
    private int temp_currCount = 0;

    SettingsBottomSheet settingsBS;

    SharedPreferences sp_OnCreate;
    SharedPreferences.OnSharedPreferenceChangeListener sp_Listener = getSpListener();

    private static final String TAG = "$$$113$$$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //database initialisation
        sp_OnCreate = getSharedPreferences(Statics.SP_NAME, MODE_PRIVATE);


        // Ui Initialisation
        Objects.requireNonNull(getSupportActionBar()).hide();
        removeStatusBarColor();
        initUI();

        setDailyNotifier();
        initUIOnCicks();

        Log.e(TAG, "onCreate: successfully ran");


    }



    private void removeStatusBarColor() {
        //wrap it in a if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){} check
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);// will remove all window bounds of our activity
    }

    private void setDailyNotifier() {
        Log.e(TAG, "setDailyNotifier: called");

        Constraints.Builder constraintsBuilder = new Constraints.Builder();

        constraintsBuilder.setRequiresBatteryNotLow(false);
        constraintsBuilder.setRequiredNetworkType(NetworkType.NOT_REQUIRED);
        constraintsBuilder.setRequiresCharging(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            constraintsBuilder.setRequiresDeviceIdle(false);
        }
        Constraints constraints = constraintsBuilder.build();


        PeriodicWorkRequest.Builder builder = new PeriodicWorkRequest
                .Builder(PeriodicNotifyWorker.class, 2, TimeUnit.HOURS, 5, TimeUnit.MINUTES);
//        Log.e(TAG, "setDailyNotifier: setted up builder:"+builder );


        builder.setConstraints(constraints);
//        Log.e(TAG, "setDailyNotifier: added constraint" );


        PeriodicWorkRequest request = builder.build();

//        Log.e(TAG, "setDailyNotifier: created request:"+request );

        String REQUEST_ID = "PERIODIC_NOTIFY_ID";

//      WorkManager.getInstance().enqueue(request);
        WorkManager.getInstance().enqueueUniquePeriodicWork(REQUEST_ID, ExistingPeriodicWorkPolicy.KEEP, request);
        Log.e(TAG, "setDailyNotifier: successfully ran");
//        Log.e(TAG, "setDailyNotifier: successfully enqueued the request");

    }

    private void initUI() {
        tvStreakTitle = findViewById(R.id.text_ttitle);
        tvLastCheckIn = findViewById(R.id.text_date_updated_on);
        tvStartDate = findViewById(R.id.text_date_started); // current date or last checkin date???
        tvMissedCounter = findViewById(R.id.text_missed_counter);
        tvCurrentCounter = findViewById(R.id.text_total_counter);

        btCodedYes = findViewById(R.id.bt_coded_yes);
        btCodedNo = findViewById(R.id.bt_coded_no);
        btShare = findViewById(R.id.bt_share);
        btMissedDec = findViewById(R.id.bt_im_dec_missed);
        btMissedInc = findViewById(R.id.bt_im_inc_missed);

        llAskUserResp = findViewById(R.id.layout_user_response);
        llShare = findViewById(R.id.layout_share);

        rvQuotes = findViewById(R.id.rv_quotes);
        rvQuotes.setLayoutManager(new LinearLayoutManager(this));
        rvQuotes.setAdapter(new RvAdapter(Statics.getQuotes()));
        rvQuotes.setHasFixedSize(true);

        layoutMain = findViewById(R.id.layout_main);

        llbottomsheet = findViewById(R.id.layout_bottomsheet_settings);


        setUIData(sp_OnCreate);
        //this line is highly dangerous, because not does this "init" bottomsheet, but creates a whole environment inside it
        settingsBS = new SettingsBottomSheet(llbottomsheet, sp_OnCreate);

        Log.e(TAG, "initUI:  successfully ran");


    }

    //    warning : extreamly coupled function, used by many functions modify with care.
    private void setUIData(SharedPreferences sp) {
        if (sp == null) {
            sp = this.getSharedPreferences(Statics.SP_NAME, MODE_PRIVATE);
        }

        String title = sp.getString(Statics.STREAK_NAME_r_str, "#100DaysofCode");
        String streakStarted = sp.getString(Statics.START_DATE_r_str, Statics.getTodayString());
        String streakLastCheckin = sp.getString(Statics.LAST_CHECKIN_DATE_r_str, NOT_SET);

        String currentStreak = "" + sp.getInt(Statics.CURRENT_STREAK_COUNT_r_int, 0);
        String maxStreak = "" + sp.getInt(Statics.MAX_STREAK_COUNT_r_int, 100);
        String missed = "" + sp.getInt(Statics.MISSED_COUNT_r_int, 0);

        tvStreakTitle.setText(title);
        tvLastCheckIn.setText(streakLastCheckin);
        tvStartDate.setText(streakStarted);
        tvMissedCounter.setText(missed);
        tvCurrentCounter.setText(MessageFormat.format("{0}/{1}", currentStreak, maxStreak));

        if (Objects.requireNonNull(streakLastCheckin).equals(Statics.getTodayString())) {
            llAskUserResp.setVisibility(View.GONE);
            llShare.setVisibility(View.VISIBLE);
        } else {
            llAskUserResp.setVisibility(View.VISIBLE);
            llShare.setVisibility(View.GONE);
        }



        Log.e(TAG, "setUIData: successfully ran");
    }

    private void initUIOnCicks() {
        btMissedInc.setOnClickListener(v -> {
            int missed = sp_OnCreate.getInt(Statics.MISSED_COUNT_r_int, 0);
            sp_OnCreate.edit().putInt(Statics.MISSED_COUNT_r_int, missed + 1).apply();
            //will be automatically updated in ui when  sp change listener trigers setUIData().
        });

        btMissedDec.setOnClickListener(v -> {
            int missed = sp_OnCreate.getInt(Statics.MISSED_COUNT_r_int, 0);
            missed = (missed - 1 <= 0) ? 0 : (missed - 1);
            sp_OnCreate.edit().putInt(Statics.MISSED_COUNT_r_int, missed).apply();
            //will be automatically updated in ui when  sp change listener trigers setUIData().
        });
        tvStreakTitle.setOnClickListener(v -> settingsBS.toggleBottomSheet());

        btCodedYes.setOnClickListener(v -> {
            //1. change ui( automatically being done by the pref changelisteners)
            //2. upload the data on sharedPref
            updateSharedPref(Source.INCREASE);
            // 3 show snackbar
            String msg = "Keep Coding!";
            Snackbar s = Snackbar.make(layoutMain, msg, Snackbar.LENGTH_LONG);

            s.setAction("undo", v1 -> updateSharedPref(Source.UNDO));

            View snackbarView = s.getView();
            int navbarHeight = 150;
            int navbarSides =50;
            CoordinatorLayout.LayoutParams parentParams = (CoordinatorLayout.LayoutParams) snackbarView.getLayoutParams();
            parentParams.setMargins(navbarSides, 0, navbarSides,  navbarHeight);
            snackbarView.setLayoutParams(parentParams);
            s.show();
        });
        btCodedNo.setOnClickListener(v -> {
            String msg = "Will show a notification again in next 2 hours";
            Snackbar s = Snackbar.make(layoutMain, msg, Snackbar.LENGTH_LONG);
            s.setAction("settings", v12 -> settingsBS.toggleBottomSheet());

            View snackbarView = s.getView();
            int navbarHeight = 150;
            int navbarSides =50;
            CoordinatorLayout.LayoutParams parentParams = (CoordinatorLayout.LayoutParams) snackbarView.getLayoutParams();
            parentParams.setMargins(navbarSides, 0, navbarSides,  navbarHeight);
            snackbarView.setLayoutParams(parentParams);

            s.show();
        });
        btShare.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ShareProgressActivity.class));
            Log.e(TAG, "initUIOnCicks: successfully ran");

        });

        Log.e(TAG, "initUIOnCicks: successfully ran");
    }

    private enum Source {INCREASE, UNDO}

    private void updateSharedPref(Source SOURCE) {  // to be called ONLY after shared pref is registered

        if (SOURCE == Source.INCREASE) {

            //Store the old values, in case if pressed undo
            temp_currCount = sp_OnCreate.getInt(Statics.CURRENT_STREAK_COUNT_r_int, 0);
            temp_lastCheckInDate = sp_OnCreate.getString(Statics.LAST_CHECKIN_DATE_r_str, NOT_SET);

            // update new values
            sp_OnCreate.edit()
                    .putInt(Statics.CURRENT_STREAK_COUNT_r_int, temp_currCount + 1)
                    .putString(Statics.LAST_CHECKIN_DATE_r_str, Statics.getTodayString())
                    .apply();
        } else if (SOURCE == Source.UNDO) {
            sp_OnCreate.edit()
                    .putInt(Statics.CURRENT_STREAK_COUNT_r_int, temp_currCount)
                    .putString(Statics.LAST_CHECKIN_DATE_r_str, temp_lastCheckInDate)
                    .apply();
        }
    }

    private SharedPreferences.OnSharedPreferenceChangeListener getSpListener() {
        return (sp, key) -> {
            MainActivity.this.setUIData(sp);
            settingsBS.updateLBSdataPubicVersion();
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sp_OnCreate == null) {
            sp_OnCreate = getSharedPreferences(Statics.SP_NAME, MODE_PRIVATE);
        }
        sp_OnCreate.registerOnSharedPreferenceChangeListener(sp_Listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sp_OnCreate != null) {
            sp_OnCreate.unregisterOnSharedPreferenceChangeListener(sp_Listener);
        }

    }


}

//note : to make snackbar jump everything, we gotta make cordinator layout as the main parent, which,
// is a hassle for now. maybe in the later part  we will do
