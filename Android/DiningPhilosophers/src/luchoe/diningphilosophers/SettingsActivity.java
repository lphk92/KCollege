package luchoe.diningphilosophers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SettingsActivity extends Activity 
{
	public final static String EXTRA_NUMBER = "com.example.diningphilosopher.NUMBER";
	public final static String EXTRA_MESSAGE = "com.example.diningphilosopher.MESSAGE";
	
	private Spinner spinner;
	private static String SpinnerItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		addListenerOnSpinnerItemSelected();
	}
	
	public void addListenerOnSpinnerItemSelected()
	{
		spinner = (Spinner) findViewById(R.id.edit_strategy_id);
		spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/** Called when the user clicks the Send button */
    public void sendMessage (View view)
    {
    	Intent intent = new Intent(this, MainActivity.class);
    	EditText editNum = (EditText) findViewById(R.id.edit_numbers_id);
    	String number = editNum.getText().toString();
    	
    	intent.putExtra(EXTRA_NUMBER, number);
    	intent.putExtra(EXTRA_MESSAGE, SpinnerItem);
    	startActivity(intent);
    }
}
