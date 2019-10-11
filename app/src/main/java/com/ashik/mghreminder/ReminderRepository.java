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
    public void update(Reminder reminder){
        new UpdateReminderAsyncTask(reminderDao).execute(reminder);
    }
    public void delete(Reminder reminder){
        new DeleteReminderAsyncTask(reminderDao).execute(reminder);
    }
    public LiveData<List<Reminder>> getAllReminders(){
        return getAllReminders;
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

    private static class UpdateReminderAsyncTask extends AsyncTask<Reminder, Void, Void>{

        private ReminderDao reminderDao;

        private UpdateReminderAsyncTask(ReminderDao reminderDao){
            this.reminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(Reminder... reminders) {
            reminderDao.update(reminders[0]);
            return null;
        }
    }

    private static class DeleteReminderAsyncTask extends AsyncTask<Reminder, Void, Void>{

        private ReminderDao reminderDao;

        private DeleteReminderAsyncTask(ReminderDao reminderDao){
            this.reminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(Reminder... reminders) {
            reminderDao.delete(reminders[0]);
            return null;
        }
    }
}
