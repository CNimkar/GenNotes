package com.example.chai.gennotes;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.chai.gennotes.database.NoteDAO;
import com.example.chai.gennotes.database.NoteEntity;
import com.example.chai.gennotes.database.NotesDatabase;
import com.example.chai.gennotes.utilities.SampleData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    public static final String TAG = "JUnit";
    private NotesDatabase nDB;
    private NoteDAO mDao;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        nDB = Room.inMemoryDatabaseBuilder(context, NotesDatabase.class).build();
        mDao = nDB.noteDAO();
        Log.i(TAG, "created DB");
    }

    @After
    public void closeDb(){
        nDB.close();
        Log.i(TAG, "closed DB");
    }

    @Test
    public void createAndRetrieveNotes(){
        mDao.insertAll(SampleData.getNotes());
        int count = mDao.getCount();
        Log.i(TAG, "Count is "+count);
        assertEquals(SampleData.getNotes().size(), count);
    }

    @Test
    public void compareStrings(){
        mDao.insertAll(SampleData.getNotes());
        NoteEntity original = SampleData.getNotes().get(0);
        NoteEntity fromDb = mDao.getNoteById(1);
        assertEquals(original.getText(), fromDb.getText());
        assertEquals(original.getDate().toString(), fromDb.getDate().toString());
    }

    @Test
    public void checkAutoGenerateNumbers(){
        mDao.insertAll(SampleData.getNotes());
        NoteEntity fromDbOne = mDao.getNoteById(1);
        NoteEntity fromDbTwo = mDao.getNoteById(2);
        NoteEntity fromDbThree = mDao.getNoteById(3);

        assertEquals(1, fromDbOne.getId());
        assertEquals(2, fromDbTwo.getId());
        assertEquals(3, fromDbThree.getId());
        }


}
