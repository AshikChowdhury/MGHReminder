package com.ashik.mghreminder.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Reminder.class}, version = 1, exportSchema = false)
public abstract class ReminderDB extends RoomDatabase {

    private static ReminderDB INSTANCE;

    public abstract ReminderDao reminderDao();

    public static synchronized ReminderDB getInstance(Context context) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        ReminderDB.class, "Reminders.db")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build();
            }
            return INSTANCE;
        }

    private static  RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ReminderDao reminderDao;

        private PopulateDbAsyncTask(ReminderDB db){
            reminderDao = db.reminderDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            reminderDao.insert(new Reminder("Title1", "30/12/1995", "14:25","","True"));
            reminderDao.insert(new Reminder("Title1", "30/12/1995", "14:25","","True"));
            reminderDao.insert(new Reminder("Title1", "30/12/1995", "14:25","","True"));
            return null;
        }
    }
}
