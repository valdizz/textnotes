package com.valdizz.textnotes;

import android.app.Application;

import io.realm.Realm;

public class TextNotesApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
