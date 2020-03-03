package c.sakshi.lab5;

import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import android.database.Cursor;

public class DBHelper {

    SQLiteDatabase database;

    public DBHelper(SQLiteDatabase database) {
        this.database = database;
    }

    public void createTable() {

        database.execSQL("CREATE TABLE IF NOT EXISTS notes " +
                "(id INTEGER PRIMARY KEY, username TEXT, date TEXT, title TEXT, content TEXT, src TEXT)");

    }

    public ArrayList<Note> readNotes(String username) {

        createTable();
        Cursor c = database.rawQuery(String.format("SELECT * from notes where username like '%s'", username), null);

        int dateIndex = c.getColumnIndex("date");
        int titleIndex = c.getColumnIndex("title");
        int contentIndex = c.getColumnIndex("content");

        c.moveToFirst();

        ArrayList<Note> notesList = new ArrayList<>();

        while (!c.isAfterLast()) {
            String title = c.getString(titleIndex);
            String date = c.getString(dateIndex);
            String content = c.getString(contentIndex);

            Note note = new Note(date, username, title, content);
            notesList.add(note);
            c.moveToNext();
        }
        c.close();
        database.close();

        return notesList;
    }

    public void saveNotes(String date, String username, String title, String content) {
        createTable();
        database.execSQL(String.format("INSERT INTO notes (username, date, title, content) VALUES ('%s', '%s', '%s', '%s')",
                username, date, title, content));
    }

    public void updateNote(String date, String username, String title, String content) {
        createTable();
        database.execSQL(String.format("UPDATE notes set content = '%s', date = '%s', where title = '%s' and username = '%s'",
                content, date, title, username));
    }

}
