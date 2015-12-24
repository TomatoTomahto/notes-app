package ryangisleson.com.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class NoteView extends AppCompatActivity {
    int noteIndex;
    Note note;
    EditText noteTitle;
    EditText noteContent;
    EditText noteTags;
    TextView noteDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        note = extras.getParcelable("note");
        noteIndex = extras.getInt("noteIndex");

        noteTitle = (EditText) findViewById(R.id.note_title);
        noteContent = (EditText) findViewById(R.id.note_content);
        noteTags = (EditText) findViewById(R.id.note_tags);
        noteDate = (TextView) findViewById(R.id.time_edited);

        noteTitle.setText(note.title);
        noteContent.setText(note.content);
        String tags = "";
        for (String tag : note.tags) {
            tags += tag + ", ";
        }
        if (!tags.equals(""))
            noteTags.setText(tags.substring(0, tags.length() - 2));
        String dateText = note.date;
        noteDate.setText(dateText.substring(0, dateText.length() - 3)); // removes seconds

        noteTitle.addTextChangedListener(editWatcher);
        noteContent.addTextChangedListener(editWatcher);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean returnToMainActivity(boolean deleted) {
        Intent resultIntent = new Intent();
        note.title = noteTitle.getText().toString();
        note.content = noteContent.getText().toString();
        note.tags = getTags();
        resultIntent.putExtra("note", note);
        resultIntent.putExtra("noteIndex", noteIndex);
        resultIntent.putExtra("deleted", deleted);
        setResult(RESULT_OK, resultIntent);
        finish();
        return true;
    }

    private ArrayList<String> getTags() {
        ArrayList<String> tags = new ArrayList<>();
        String tagText = noteTags.getText().toString();
        Collections.addAll(tags, tagText.split(", "));
        return tags;
    }

    private boolean returnToMainActivity() {
        return returnToMainActivity(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(Menu.NONE, R.id.menu_delete, Menu.NONE, R.string.menu_delete);
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.menu_delete:
                return returnToMainActivity(true);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp () {
        return returnToMainActivity();
    }

    @Override
    public void onBackPressed() {
        returnToMainActivity();
    }

    private final TextWatcher editWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            note.updateDate();
            String dateText = note.date;
            noteDate.setText(dateText.substring(0, dateText.length() - 3));
        }
    };
}
