package com.valdizz.textnotes.model;


import com.valdizz.textnotes.model.entity.Note;

import java.util.Date;
import java.util.UUID;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.Sort;

public class NoteModel {

    private final Realm realm;

    public NoteModel() {
        realm = Realm.getDefaultInstance();
    }

    public void closeRealm(){
        realm.close();
    }

    public OrderedRealmCollection<Note> getNotes() {
        return realm.where(Note.class).findAllAsync().sort("favorite", Sort.DESCENDING, "created", Sort.DESCENDING);
    }

    public void addNote( final String title, final String notetext) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note note = realm.createObject(Note.class, UUID.randomUUID().toString());
                note.setTitle(title);
                note.setNote(notetext);
                note.setFavorite(false);
            }
        });
    }

    public void updateNote(final String id, final String title, final String notetext) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note note = realm.where(Note.class).equalTo("id", id).findFirst();
                note.setTitle(title);
                note.setNote(notetext);
                note.setCreated(new Date());
            }
        });
    }

    public void deleteNote(final String id) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note note = realm.where(Note.class).equalTo("id", id).findFirst();
                note.deleteFromRealm();
            }
        });
    }

    public void setFavoriteNote(final String id) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note note = realm.where(Note.class).equalTo("id", id).findFirst();
                note.setFavorite(!note.isFavorite());
            }
        });
    }
}
