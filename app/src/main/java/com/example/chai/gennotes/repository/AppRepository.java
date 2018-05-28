package com.example.chai.gennotes.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.chai.gennotes.database.NoteEntity;
import com.example.chai.gennotes.database.NotesDatabase;
import com.example.chai.gennotes.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;

    public LiveData<List<NoteEntity>> notes;
    private NotesDatabase nDB;
    private Executor executor = Executors.newSingleThreadExecutor();


    public static AppRepository getInstance(Context context) {

        if(ourInstance == null)
            ourInstance = new AppRepository(context);

        return ourInstance;
    }

    private AppRepository(Context context) {
        nDB = NotesDatabase.getInstance(context);
        notes =  getAllNotes();
     }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                nDB.noteDAO().insertAll(SampleData.getNotes());
            }
        });
    }

    private LiveData<List<NoteEntity>> getAllNotes(){
        //Room does the background threading. Hence no Executor.
        return nDB.noteDAO().getAll();
    }

    public void deleteAllNotes() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                nDB.noteDAO().deleteAll();
            }
        });
    }

    public NoteEntity getNoteById(int noteId) {
        return nDB.noteDAO().getNoteById(noteId);
    }

    public void insertNote(final NoteEntity note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                nDB.noteDAO().insertNote(note);
            }
        });
    }

    public void deleteNote(final NoteEntity note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                nDB.noteDAO().deleteNote(note);
            }
        });
    }
}
