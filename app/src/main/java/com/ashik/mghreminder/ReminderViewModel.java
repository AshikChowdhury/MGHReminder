package com.ashik.mghreminder;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ashik.mghreminder.data.Reminder;

import java.util.List;

public class ReminderViewModel extends AndroidViewModel {
    private ReminderRepository repository;
    private LiveData<List<Reminder>> allReminders;

    public ReminderViewModel(@NonNull Application application) {
        super(application);
        repository = new ReminderRepository(application);
        allReminders = repository.getAllReminders();
    }
    public void insert(Reminder reminder){
        repository.insert(reminder);
    }
    public void update(Reminder note){
        repository.update(note);
    }
    public void delete(Reminder note){
        repository.delete(note);
    }
    public LiveData<List<Reminder>> getAllReminders(){
        return allReminders;
    }
}
