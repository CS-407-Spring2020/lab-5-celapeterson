package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {

    public void loginClick(View view) {

        EditText roughUsername = (EditText) findViewById(R.id.etLogin);
        EditText roughPW = (EditText) findViewById(R.id.etPassword);

        String username = roughUsername.getText().toString();
        String password = roughPW.getText().toString();

        SharedPreferences sharedPreferences= getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", username).apply();

        goToNotesList(username);

    }

    public void goToNotesList(String s) {

        Intent intent = new Intent(this, NotesListActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        String usernameKey = "username";

        SharedPreferences sharedPreferences= getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);

        if (!sharedPreferences.getString(usernameKey, "").equals("")) {
            //means a user was logged in before the app closed
            String username = sharedPreferences.getString("username", "");
            Intent intent = new Intent(this, NotesListActivity.class);

            //pass string we want to output in the second activity
            intent.putExtra("name",username);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_main);
        }

    }


}
