package com.valdizz.textnotes.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.valdizz.textnotes.R;
import com.valdizz.textnotes.adapter.NotesRecyclerViewAdapter;
import com.valdizz.textnotes.presenter.NotePresenter;
import com.valdizz.textnotes.presenter.NotePresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NoteListActivity extends AppCompatActivity implements NoteListView, NotesRecyclerViewAdapter.OnNoteClickListener {

    @BindView(R.id.note_list) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;

    private boolean mTwoPane;
    private NotePresenter notePresenter;
    private NotesRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        ButterKnife.bind(this);
        init();
        initList();
    }

    private void init() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        fab.setOnClickListener(onClickFabListener);

        if (findViewById(R.id.note_detail_container) != null) {
            mTwoPane = true;
        }

        if (notePresenter == null) {
            notePresenter = new NotePresenterImpl(this);
        }
    }

    private View.OnClickListener onClickFabListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addNote();
        }
    };

    private void initList() {
        recyclerViewAdapter = new NotesRecyclerViewAdapter(notePresenter.getNotes());
        recyclerViewAdapter.setNoteClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onDestroy() {
        notePresenter.closeRealm();
        super.onDestroy();
    }

    @Override
    public void addNote() {
        if (mTwoPane) {
            NoteDetailFragment fragment = new NoteDetailFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.note_detail_container, fragment).commit();
        } else {
            Intent intent = new Intent(this, NoteDetailActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void updateNoteClick(String id, String title, String notetext) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(NoteDetailFragment.NOTE_ID, id);
            arguments.putString(NoteDetailFragment.NOTE_TITLE, title);
            arguments.putString(NoteDetailFragment.NOTE_TEXT, notetext);
            NoteDetailFragment fragment = new NoteDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.note_detail_container, fragment).commit();
        } else {
            Intent intent = new Intent(this, NoteDetailActivity.class);
            intent.putExtra(NoteDetailFragment.NOTE_ID, id);
            intent.putExtra(NoteDetailFragment.NOTE_TITLE, title);
            intent.putExtra(NoteDetailFragment.NOTE_TEXT, notetext);
            startActivity(intent);
        }
    }

    @Override
    public void favoriteNoteClick(String id) {
        notePresenter.onFavoriteNoteClick(id);
    }

    @Override
    public void deleteNoteClick(String id) {
        notePresenter.onDeleteNoteClick(id);
    }
}
