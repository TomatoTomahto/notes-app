package ryangisleson.com.notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

class Note implements Parcelable {
    protected int _id;
    public String title;
    public String content;
    public String date;
    public ArrayList<String> tags;

    /**
     * Used for creating new notes.
     */
    public Note() {
        this.title = "";
        this.content = "";
        updateDate();
        tags = new ArrayList<>();
    }

    /**
     * Used for restoring note from database.
     */
    public Note(int id, String title, String content, String date, ArrayList<String> tags) {
        this._id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.tags = tags;
    }

    public void updateDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        this.date = dateFormat.format(date);
    }

    protected Note(Parcel in) {
        _id = in.readInt();
        title = in.readString();
        content = in.readString();
        date = in.readString();
        tags = in.createStringArrayList();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(date);
        dest.writeStringList(tags);
    }
}
