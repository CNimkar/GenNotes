package com.example.chai.gennotes.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.chai.gennotes.database.NoteEntity;
import com.example.chai.gennotes.repository.AppRepository;
import com.example.chai.gennotes.utilities.SampleData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<NoteEntity>> notes;
    private AppRepository singletonRepository;



    public MainViewModel(@NonNull Application application) {
        super(application);

        singletonRepository = AppRepository.getInstance(application.getApplicationContext());
        notes = singletonRepository.notes;


    }

    public void addSampleData() {
        singletonRepository.addSampleData();
    }

    public void deleteAllNotes() {
        singletonRepository.deleteAllNotes();
    }
}
