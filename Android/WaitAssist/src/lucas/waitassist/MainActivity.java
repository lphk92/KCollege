package lucas.waitassist;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

/**
 * The main activity for use in this application. Includes options for creating
 * new Patrons, storing a snapshot of the current database as a CSV file, or
 * importing an existing file to reload a previous snapshot of the database.
 * @author Lucas Kushner
 *
 */
public class MainActivity extends Activity 
{
    private PatronDbHelper helper;
    private boolean dirty;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		helper = new PatronDbHelper(this);
		dirty = false;
			
		Intent intent = this.getIntent();
		String newTableId = intent.getStringExtra(EnterPatronActivity.TABLE_ID);
		String newSeatId = intent.getStringExtra(EnterPatronActivity.SEAT_ID);
		String newMenuSelection = intent.getStringExtra(EnterPatronActivity.MENU_SELECTION);
		
		if (newTableId != null && newSeatId != null && newMenuSelection != null)
		{
			try
			{
				Patron patron = new Patron(newTableId, newSeatId, newMenuSelection);
				if (helper.addPatron(patron) >= 0)
				{
				   dirty = true;
				   Log.i("dev", "Successfully added new Patron!");
				}
				else
				{
				    Log.i("dev", "Couldn't add the patron...");
				}
			} 
			catch (Exception e) 
			{ 
				Log.i("dev", "There was an error in adding the new Patron");
				e.printStackTrace(); 
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Sends the user to the EnterPatronActivity
	 * @param view
	 */
	public void enterPatron(View view)
	{
		Intent intent = new Intent(this, EnterPatronActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Displays all information currently stored in the database
	 * @param view
	 */
	public void viewList(View view)
	{
	    List<Patron> patrons = helper.getAllPatrons();
	    
	    TextView patronInfo = (TextView) findViewById(R.id.patronInfo);
	    patronInfo.setText("TableId, SeatId, Menu");
	    for (Patron p : patrons)
	    {
	        patronInfo.setText(patronInfo.getText() + "\n" + p.toString());
	    }
	}
	
	/**
	 * Stores all information in the database into the specified
	 * CSV file
	 * @param view
	 */
	public void store(View view)
	{
	    String listName = ((TextView)findViewById(R.id.storeName)).getText().toString();
	    List<Patron> patrons = helper.getAllPatrons();
	    
	    try
	    {
    	    File file = new File(this.getFilesDir(), listName);
    	    
    	    if (!file.exists())
    	        file.createNewFile();
    	    
    	    PrintWriter fout = new PrintWriter(file);
    	    fout.println("tableId,seatId,menuSelection");
    	    for (Patron p: patrons)
    	    {
    	        fout.println(p.toString());
    	    }
    	    fout.close();
    	    
    	    Log.i("dev", "Succesfully saved file: " + listName);
    	    
	    } catch (Exception e)
	    {
	        Log.e("dev", e.toString());
	    }
	}
	
	/**
	 * Loads the specified CSV file information into the database,
	 * then redisplays the data
	 * @param view
	 */
	public void load(View view)
	{
	    String listName = ((TextView)findViewById(R.id.loadName)).getText().toString();
	    
	    try
        {
            File file = new File(this.getFilesDir(), listName);
            
            if (!file.exists())
            {
                throw new IllegalArgumentException("File name \"" + listName + "\" was not found");
            }            
            
            Scanner fin = new Scanner(file);
            helper.clear();
            fin.nextLine(); // skip header line
            while (fin.hasNextLine())
            {
                helper.addPatron(Patron.constructFromCSV(fin.nextLine()));
            }
            viewList(view);
            
            Log.i("dev", "Succesfully loaded file: " + listName);
            
        } catch (Exception e)
        {
            Log.e("dev", e.toString());
        }
	}
	
	/**
	 * Exits the app, prompting the user with a confirmation dialog.
	 * @param view
	 */
	public void exit(View view)
	{
	    if (dirty)
	    {
    	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	    builder.setMessage("Are you sure you would like to exit without saving changes to a list?");
    	    builder.setTitle("Exit Confirmation");
    	    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {                
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        MainActivity.this.finish();
                    }
                });
    	    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
    	    
    	    builder.show();
	    }
	    else
	    {
	        this.finish();
	    }
	}
}