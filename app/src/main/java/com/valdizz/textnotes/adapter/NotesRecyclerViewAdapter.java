package com.valdizz.textnotes.adapter;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.valdizz.textnotes.R;
import com.valdizz.textnotes.model.entity.Note;
import java.text.SimpleDateFormat;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


public class NotesRecyclerViewAdapter extends RealmRecyclerViewAdapter<Note, NotesRecyclerViewAdapter.NoteViewHolder>{

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private OnNoteClickListener noteClickListener;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public NotesRecyclerViewAdapter(OrderedRealmCollection<Note> notes) {
        super(notes, true);
        viewBinderHelper.setOpenOnlyOne(true);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_content, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        final Note note = getItem(position);
        viewBinderHelper.bind(holder.swipeRevealLayout, note.getId());
        holder.tv_title.setText(note.getTitle());
        holder.tv_note.setText(note.getNote());
        holder.tv_created.setText(dateFormat.format(note.getCreated()));
        holder.btn_favorite.setImageResource(note.isFavorite() ? R.drawable.ic_star : R.drawable.ic_star_border);
        holder.btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noteClickListener != null){
                    viewBinderHelper.closeLayout(note.getId());
                    noteClickListener.favoriteNoteClick(note.getId());
                }
            }
        });
        holder.frontLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noteClickListener != null){
                    viewBinderHelper.closeLayout(note.getId());
                    noteClickListener.updateNoteClick(note.getId(), note.getTitle(), note.getNote());
                }
            }
        });
        holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle(android.R.string.dialog_alert_title)
                        .setMessage(R.string.dialog_deletenote)
                        .setCancelable(true)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                noteClickListener.deleteNoteClick(note.getId());
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                viewBinderHelper.closeLayout(note.getId());
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    public void setNoteClickListener(OnNoteClickListener onNoteClickListener){
        noteClickListener = onNoteClickListener;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.swipe_layout) SwipeRevealLayout swipeRevealLayout;
        @BindView(R.id.delete_layout) View deleteLayout;
        @BindView(R.id.front_layout) View frontLayout;
        @BindView(R.id.tv_title) TextView tv_title;
        @BindView(R.id.tv_note) TextView tv_note;
        @BindView(R.id.tv_created) TextView tv_created;
        @BindView(R.id.btn_favorite) ImageButton btn_favorite;

        public NoteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnNoteClickListener {

        void updateNoteClick(String id, String title, String notetext);
        void favoriteNoteClick(String id);
        void deleteNoteClick(String id);
    }
}
