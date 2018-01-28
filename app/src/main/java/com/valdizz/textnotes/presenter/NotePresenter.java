package com.valdizz.textnotes.presenter;


import com.valdizz.textnotes.model.entity.Note;

import io.realm.OrderedRealmCollection;

public interface NotePresenter extends BasePresenter {

    void onFavoriteNoteClick(String id);
    void onDeleteNoteClick(String id);
    OrderedRealmCollection<Note> getNotes();
}
