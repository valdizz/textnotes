package com.valdizz.textnotes.presenter;


import com.valdizz.textnotes.model.NoteModel;
import com.valdizz.textnotes.model.entity.Note;
import com.valdizz.textnotes.view.NoteListView;

import io.realm.OrderedRealmCollection;

public class NotePresenterImpl implements NotePresenter{

    private final NoteModel noteModel;
    private NoteListView noteListView;


    public NotePresenterImpl(NoteListView noteListView) {
        noteModel = new NoteModel();
        this.noteListView = noteListView;
    }

    @Override
    public void closeRealm() {
        noteModel.closeRealm();
    }

    @Override
    public OrderedRealmCollection<Note> getNotes(){
        return noteModel.getNotes();
    }

    @Override
    public void onFavoriteNoteClick(String id) {
        noteModel.setFavoriteNote(id);
    }

    @Override
    public void onDeleteNoteClick(String id) {
        noteModel.deleteNote(id);
    }

}
