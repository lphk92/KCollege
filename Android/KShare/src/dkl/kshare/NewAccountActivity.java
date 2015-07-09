package dkl.kshare;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewAccountActivity extends Activity
{
    private SharedPreferences prefs;
    private DbManager dbm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        prefs = getApplicationContext().getSharedPreferences(LoginActivity.PREFS_NAME, LoginActivity.PREFS_PRIVACY);
        dbm = new DbManager(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_account, menu);
        return true;
    }

    /**
     * Adds inputed account information, email and password, into the database
     * and takes user to the MyPostActivity.
     * 
     * @param view
     *            "register" button
     * 
     *            This method will check to see if email is valid and see if
     *            password is correct when both of these are true data is
     *            inputed into the database. From there the user is taken to the
     *            MyPostActivty.
     * 
     */
    public void add_account(View view)
    {
        // Gets info from text fields
        String username = ((EditText) findViewById(R.id.email)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        String confirmPassword = ((EditText) findViewById(R.id.confirmPassword)).getText().toString();
        
        // checks for valid email
        if (!username.contains("@"))
        {
            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_LONG).show();
            return;
        }
        // checks if passwords match
        else if (!password.matches(confirmPassword))
        {
            Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_LONG).show();
            return;
        }
        // checks if password is at least 5 letters long
        else if (!(password.length() >= 5))
        {
            Toast.makeText(getApplicationContext(), "Passwords must at least 5 letters long", Toast.LENGTH_LONG).show();
            return;
        }
        // if all tests are passed user is put in the database and user is send
        // to the ride feed
        else
        {
            dbm.addUser(new User(password, username));
            User newUser = dbm.getUser(username);
            Editor editor = prefs.edit();
            editor.putString(LoginActivity.EMAIL, username);
            editor.putInt(LoginActivity.ID, newUser.getId());
            editor.commit();
            
            Intent intent = new Intent(this, RideFeedActivity.class);
            startActivity(intent);
        }
    }

}
