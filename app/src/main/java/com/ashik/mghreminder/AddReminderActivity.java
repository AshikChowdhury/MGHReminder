package com.ashik.mghreminder;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Arrays;
import java.util.Calendar;

public class AddReminderActivity extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_TITLE =
            "com.ashik.mghreminder.EXTRA_TITLE";
    public static final String EXTRA_LOCATION =
            "com.ashik.mghreminder.EXTRA_LOCATION";
    public static final String EXTRA_LAT =
            "com.ashik.mghreminder.EXTRA_LAT";
    public static final String EXTRA_LON =
            "com.ashik.mghreminder.EXTRA_LON";
    public static final String EXTRA_DATE =
            "com.chowdhury.ashik.mytodo.EXTRA_DATE";
    public static final String EXTRA_TIME =
            "com.chowdhury.ashik.mytodo.EXTRA_TIME";
    public static final String EXTRA_ACTIVE =
            "com.chowdhury.ashik.mytodo.EXTRA_ACTIVE";

    private EditText mReminderTitle,mReminderLocaion;
    private TextView mDateText, mTimeText;

    private Toolbar toolbar;
    private FloatingActionButton mFAB1;
    private FloatingActionButton mFAB2;
    private Calendar mCalendar;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private String mTitle;
    private String mLocation;
    private double latitude;
    private double longitude;
    private String mTime;
    private String mDate;
    private String mActive;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        toolbar = findViewById(R.id.toolbar);
        mReminderTitle = findViewById(R.id.reminder_title);
        mDateText =  findViewById(R.id.set_date);
        mTimeText = findViewById(R.id.set_time);
        mFAB1 = findViewById(R.id.starred1);
        mFAB2 = findViewById(R.id.starred2);
        mActive = "True";

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);

        mDate = mDay + "/" + mMonth + "/" + mYear;
        mTime = mHour + ":" + mMinute;

        mDateText.setText(mDate);
        mTimeText.setText(mTime);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_add_reminder);

        //Place Auto Complete API Setup
        String apiKey = getString(R.string.api_key);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                mLocation = place.getName();
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;
                // TODO: Get info about the selected place.
                Log.e("Place", "Place: " + place.getName() + ", " + place.getLatLng());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.e("Place", "An error occurred: " + status);
            }
        });

        // Setup Reminder Title EditText
        mReminderTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitle = s.toString().trim();
                mReminderTitle.setError(null);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // On clicking Time picker
    public void setTime(View v){
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setThemeDark(false);
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    // On clicking Date picker
    public void setDate(View v){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    // Obtain time from time picker
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;
        if (minute < 10) {
            mTime = hourOfDay + ":" + "0" + minute;
        } else {
            mTime = hourOfDay + ":" + minute;
        }
        mTimeText.setText(mTime);
    }

    // Obtain date from date picker
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear ++;
        mDay = dayOfMonth;
        mMonth = monthOfYear;
        mYear = year;
        mDate = dayOfMonth + "/" + monthOfYear + "/" + year;
        mDateText.setText(mDate);
    }

    // On clicking the active button
    public void selectFab1(View v) {
        mFAB1 = findViewById(R.id.starred1);
        mFAB1.setVisibility(View.GONE);
        mFAB2 = findViewById(R.id.starred2);
        mFAB2.setVisibility(View.VISIBLE);
        mActive = "True";
    }

    // On clicking the inactive button
    public void selectFab2(View v) {
        mFAB2 = findViewById(R.id.starred2);
        mFAB2.setVisibility(View.GONE);
        mFAB1 = findViewById(R.id.starred1);
        mFAB1.setVisibility(View.VISIBLE);
        mActive = "False";
    }

    // On clicking the save button
    private void saveReminder(){

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, mTitle);
        data.putExtra(EXTRA_LOCATION, mLocation);
        data.putExtra(EXTRA_LAT, latitude);
        data.putExtra(EXTRA_LON, longitude);
        data.putExtra(EXTRA_DATE, mDate);
        data.putExtra(EXTRA_TIME, mTime);
        data.putExtra(EXTRA_ACTIVE, mActive);

        setResult(RESULT_OK, data);
        finish();
//        // Create a new notification
//        if (mActive.equals("true")) {
//            if (mRepeat.equals("true")) {
//                new AlarmReceiver().setRepeatAlarm(getApplicationContext(), mCalendar, ID, mRepeatTime);
//            } else if (mRepeat.equals("false")) {
//                new AlarmReceiver().setAlarm(getApplicationContext(), mCalendar, ID);
//            }
//        }

        // Create toast to confirm new reminder
//        Toast.makeText(getApplicationContext(), "Saved",
//                Toast.LENGTH_SHORT).show();

        onBackPressed();
    }

    // On pressing the back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Creating the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
        menu.findItem(R.id.discard_reminder).setVisible(false);
        return true;
    }

    // On clicking menu buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.save_reminder:
                mReminderTitle.setText(mTitle);
                if (mReminderTitle.getText().toString().length() == 0) {
                    mReminderTitle.setError("Reminder Title cannot be blank!");
                }else {
                    saveReminder();
                }
                return true;

            // On clicking discard reminder button
            // Discard any changes
            case R.id.discard_reminder:
                Toast.makeText(getApplicationContext(), "Discarded",
                        Toast.LENGTH_SHORT).show();

                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
