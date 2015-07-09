package dkl.kshare;

import java.text.ParseException;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

@SuppressLint("NewApi")
public class EditPostActivity extends Activity
{
	public static int POST_ID;
	
	private SharedPreferences prefs;
	
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        prefs = getApplicationContext().getSharedPreferences(LoginActivity.PREFS_NAME, LoginActivity.PREFS_PRIVACY);
        
        // puts post data into fields
        Intent intent = getIntent();
        POST_ID = intent.getIntExtra(MyPostsActivity.EXTRA_MESSAGE, -1);
        
        // postId will be -1 when not called from myPostActivity
        if(!(POST_ID == -1))
        {
            DbManager dbm = new DbManager(this);
            Post p = null;
            try 
            {
    			p = dbm.getPost(POST_ID);
    		}
            catch (ParseException e) 
            {
    			e.printStackTrace();
    		} 
            EditText destination = (EditText) findViewById(R.id.destination);
            EditText seats = (EditText) findViewById(R.id.seats_available);
            EditText meetingLocation = (EditText) findViewById(R.id.meeting_location);
            TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
            DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
            Button button = (Button) findViewById(R.id.button_upload);
            
            destination.setText(p.getDestination());
            seats.setText(""+ p.getAvailableSeats());
            meetingLocation.setText(p.getMeetingLocation());
            Date date = p.getMeetingTime();
            timePicker.setCurrentHour(date.getHours());
            timePicker.setCurrentMinute(date.getMinutes());
            datePicker.updateDate(date.getYear(), date.getMonth(), date.getDay());
            button.setText("Update");
        }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_post, menu);
        return true;
    }
    
    
    public void upload(View view) 
    {
    	// make new post
    	
    	// gets text from fields 
    	EditText destination = (EditText) findViewById(R.id.destination);
        EditText seats = (EditText) findViewById(R.id.seats_available);
        EditText meetingLocation = (EditText) findViewById(R.id.meeting_location);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        
        String post_destination = destination.getText().toString();
        String post_meeting_location = meetingLocation.getText().toString();
        
        if(post_destination.isEmpty())
        {
	    	Toast.makeText(getApplicationContext(), "No destination marked.", Toast.LENGTH_LONG).show();
	    	return;
        }
    	if(post_meeting_location.isEmpty())
    	{
	    	Toast.makeText(getApplicationContext(), "No meeting location marked.", Toast.LENGTH_LONG).show();
	    	return;
        }
		if(seats.getText().toString().isEmpty())
		{
			Toast.makeText(getApplicationContext(), "No seat number marked.", Toast.LENGTH_LONG).show();
	    	return;
		}
		int post_seats = Integer.parseInt(seats.getText().toString());
		
        @SuppressWarnings("deprecation")
        // puts time fields into a date object
		Date post_date = new Date(
        		datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth(),
        		timePicker.getCurrentHour(), timePicker.getCurrentMinute());
        DbManager dbm = new DbManager(this);
        // post is put into the database
        if(POST_ID == -1)
        {
	        // text field put into a post object            
	        Post p = new Post(prefs.getInt(LoginActivity.ID, -1), post_meeting_location, post_destination, post_date, post_seats);
	    	dbm.addPost(p);
	    	Toast.makeText(getApplicationContext(), "Successfully added a new Post!", Toast.LENGTH_LONG).show();
        }
        else
        {
        	Post p = new Post(prefs.getInt(LoginActivity.ID, -1), post_meeting_location, post_destination, post_date, post_seats, POST_ID);
        	dbm.updatePost(p);
        	Toast.makeText(getApplicationContext(), "Successfully updated your Post!", Toast.LENGTH_LONG).show();
        }
        
        
    	Intent intent = new Intent(EditPostActivity.this, RideFeedActivity.class);
        startActivity(intent);
    }
}