package com.valdizz.textnotes.presenter;


public interface NoteDetailPresenter extends BasePresenter{

    void onAddNoteClick(String title, String notetext);
    void onUpdateNoteClick(String id, String title, String notetext);
}
