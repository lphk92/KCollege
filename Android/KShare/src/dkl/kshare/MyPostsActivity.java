package dkl.kshare;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MyPostsActivity extends Activity
{
	public final static String EXTRA_MESSAGE = "postId";
	
	private SharedPreferences prefs;
    private DbManager dbm;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        
        prefs = getApplicationContext().getSharedPreferences(LoginActivity.PREFS_NAME, LoginActivity.PREFS_PRIVACY);
        dbm = new DbManager(getApplicationContext());

        Toast.makeText(this, prefs.getString(LoginActivity.EMAIL, "Nobody is logged in"), Toast.LENGTH_LONG).show();
        
        List<Post> posts = new ArrayList<Post>();
        try
        {
            for (Post post : dbm.getAllPosts())
            {
                if (post.getUserID() == prefs.getInt(LoginActivity.ID, -1))
                {
                    posts.add(post);
                }
            }
        }
        catch (ParseException ex)
        {
            Log.e("error", ex.toString());
        }

        List<Post> userPosts = null;
		try 
		{
			userPosts = dbm.getAllUsersPosts(prefs.getInt(LoginActivity.ID, -1));
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		if(userPosts == null)
		{
			Toast.makeText(getApplicationContext(), "No posts make yet!", Toast.LENGTH_SHORT).show();
		}
		
        ArrayAdapter<Post> adapter = new ArrayAdapter<Post>(this, android.R.layout.simple_list_item_1, 
                (Post[])userPosts.toArray(new Post[userPosts.size()]));
        
        ListView rideList = (ListView) findViewById(R.id.myPostsList);
        rideList.setAdapter(adapter);
        rideList.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3)
            {
                ListView rideList = (ListView) findViewById(R.id.myPostsList);
                Post post = (Post)rideList.getItemAtPosition(position);
                
                Intent intent = new Intent(MyPostsActivity.this, EditPostActivity.class);
                int postId = post.getPostID();
                intent.putExtra(EXTRA_MESSAGE, postId);
                startActivity(intent);
            }            
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_posts, menu);
        return true;
    }
}
