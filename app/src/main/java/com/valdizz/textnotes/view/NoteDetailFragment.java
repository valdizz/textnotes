package com.valdizz.textnotes.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valdizz.textnotes.R;

public class NoteDetailFragment extends Fragment {

    public static final String NOTE_ID = "note_id";
    public static final String NOTE_TITLE = "note_title";
    public static final String NOTE_TEXT = "note_text";

    public NoteDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.note_detail, container, false);

        if (getArguments() != null) {
            (rootView.findViewById(R.id.tv_edit_title)).setTag(getArguments().getString(NOTE_ID));
            ((TextView) rootView.findViewById(R.id.tv_edit_title)).setText(getArguments().getString(NOTE_TITLE));
            ((TextView) rootView.findViewById(R.id.tv_edit_note)).setText(getArguments().getString(NOTE_TEXT));
        }
        else {
            (rootView.findViewById(R.id.tv_edit_title)).setTag(null);
            ((TextView) rootView.findViewById(R.id.tv_edit_title)).setText("");
            ((TextView) rootView.findViewById(R.id.tv_edit_note)).setText("");
        }

        return rootView;
    }
}
