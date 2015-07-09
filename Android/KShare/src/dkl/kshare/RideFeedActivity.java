package dkl.kshare;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class RideFeedActivity extends Activity
{
    private SharedPreferences prefs;
	private DbManager dbm;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_feed);
        
        prefs = getApplicationContext().getSharedPreferences(LoginActivity.PREFS_NAME, LoginActivity.PREFS_PRIVACY);
        Toast.makeText(this, prefs.getString(LoginActivity.EMAIL, "nobody is logged in"), Toast.LENGTH_LONG).show();
        dbm = new DbManager(this);

        // gets all posts from database
        List<Post> allPosts = new ArrayList<Post>();
        try
        {
            allPosts = dbm.getAllPosts();
        }
        catch (ParseException ex)
        {
            Log.e("error", ex.toString());
        }
        
        // deletes posts that are past current date
        // only adds post to the feed that are not from user
        Date currentDate = new GregorianCalendar().getGregorianChange();
        List<Post> posts = new ArrayList<Post>();
        
        int userId = prefs.getInt(LoginActivity.ID, -1);
        
        for(Post p: allPosts)
        {
        	if(p.getMeetingTime().after(currentDate))
        	{ // if post is out of date
        		if(!(userId == p.getUserID())) 
        		{   // if userId doesn't match current user
        		    if(p.getAvailableSeats() > 0) // if there is seats open
        		        posts.add(p);
        		}
        	}
        	else{
        	dbm.deletePost(p);
        	}
        }
        
        ArrayAdapter<Post> adapter = new ArrayAdapter<Post>(this, android.R.layout.simple_list_item_1, 
                (Post[])posts.toArray(new Post[posts.size()]));
        
        ListView rideList = (ListView) findViewById(R.id.rideList);
        rideList.setAdapter(adapter);
        rideList.setOnItemClickListener(new ListListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ride_feed, menu);
        return true;
    }
    
    /** takes user to a list of there posts */
    public void myPosts(View view)
    {
        Intent intent = new Intent(this, MyPostsActivity.class);
        startActivity(intent);
    }
    
    /** takes user to a the edit post activity */
    public void newPost(View view)
    {
        Intent intent = new Intent(this, EditPostActivity.class);
        startActivity(intent);
    }
    
    /**
     * Reserves a seat in the given ride for the current user.
     * @param post
     */
    private void reserveRide(Post post)
    {
        //subtracts 1 from the number of seats
        post.setAvailableSeats(post.getAvailableSeats()-1);
        dbm.updatePost(post);
        dbm.addRider(prefs.getInt(LoginActivity.ID, -1), post.getPostID());
   
        Toast.makeText(RideFeedActivity.this, "Successfully reserved your ride!", Toast.LENGTH_LONG).show();
        startActivity(getIntent());
    }
    
    /**
     * Generates a nicely structured confirmation message for
     * the given Post
     * @param post
     * @return The confirmation message
     */
    private String makeConfirmationMessage(Post post)
    {
        SimpleDateFormat format = new SimpleDateFormat();
        String message = "Are you sure you would like to reserve the following ride? \n\n";
        message += "Meeting loc  - " + post.getMeetingLocation() + "\n";
        message += "Destination  - " + post.getDestination() + "\n";
        message += "Meeting time - " + format.format(post.getMeetingTime());
        
        return message;
    }
    
    /**
     * A small listener that handles when the user clicks Ok on the
     * reservations confirmation dialog.
     * @author Lucas Kushner
     *
     */
    private class OkListener implements DialogInterface.OnClickListener
    {
        private Post post;
        
        public OkListener(Post post)
        {
            this.post = post;
        }
        
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
        	//subtracts 1 from the number of seats
            post.setAvailableSeats(post.getAvailableSeats()-1);
        	dbm.updatePost(post);
        	
            RideFeedActivity.this.reserveRide(post);
            Toast.makeText(RideFeedActivity.this, "Successfully reserved your ride!", Toast.LENGTH_LONG).show();
        }
    }
    
    private class ListListener implements OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3)
        {
            ListView rideList = (ListView) findViewById(R.id.rideList);
            Post post = (Post)rideList.getItemAtPosition(position);
            
            AlertDialog.Builder builder = new AlertDialog.Builder(RideFeedActivity.this);
            builder.setTitle(R.string.infoTitle);
            builder.setMessage(makeConfirmationMessage(post));
            builder.setPositiveButton(R.string.infoOk, new OkListener(post));            
            builder.setNegativeButton(R.string.infoCancel, new DialogInterface.OnClickListener()
            {                    
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    // Don't do anything on cancellation, just let the dialog go away
                }
            });
            
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        if((keyCode == KeyEvent.KEYCODE_BACK)) 
        {
            Intent intent = new Intent(RideFeedActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
