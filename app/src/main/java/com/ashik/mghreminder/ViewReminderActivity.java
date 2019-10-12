package com.ashik.mghreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewReminderActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String EXTRA_ID =
            "com.ashik.mghreminder.EXTRA_ID";
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

    private Toolbar toolbar;
    private TextView mTitle, mAddress, mDate, mTime;
    private GoogleMap mMap;
    private double lat;
    private double lon;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminder);

//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(R.string.title_activity_view_reminder);

        mTitle =  findViewById(R.id.view_reminder_title);
        mAddress =  findViewById(R.id.view_location);
        mDate =  findViewById(R.id.view_set_date);
        mTime =  findViewById(R.id.view_set_time);

        String title = getIntent().getStringExtra(EXTRA_TITLE);
        address = getIntent().getStringExtra(EXTRA_LOCATION);
        String date = getIntent().getStringExtra(EXTRA_DATE);
        String time = getIntent().getStringExtra(EXTRA_TIME);
        lat = getIntent().getDoubleExtra(EXTRA_LAT, 0.00);
        lon = getIntent().getDoubleExtra(EXTRA_LON, 0.00);
        String active = getIntent().getStringExtra(EXTRA_ACTIVE);
//        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();

        mTitle.setText(title);
        mAddress.setText(address);
        mDate.setText(date);
        mTime.setText(time);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng place = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(place).title(address));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place, 15.0f));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
    }
}
