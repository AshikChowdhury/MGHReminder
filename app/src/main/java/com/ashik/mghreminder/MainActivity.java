package com.ashik.mghreminder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ashik.mghreminder.data.Reminder;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_REMINDER_REQUEST = 1;
    public static final int VIEW_REMINDER_REQUEST = 2;
    private Toolbar toolbar;
    private ReminderViewModel reminderViewModel;
    private TextView mNoReminderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        mNoReminderView =  findViewById(R.id.no_reminder_text);
        FloatingActionButton buttonAddReminder = findViewById(R.id.fab);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ReminderAdapter adapter = new ReminderAdapter();
        recyclerView.setAdapter(adapter);

        reminderViewModel = ViewModelProviders.of(this).get(ReminderViewModel.class);
        reminderViewModel.getAllReminders().observe(this, new Observer<List<Reminder>>() {
            @Override
            public void onChanged(List<Reminder> reminders) {
                adapter.submitList(reminders);
            }
        });
//        if (mTest.isEmpty()) {
//            mNoReminderView.setVisibility(View.VISIBLE);
//        }

        buttonAddReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddReminderActivity.class);
                startActivityForResult(intent, ADD_REMINDER_REQUEST);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                reminderViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Reminder Deleted", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ReminderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Reminder reminder) {
                Intent intent = new Intent(MainActivity.this, ViewReminderActivity.class);
                intent.putExtra(ViewReminderActivity.EXTRA_ID, reminder.getId());
                intent.putExtra(ViewReminderActivity.EXTRA_TITLE, reminder.getTitle());
                intent.putExtra(ViewReminderActivity.EXTRA_LOCATION, reminder.getAddress());
                intent.putExtra(ViewReminderActivity.EXTRA_LAT, reminder.getLat());
                intent.putExtra(ViewReminderActivity.EXTRA_LON, reminder.getLon());
                intent.putExtra(ViewReminderActivity.EXTRA_DATE, reminder.getDate());
                intent.putExtra(ViewReminderActivity.EXTRA_TIME, reminder.getTime());
                intent.putExtra(ViewReminderActivity.EXTRA_ACTIVE, reminder.getActive());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode, data);

        if (resultCode == RESULT_OK){
            String title = data.getStringExtra(AddReminderActivity.EXTRA_TITLE);
            String date = data.getStringExtra(AddReminderActivity.EXTRA_DATE);
            String time = data.getStringExtra(AddReminderActivity.EXTRA_TIME);
            String address = data.getStringExtra(AddReminderActivity.EXTRA_LOCATION);
            double lat = data.getDoubleExtra(AddReminderActivity.EXTRA_LAT,0.00);
            double lon = data.getDoubleExtra(AddReminderActivity.EXTRA_LON,0.00);
            String active = data.getStringExtra(AddReminderActivity.EXTRA_ACTIVE);

            Reminder reminder = new Reminder(title, date, time,address, lat, lon,active);
            reminderViewModel.insert(reminder);

            Toast.makeText(this, "Reminder Saved", Toast.LENGTH_SHORT).show();
        }
//        else{
//            Toast.makeText(this, "Reminder failed to Saved", Toast.LENGTH_SHORT).show();
//        }
    }

}
