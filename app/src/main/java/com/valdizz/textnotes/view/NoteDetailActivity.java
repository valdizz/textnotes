package com.valdizz.textnotes.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.valdizz.textnotes.R;
import com.valdizz.textnotes.presenter.NoteDetailPresenter;
import com.valdizz.textnotes.presenter.NoteDetailPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteDetailActivity extends AppCompatActivity implements NoteDetailView {

    @BindView(R.id.detail_toolbar) Toolbar toolbar;

    private NoteDetailPresenter noteDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        ButterKnife.bind(this);
        init();

        if (savedInstanceState == null) {
            NoteDetailFragment fragment = new NoteDetailFragment();
            if (getIntent().hasExtra(NoteDetailFragment.NOTE_ID)) {
                Bundle arguments = new Bundle();
                arguments.putString(NoteDetailFragment.NOTE_ID, getIntent().getStringExtra(NoteDetailFragment.NOTE_ID));
                arguments.putString(NoteDetailFragment.NOTE_TITLE, getIntent().getStringExtra(NoteDetailFragment.NOTE_TITLE));
                arguments.putString(NoteDetailFragment.NOTE_TEXT, getIntent().getStringExtra(NoteDetailFragment.NOTE_TEXT));
                fragment.setArguments(arguments);
            }
            getSupportFragmentManager().beginTransaction().add(R.id.note_detail_container, fragment).commit();
        }
    }

    private void init(){
        setSupportActionBar(toolbar);
        setTitle(getIntent().hasExtra(NoteDetailFragment.NOTE_ID) ? R.string.edit_note : R.string.add_note);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (noteDetailPresenter == null){
            noteDetailPresenter = new NoteDetailPresenterImpl(this);
        }
    }

    @Override
    protected void onDestroy() {
        noteDetailPresenter.closeRealm();
        super.onDestroy();
    }

    @Override
    public void saveNote() {
        Fragment noteDetailFragment = getSupportFragmentManager().findFragmentById(R.id.note_detail_container);

        if (((TextView)noteDetailFragment.getView().findViewById(R.id.tv_edit_title)).getText().toString().length()==0 && ((TextView)noteDetailFragment.getView().findViewById(R.id.tv_edit_note)).getText().toString().length()==0){
            Snackbar.make(noteDetailFragment.getView(), R.string.empty_note, Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (getIntent().hasExtra(NoteDetailFragment.NOTE_ID)) {
            noteDetailPresenter.onUpdateNoteClick((noteDetailFragment.getView().findViewById(R.id.tv_edit_title)).getTag().toString(), ((TextView)noteDetailFragment.getView().findViewById(R.id.tv_edit_title)).getText().toString(), ((TextView)noteDetailFragment.getView().findViewById(R.id.tv_edit_note)).getText().toString());
        }
        else {
            noteDetailPresenter.onAddNoteClick(((TextView)noteDetailFragment.getView().findViewById(R.id.tv_edit_title)).getText().toString(), ((TextView)noteDetailFragment.getView().findViewById(R.id.tv_edit_note)).getText().toString());
        }

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notedetail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                navigateUpTo(new Intent(this, NoteListActivity.class));
                return true;
            case R.id.action_save:
                saveNote();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
