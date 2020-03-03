package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity3 extends AppCompatActivity {

    private int noteid = -1;

    public void saveClick(View view) {

        // get editText view and content user entered
        EditText et = findViewById(R.id.edit_text);
        String content = et.getText().toString();

        // initialize database instance
        Context context = getApplicationContext();
        SQLiteDatabase database = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        // initialize DBHelper class
        DBHelper dbHelper = new DBHelper(database);

        // set username in the following variable by fetching it from SharedPreferences
        SharedPreferences sharedPreferences= getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        // save info to database
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) { //add note
            title = "NOTE_" + (NotesListActivity.notes.size() + 1);
            dbHelper.saveNotes(date, username, title, content);
        } else { //update note
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNote(date, username, title, content);
        }

        //go to second activity using intents
        Intent intent = new Intent(this, NotesListActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        // get EditText view
        EditText et = findViewById(R.id.edit_text);

        // get intent
        Intent intent = getIntent();

        // get value of integer "noteid" from intent
        noteid = intent.getIntExtra("noteid", -1);

        // initialize class variable "noteid" with the value from intent
        if (noteid != -1) {
            Note note = NotesListActivity.notes.get(noteid);
            String noteContent = note.getContent();
            et.setText(noteContent);
        }
    }
}
