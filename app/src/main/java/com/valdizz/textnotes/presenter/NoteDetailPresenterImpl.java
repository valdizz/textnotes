package com.valdizz.textnotes.presenter;


import com.valdizz.textnotes.model.NoteModel;
import com.valdizz.textnotes.view.NoteDetailView;

public class NoteDetailPresenterImpl implements NoteDetailPresenter{

    private final NoteModel noteModel;
    private NoteDetailView noteDetailView;


    public NoteDetailPresenterImpl(NoteDetailView noteDetailView) {
        noteModel = new NoteModel();
        this.noteDetailView = noteDetailView;
    }

    @Override
    public void closeRealm() {
        noteModel.closeRealm();
    }

    @Override
    public void onAddNoteClick(String title, String notetext) {
        noteModel.addNote(title, notetext);
    }

    @Override
    public void onUpdateNoteClick(String id, String title, String notetext) {
        noteModel.updateNote(id, title, notetext);
    }
}
