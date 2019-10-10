package com.ashik.mghreminder.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ReminderDao {

    @Insert
    void insert(Reminder reminder);

    @Update
    void update(Reminder reminder);

    @Delete
    void delete(Reminder reminder);

    @Query("DELETE FROM reminders_table")
    void deleteAllNotes();

    @Query("SELECT * FROM reminders_table ORDER BY id DESC")
    LiveData<List<Reminder>> getAllReminders();
}
