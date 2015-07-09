package dkl.kshare;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity
{
    public final static String PREFS_NAME = "Session";
    public final static int PREFS_PRIVACY = 0;
    public final static String EMAIL = "email";
    public final static String ID = "id";

    private SharedPreferences prefs;
    private DbManager dbm;

    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getApplicationContext().getSharedPreferences(PREFS_NAME, PREFS_PRIVACY);
        dbm = new DbManager(this);

        if(dbm.getEntryCount("posts") == 0)
	    {
	        // test data
	        dbm.addUser(new User("1111","test1@gmail.com"));
	        dbm.addUser(new User("1111","test2@gmail.com"));
	        dbm.addUser(new User("1111","test3@gmail.com"));
            
	        dbm.addPost(new Post(0, "Red Square", "Meijer", new Date(2014, 2, 01), 2));
	        dbm.addPost(new Post(0, "Red Square", "Food Co-op", new Date(2014, 3, 01), 2));
	        dbm.addPost(new Post(0, "Red Square", "My House", new Date(2014, 1, 03), 2));
	        dbm.addPost(new Post(0, "Red Square", "Saturn", new Date(2014, 1, 01), 2));
	        dbm.addPost(new Post(1, "Red Square", "Western", new Date(2014, 2, 01), 2));
	        dbm.addPost(new Post(1, "Red Square", "Waldo's", new Date(2014, 11, 01), 2));
	        dbm.addPost(new Post(1, "Red Square", "Beer Exchange", new Date(2014, 8, 04), 2));
	        dbm.addPost(new Post(1, "Red Square", "Food Dance", new Date(2014, 1, 17), 2));
	        dbm.addPost(new Post(2, "Red Square", "The Graveyard", new Date(2014, 4, 01), 2));
	        dbm.addPost(new Post(2, "Red Square", "D&W", new Date(2014, 1, 26), 2));
	        dbm.addPost(new Post(2, "Red Square", "Pacific Rim", new Date(2014, 3, 22), 2));
	    }
    }
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    /**
     * checks valid email and password and sends user to rideFeed
     * 
     * @param view
     */
    public void login(View view)
    {
        // gets inputed email and password
        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();

        // checks to see if username and password match in the Db
        ArrayList<User> allUsers = (ArrayList<User>) dbm.getAllUsers();
        for (User u : allUsers)
        {
            if (u.getEmail().matches(username))
            {
                if (u.getpassword().matches(password))
                {
                    Editor editor = prefs.edit();
                    editor.putString(EMAIL, username);
                    editor.putInt(ID, u.getId());
                    editor.commit();
                    
                    Intent intent = new Intent(this, RideFeedActivity.class);
                    startActivity(intent);
                    return;
                }
                Toast.makeText(getApplicationContext(), "Email and password do not match!", Toast.LENGTH_LONG).show();
                return;
            }
        }
        Toast.makeText(getApplicationContext(), "Email not found!", Toast.LENGTH_LONG).show();
    }

    // Takes user to new account Activity
    public void newAccount(View view)
    {
        Intent intent = new Intent(this, NewAccountActivity.class);
        startActivity(intent);
    }
}