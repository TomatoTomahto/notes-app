package ryangisleson.com.notes;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


class NoteAdapter extends ArrayAdapter<Note> {
    private NotesActivity activity;

    public NoteAdapter(Context context, ArrayList<Note> notes) {
        super(context, R.layout.note_row, notes);
        this.activity = (NotesActivity) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.note_row, parent, false);

        final Note note = getItem(position);
        final TextView noteTitle = (TextView) view.findViewById(R.id.note_title);
        TextView noteContent = (TextView) view.findViewById(R.id.note_content);
        TextView noteDate = (TextView) view.findViewById(R.id.note_date);

        noteTitle.setText(note.title);
        noteContent.setText(note.content);
        String dateText = note.date;
        noteDate.setText(dateText.substring(0, dateText.length() - 3));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.editNote(note);
            }
        });

        return view;
    }
}
