package com.example.chai.gennotes.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.chai.gennotes.database.NoteEntity;
import com.example.chai.gennotes.repository.AppRepository;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditorViewModel extends AndroidViewModel {

    public MutableLiveData<NoteEntity> mutableLiveNote = new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public EditorViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int noteId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                NoteEntity note = mRepository.getNoteById(noteId);
                mutableLiveNote.postValue(note);
            }
        });
    }

    public void saveNote(String noteText) {
        NoteEntity note = mutableLiveNote.getValue();

        if(note == null){
            if(TextUtils.isEmpty(noteText.trim())){
                return;
            }
            note = new NoteEntity(new Date(), noteText.trim());
        }
        else{
            note.setText(noteText.trim());
        }

        mRepository.insertNote(note);
    }

    public void deleteNote() {
        mRepository.deleteNote(mutableLiveNote.getValue());
    }
}
