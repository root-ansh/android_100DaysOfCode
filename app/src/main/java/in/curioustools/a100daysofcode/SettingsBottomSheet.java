package in.curioustools.a100daysofcode;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import static android.content.Context.MODE_PRIVATE;

class SettingsBottomSheet {
    /*
     * The given class presumes that the bottomsheet has outermostLayout as linearlayout, and asks for
     * its instantiated object as mandatory parameter.
     * */
    private LinearLayout rootParent;
    SharedPreferences sp;

    private BottomSheetBehavior sheetBehavior;

    private ImageButton lbsBtSave;
    private TextInputEditText lbsEtTitle, lbsEtCurrStreak, lbsEtMaxStreak, lbsEtMissed;
    private TextView lbsTvStartDate, lbsTvlastCheckin;

    private RadioGroup lbsRgNotifGroup;
    private RadioButton lbsRbOff, lbsRbOnceAday, lbsRbFullDay;
    private LinearLayout lbsLLNotif;
    private TextView lbsTvNotifTime, lbsTvNotifMessage;


    public SettingsBottomSheet(LinearLayout rootParent,SharedPreferences sp) {
        this.rootParent = rootParent;
        this.sp =sp;
        initLbsUI();
        initLbsDataAndActions();
    }

    private void initLbsUI() {
        sheetBehavior = BottomSheetBehavior.from(this.rootParent);
        lbsEtTitle = rootParent.findViewById(R.id.lbs_et_streak_title);
        lbsEtCurrStreak = rootParent.findViewById(R.id.lbs_et_current_streak);
        lbsEtMaxStreak = rootParent.findViewById(R.id.lbs_et_max_streak);
        lbsEtMissed = rootParent.findViewById(R.id.lbs_et_missed);

        lbsTvStartDate = rootParent.findViewById(R.id.lbs_text_startDate);
        lbsTvlastCheckin = rootParent.findViewById(R.id.lbs_text_lastcheckin);
        lbsTvNotifTime = rootParent.findViewById(R.id.lbs_notif_time);
        lbsTvNotifMessage = rootParent.findViewById(R.id.lbs_text_notif_msg);

        lbsBtSave = rootParent.findViewById(R.id.lbs_bt_im_close);

        lbsLLNotif = rootParent.findViewById(R.id.lbs_llnotifTime);

        lbsRgNotifGroup = rootParent.findViewById(R.id.lbs_rg_notif_group);
        lbsRbOff = rootParent.findViewById(R.id.lbs_rb_off);
        lbsRbOnceAday = rootParent.findViewById(R.id.lbs_rb_once_a_day);
        lbsRbFullDay = rootParent.findViewById(R.id.lbs_rb_full_day);

    }
    private void initLbsDataAndActions() {
        String title = sp.getString(Statics.STREAK_NAME_r_str, "#100DaysofCode");
        String currentStreak = "" + sp.getInt(Statics.CURRENT_STREAK_COUNT_r_int, 0);
        String maxStreak = "" + sp.getInt(Statics.MAX_STREAK_COUNT_r_int, 100);
        String missed = "" + sp.getInt(Statics.MISSED_COUNT_r_int, 0);

        String streakStarted = sp.getString(Statics.START_DATE_r_str, Statics.getTodayString());
        String streakLastCheckin = sp.getString(Statics.LAST_CHECKIN_DATE_r_str, Statics.NOT_SET);

        int notifSettings = sp.getInt(Statics.NOTIFY_SETTINGS_r_int012, 2);

        lbsEtTitle.setText(title);
        lbsEtCurrStreak.setText(currentStreak);
        lbsEtMaxStreak.setText(maxStreak);
        lbsEtMissed.setText(missed);

        lbsTvStartDate.setText(streakStarted);
        lbsTvlastCheckin.setText(streakLastCheckin);

        //set radiobuttons , notif time, notif message
        setNotifSettings(notifSettings);



        lbsRgNotifGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.lbs_rb_full_day:
                    setNotifSettings(2);
                    break;
                case R.id.lbs_rb_once_a_day:
                    setNotifSettings(1);
                    break;
                case R.id.lbs_rb_off:
                    setNotifSettings(0);
                    break;
            }
        });

        sheetBehavior.setBottomSheetCallback(getBottomSheetCallback());
        lbsBtSave.setOnClickListener(v -> {
            saveData();
            toggleBottomSheet();
        });

        lbsTvStartDate.setOnClickListener(v -> { showDatePicker(lbsTvStartDate); });
        lbsTvlastCheckin.setOnClickListener(v -> { showDatePicker(lbsTvlastCheckin); });
        lbsTvNotifTime.setOnClickListener(v -> showTimePicker(lbsTvNotifTime));

    }

    private void showTimePicker(TextView tv) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog.OnTimeSetListener tpdListener= (timePicker, selectedHour, selectedMinute) ->
                tv.setText(MessageFormat.format("{0}:{1}", selectedHour, selectedMinute));
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(tv.getContext(),tpdListener, hour, minute, false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
    private void showDatePicker(TextView tv) {
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener dplisterner= (datepicker, selectedyear, selectedmonth, selectedday) -> {
            selectedmonth = selectedmonth + 1;

            String currDateString=""+selectedyear+'-'+selectedmonth+'-'+selectedday;
            String dateStringMyPattern ="";
            try {
                SimpleDateFormat currDatePattern = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
                Date currDate = currDatePattern.parse(currDateString);
                dateStringMyPattern = new SimpleDateFormat("MMM dd, yyyy.",Locale.getDefault())
                        .format(currDate);
            }
            catch (Exception e) {
                dateStringMyPattern=Statics.getTodayString();
            }
            tv.setText(dateStringMyPattern);
        };
        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(tv.getContext(), dplisterner, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    //set radiobuttons , notif time, notif message
    private void setNotifSettings(int notifSettings) {
        String msg = "";
        switch (notifSettings) {
            case 0:
                lbsRbOff.setChecked(true);
                lbsLLNotif.setVisibility(View.GONE);

                lbsTvNotifTime.setText(Statics.getCurrentTime());


                msg = "There will be no notification.You must update Streak Manually";
                lbsTvNotifMessage.setText(msg);



                break;
            case 1:
                lbsRbOnceAday.setChecked(true);

                lbsLLNotif.setVisibility(View.VISIBLE);

                String notiftime = sp.getString(Statics.NOTIFY_TIME_r_str, Statics.getCurrentTime());
                lbsTvNotifTime.setText(notiftime);

                msg = "There will be a single notification at above said time." ;
                lbsTvNotifMessage.setText(msg);

                break;
            case 2:
                lbsRbFullDay.setChecked(true);
                lbsLLNotif.setVisibility(View.GONE);

                lbsTvNotifTime.setText(Statics.getCurrentTime());

                msg = "There will be notification every 2 hours";
                lbsTvNotifMessage.setText(msg);
                break;
        }

    }
    private BottomSheetBehavior.BottomSheetCallback getBottomSheetCallback() {
        //to set any animations during sheet going up/down
        return new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newstate) {
                if (newstate == BottomSheetBehavior.STATE_COLLAPSED) {
                    saveData();
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
            }
        };
    }
    private void saveData() {
        String title = lbsEtTitle.getText().toString();
        int currentStreak = Integer.parseInt(lbsEtCurrStreak.getText().toString());
        int maxStreak = Integer.parseInt(lbsEtMaxStreak.getText().toString());
        int missed = Integer.parseInt(lbsEtMissed.getText().toString());

        String streakStarted = lbsTvStartDate.getText().toString();
        String lastchecin = lbsTvlastCheckin.getText().toString();

        String notiftime = lbsTvNotifTime.getText().toString();
        int notifSettings = 0;

        switch (lbsRgNotifGroup.getCheckedRadioButtonId()) {
            case R.id.lbs_rb_full_day:
                notifSettings = 2;
                break;
            case R.id.lbs_rb_once_a_day:
                notifSettings = 1;
                break;
            case R.id.lbs_rb_off:
                notifSettings = 0;
                break;
        }

        sp.edit()
                .putString(Statics.STREAK_NAME_r_str, title)
                .putInt(Statics.CURRENT_STREAK_COUNT_r_int, currentStreak)
                .putInt(Statics.MAX_STREAK_COUNT_r_int, maxStreak)
                .putInt(Statics.MISSED_COUNT_r_int, missed)
                .putString(Statics.START_DATE_r_str, streakStarted)
                .putString(Statics.LAST_CHECKIN_DATE_r_str, lastchecin)
                .putInt(Statics.NOTIFY_SETTINGS_r_int012, notifSettings)
                .putString(Statics.NOTIFY_TIME_r_str, notiftime)
                .apply();
    }


    //for manual toggle
    public void toggleBottomSheet() {
        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    public  void updateLBSdataPubicVersion(){
        String currentStreak = "" + sp.getInt(Statics.CURRENT_STREAK_COUNT_r_int, 0);
        String missed = "" + sp.getInt(Statics.MISSED_COUNT_r_int, 0);
        String streakLastCheckin = sp.getString(Statics.LAST_CHECKIN_DATE_r_str, Statics.NOT_SET);
        lbsEtCurrStreak.setText(currentStreak);
        lbsEtMissed.setText(missed);
        lbsTvlastCheckin.setText(streakLastCheckin);

    }


}
