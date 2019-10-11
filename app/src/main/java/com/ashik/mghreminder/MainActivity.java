package com.ashik.mghreminder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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
                Intent intent = new Intent(MainActivity.this, AddEditReminderActivity.class);
//                startActivityForResult(intent, ADD_REMINDER_REQUEST);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode, data);

        if (resultCode == RESULT_OK){
            String title = data.getStringExtra(AddEditReminderActivity.EXTRA_TITLE);
            String date = data.getStringExtra(AddEditReminderActivity.EXTRA_DATE);
            String time = data.getStringExtra(AddEditReminderActivity.EXTRA_TIME);
            String address = data.getStringExtra(AddEditReminderActivity.EXTRA_LOCATION);
            String active = data.getStringExtra(AddEditReminderActivity.EXTRA_ACTIVE);

            Reminder reminder = new Reminder(title, date, time,address,active);
            reminderViewModel.insert(reminder);

            Toast.makeText(this, "Reminder Saved", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Reminder failed to Saved", Toast.LENGTH_SHORT).show();
        }
    }
}
