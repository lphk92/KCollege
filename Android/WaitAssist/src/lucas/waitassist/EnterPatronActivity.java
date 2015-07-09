package lucas.waitassist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * An Activity for entering new Patrons into the database.
 * @author Lucas Kushner
 *
 */
public class EnterPatronActivity extends Activity 
{
	public static final String TABLE_ID = "lucas.waitassist.TABLE_ID";
	public static final String SEAT_ID = "lucas.waitassist.SEAT_ID";
	public static final String MENU_SELECTION = "lucas.waitassist.MENU_SELECTION";

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_patron);
		
		Spinner menu = (Spinner) findViewById(R.id.menu);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.menu_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		menu.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.enter_patron, menu);
		return true;
	}
	
	/**
	 * Sends the user back to the MainAcitivity, with the newly entered Patron's
	 * information included for entering into the databas
	 * @param view
	 */
	public void submitPatron(View view)
	{
		String tableId = ((EditText)findViewById(R.id.tableId)).getText().toString();
		String seatId = ((EditText)findViewById(R.id.seatId)).getText().toString();
		String menuSelection = ((Spinner)findViewById(R.id.menu)).getSelectedItem().toString();
		
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(TABLE_ID, tableId);
		intent.putExtra(SEAT_ID, seatId);
		intent.putExtra(MENU_SELECTION, menuSelection);
		
		startActivity(intent);
	}
}
