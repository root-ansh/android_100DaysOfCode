package in.curioustools.a100daysofcode;

import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CurrentStreakActivity extends AppCompatActivity {
    TextView tvLastCheckIn, tvStartDate, tvCounter;
    Button btUserRespYES, btUserRespNO;
    FloatingActionButton btFabShare;
    ConstraintLayout layoutMain;
    LinearLayout llTodayUpdatedFalse, llTodayUpdatedTrue;

    public boolean temp_isTodayUpdated = false;
    public String temp_lastCheckInDate = "";
    public int temp_currCount = 0;

    SharedPreferences sp_OnCreate;
    SharedPreferences.OnSharedPreferenceChangeListener l =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
            CurrentStreakActivity.this.setUIData(sp);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_streak);

        initUI();
        initUIOnCicks();
        sp_OnCreate = getSharedPreferences(Statics.SP_FILENAME, MODE_PRIVATE);

    }

    private void initUI() {
        tvLastCheckIn = findViewById(R.id.tv_lastcheckin);
        tvStartDate = findViewById(R.id.tv_startdate); // current date or last checkin date???

        tvCounter = findViewById(R.id.tv_counter);

        btUserRespYES = findViewById(R.id.bt_user_resp_yes);
        btUserRespNO = findViewById(R.id.bt_user_resp_no);

        btFabShare = findViewById(R.id.bt_fab_share);

        llTodayUpdatedFalse = findViewById(R.id.layout_resp_false);
        llTodayUpdatedTrue = findViewById(R.id.layout_resp_true);

        layoutMain = findViewById(R.id.layout_main);

        setUIData(sp_OnCreate);
    }
    private void setUIData(SharedPreferences sp) {
        if (sp == null) {
            sp = this.getSharedPreferences(Statics.SP_FILENAME, MODE_PRIVATE);
        }

        String defDates = Statics.getToday();

        String startDate = sp.getString(Statics.START_DATE_str, defDates);
        int totCount = sp.getInt(Statics.TOTAL_STREAK_COUNT_str, 0);

        String lciDate = sp.getString(Statics.LAST_CHECKIN_DATE_str, defDates);
        int currCount = sp.getInt(Statics.CURRENT_STREAK_COUNT_str, 0);
        boolean isTodateUpdated = sp.getBoolean(Statics.IS_TODAY_UPDATED_str, false);

        tvLastCheckIn.setText(lciDate);
        tvStartDate.setText(startDate);
        tvCounter.setText("" + currCount + " / " + totCount);

        if (!isTodateUpdated) {
            llTodayUpdatedFalse.setVisibility(View.VISIBLE);
            llTodayUpdatedTrue.setVisibility(View.GONE);
        } else {
            llTodayUpdatedFalse.setVisibility(View.GONE);
            llTodayUpdatedTrue.setVisibility(View.VISIBLE);
        }
    }
    private void initUIOnCicks() {

        btUserRespYES.setOnClickListener(v -> {
            //1. change ui( automatically being done by the pref changelisteners)
            //2. upload the data on sharedPref
            updateSharedPref(Source.INCREASE);
            // 3 show snackbar
            String msg = "Keep Coding!";
            Snackbar s = Snackbar.make(layoutMain, msg, Snackbar.LENGTH_LONG);
            s.setAction("undo", v1 -> updateSharedPref(Source.UNDO));
            s.show();
        });
        btUserRespNO.setOnClickListener(v -> {
            //1. change ui//todo: add a temporary quote layout showing motivationl quotes
            //2. Nothing<doesn't uploades any data on shared pref>
            // 3 show snackbar
            String msg = "Will show a notification again in next 2 hours";
            Snackbar s = Snackbar.make(layoutMain, msg, Snackbar.LENGTH_LONG);
            s.setAction("settings", v12 -> {
                //todo : start settings activity
            });
            s.show();
        });
        btFabShare.setOnClickListener(v -> {
            // TODO: 01-01-2019 start share activity with current score
        });
    }

    private enum Source {INCREASE, UNDO}
    private void updateSharedPref(Source SOURCE) {  // to be called ONLY after shared pref is registered

        if (SOURCE == Source.INCREASE) {

            //Store the old values, in case if pressed undo
            temp_currCount = sp_OnCreate.getInt(Statics.CURRENT_STREAK_COUNT_str, 0);
            temp_lastCheckInDate= sp_OnCreate.getString(Statics.LAST_CHECKIN_DATE_str, Statics.getToday());
            temp_isTodayUpdated= sp_OnCreate.getBoolean(Statics.IS_TODAY_UPDATED_str, false);

            // update new values
            sp_OnCreate.edit()
                    .putBoolean(Statics.IS_TODAY_UPDATED_str,true)
                    .putInt(Statics.CURRENT_STREAK_COUNT_str, temp_currCount+1)
                    .putString(Statics.LAST_CHECKIN_DATE_str, Statics.getToday())
                    .apply();
        } else if (SOURCE == Source.UNDO) {
            sp_OnCreate.edit()
                    .putBoolean(Statics.IS_TODAY_UPDATED_str,temp_isTodayUpdated)
                    .putInt(Statics.CURRENT_STREAK_COUNT_str, temp_currCount)
                    .putString(Statics.LAST_CHECKIN_DATE_str,temp_lastCheckInDate)
                    .apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sp_OnCreate == null) {
            sp_OnCreate = getSharedPreferences(Statics.SP_FILENAME, MODE_PRIVATE);
        }
        sp_OnCreate.registerOnSharedPreferenceChangeListener(l);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (sp_OnCreate != null) {
            sp_OnCreate.unregisterOnSharedPreferenceChangeListener(l);
        }

    }
}

//note : to make snackbar jump everything, we gotta make cordinator layout as the main parent, which,
// is a hassle for now. maybe in the later part  we will do
