package com.ashik.mghreminder.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Reminder.class}, version = 1)
public abstract class ReminderDB extends RoomDatabase {

    public static ReminderDB INSTANCE;
    public abstract ReminderDao reminderDao();
    private static final Object sLock = new Object();

    public static ReminderDB getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        ReminderDB.class, "Reminders.db")
                        .build();
            }
            return INSTANCE;
        }
    }
}
