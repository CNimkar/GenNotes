package com.example.chai.gennotes.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.chai.gennotes.R;
import com.example.chai.gennotes.database.NoteEntity;
import com.example.chai.gennotes.utilities.Constants;
import com.example.chai.gennotes.viewmodel.EditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditorActivity extends AppCompatActivity {

    @BindView(R.id.note_text)
    TextView mTextView;

    private EditorViewModel mViewModel;
    private boolean mNewNote, mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState != null){
            mEditing = savedInstanceState.getBoolean(Constants.EDITING_KEY);
        }

        initViewModel();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this)
                .get(EditorViewModel.class);
        mViewModel.mutableLiveNote.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(@Nullable NoteEntity noteEntity) {
                if(noteEntity != null && !mEditing)
                mTextView.setText(noteEntity.getText());
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            setTitle(getString(R.string.new_note));
            mNewNote = true;
        } else {
            setTitle(getString(R.string.edit_note));
            int noteId = extras.getInt(Constants.NOTE_ID_KEY);
            mViewModel.loadData(noteId);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!mNewNote){
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu_editor, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            saveAndReturn();
            return true;
        } else if(item.getItemId() == R.id.action_delete){
            mViewModel.deleteNote();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        mViewModel.saveNote(mTextView.getText().toString());
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(Constants.EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }
}
