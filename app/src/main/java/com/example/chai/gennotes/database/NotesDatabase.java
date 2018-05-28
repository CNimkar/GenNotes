package com.example.chai.gennotes.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {NoteEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract  class NotesDatabase extends RoomDatabase{
    public static final String DATABASE_NAME = "NotesDatabase.db";
    private static volatile NotesDatabase instance;
    private static final Object LOCK = new Object();


    public abstract NoteDAO noteDAO();

    public static NotesDatabase getInstance(Context context) {
        if(instance == null){

            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), NotesDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
