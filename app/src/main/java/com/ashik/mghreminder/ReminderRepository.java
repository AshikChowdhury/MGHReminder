package com.ashik.mghreminder;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ashik.mghreminder.data.Reminder;
import com.ashik.mghreminder.data.ReminderDB;
import com.ashik.mghreminder.data.ReminderDao;

import java.util.List;

public class ReminderRepository {
    private ReminderDao reminderDao;
    private LiveData<List<Reminder>> getAllReminders;

    public ReminderRepository(Application application){
        ReminderDB database = ReminderDB.getInstance(application);
        reminderDao = database.reminderDao();
        getAllReminders = reminderDao.getAllReminders();
    }

    public void insert(Reminder reminder){
        new InsertReminderAsyncTask(reminderDao).execute(reminder);
    }

    private static class InsertReminderAsyncTask extends AsyncTask<Reminder, Void, Void> {

        private ReminderDao reminderDao;

        private InsertReminderAsyncTask(ReminderDao reminderDao){
            this.reminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(Reminder... reminders) {
            reminderDao.insert(reminders[0]);
            return null;
        }
    }
}
