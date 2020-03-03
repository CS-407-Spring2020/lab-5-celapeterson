package c.sakshi.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesListActivity extends AppCompatActivity {

    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        //create a switch to see which menu item was pressed
        switch(item.getItemId()) {
            case R.id.item1:
                //CASE ADD NEW NOTE
                Intent intent = new Intent(this, Activity3.class);
                startActivity(intent);
                return true;
            case R.id.item2:
                //CASE LOGOUT
                //erase username from shared preferences
                Intent intent1 = new Intent(this, MainActivity.class);
                SharedPreferences sharedPreferences= getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
                sharedPreferences.edit().remove("username").apply();
                startActivity(intent1);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        // display welcome message and fetch username from shared preferences
        SharedPreferences sharedPreferences= getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        TextView welcomeMsg = (TextView) findViewById(R.id.textView);
        welcomeMsg.setText("Welcome " + username + "!");

        // get database instance
        Context context = getApplicationContext();
        SQLiteDatabase database = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        // initiate the notes class variable using readNotes method implemented in DBHelper class
        // use username from SP as a parameter to readNotes
        DBHelper dbHelper = new DBHelper(database);
        notes = dbHelper.readNotes(username);

        // create an AL string object by iterating over notes objects
        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
            Log.i("test", "added");
        }

        // use list view to display notes on screen
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listview = (ListView) findViewById(R.id.notes_list_view);
        listview.setAdapter(adapter);

        // add onItemClickedListener for listView item, a note in our case
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Activity3.class);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });

    }


}
