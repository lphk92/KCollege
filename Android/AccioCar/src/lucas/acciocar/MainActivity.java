package lucas.acciocar;

import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

/**
 * A app that allows the user to store the location of their car, and then
 * pull up a map that will show them their location, and their car's location,
 * making it easier to find their car after parking for an event.
 * 
 * @author Lucas Kushner
 * @since November 15th, 2013
 *
 */
public class MainActivity extends FragmentActivity implements 
    GooglePlayServicesClient.ConnectionCallbacks,
    GooglePlayServicesClient.OnConnectionFailedListener
{
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    
    public final static String PREFS = "Location";
    public final static String TARGET_LATITUDE = "Latitude";
    public final static String TARGET_LONGITUDE = "Longitude";
    public final static String CURRENT_LATITUDE = "CurrentLatitude";
    public final static String CURRENT_LONGITUDE = "CurrentLongitude";
    
    LocationClient client;
    Location loc;
    SharedPreferences prefs;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        client = new LocationClient(this, this, this);
        prefs = getApplicationContext().getSharedPreferences(PREFS, 0);
        
        // Load up the initial UI from saved data
        if (prefs.contains(TARGET_LATITUDE) && prefs.contains(TARGET_LONGITUDE))
        {
            updateSavedUI(true);
        }
        else
        {
            updateSavedUI(false);
        }
    }
    
    /**
     * Updates the appropriate UI comopnents based on whether or not the user
     * has a saved location
     * @param saved
     */
    private void updateSavedUI(boolean saved)
    {
        if (saved)
        {
            ((TextView)findViewById(R.id.isSaved)).setText(R.string.isSaved);
            ((TextView)findViewById(R.id.savedLocation)).setText(
                    "Latitude: " + prefs.getFloat(TARGET_LATITUDE, 0.0F) + "\n" +
                    "Longitude: " + prefs.getFloat(TARGET_LONGITUDE, 0.0F));
            ((Button)findViewById(R.id.viewMap)).setEnabled(true);
        }
        else
        {
            ((TextView)findViewById(R.id.isSaved)).setText("");
            ((TextView)findViewById(R.id.savedLocation)).setText("");
            ((Button)findViewById(R.id.viewMap)).setEnabled(false);
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
     * Saves the current location using SharedPreferences
     * @param view
     */
    public void save(View view)
    {
        Editor editor = prefs.edit();
        editor.putFloat(TARGET_LATITUDE, (float)loc.getLatitude());
        editor.putFloat(TARGET_LONGITUDE, (float)loc.getLongitude());
        editor.commit();
        
        updateSavedUI(true);
    }
    
    /**
     * Pulls in new GPS data and updates all applicable components
     * @param view
     */
    public void refresh(View view)
    {
        updateLocation(client.getLastLocation());
    }
    
    /**
     * Clears out the stored location
     * @param view
     */
    public void clear(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Clear");
        builder.setMessage("Are you sure you would like to clear the stored location?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {                    
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                
                updateSavedUI(false);
            }
        });            
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {                    
            @Override
            public void onClick(DialogInterface dialog, int which)
            { }
        });
        builder.show();
    }
    
    /**
     * Takes the user to a map that shows both their current location
     * and the location of their car
     * @param view
     */
    public void viewMap(View view)
    {
        if (prefs.contains(TARGET_LATITUDE) && prefs.contains(TARGET_LONGITUDE))
        {
            Log.i("check", "View clicked");
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "No target specified", Toast.LENGTH_LONG).show();
        }
    }    
    
    /**
     * Updates both the SharedPreferences and the UI components to match
     * the given Location
     * @param location
     */
    private void updateLocation(Location location)
    {
        loc = location;
        TextView latitude = (TextView)findViewById(R.id.latitude);
        TextView longitude = (TextView)findViewById(R.id.longitude);
        
        latitude.setText(Double.toString(loc.getLatitude()));
        longitude.setText(Double.toString(loc.getLongitude()));
        
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(CURRENT_LATITUDE, (float)loc.getLatitude());
        editor.putFloat(CURRENT_LONGITUDE, (float)loc.getLongitude());
        editor.commit();
        
        GetAddressTask task = new GetAddressTask(getApplicationContext());
        task.execute(loc);
    }
    
    /**
     * An asynchronous task that is uses the geocoder to lookup the address
     * for the current location
     * @author lucas
     *
     */
    private class GetAddressTask extends AsyncTask<Location, Void, String>
    {
        private Context mContext;
        public GetAddressTask(Context context) 
        {
            super();
            mContext = context;
        }
        
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            TextView addressView = (TextView)findViewById(R.id.address);
            addressView.setText("Looking up address...");
        }
        
        @Override
        protected String doInBackground(Location... params)
        {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

            // Get the current location from the input parameter list
            Location loc = params[0];
            // Create a list to contain the result address
            List<Address> addresses = null;
            try
            {
                addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            } 
            catch (Exception ex)
            {
                Log.e("geocoder",  "Exception in getFromLocation()");
                ex.printStackTrace();
                return "Address lookup failed: " + ex.getMessage();
            }
            // If the reverse geocode returned an address
            if (addresses != null && addresses.size() > 0)
            {
                // Get the first address
                Address address = addresses.get(0);
                /*
                 * Format the first line of address (if available), city, and
                 * country name.
                 */
                String addressText = String.format("%s, %s, %s",
                        // If there's a street address, add it
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        // Locality is usually a city
                        address.getLocality(),
                        // The country of the address
                        address.getCountryName());
                // Return the text
                return addressText;
            }
            else
            {
                return "No address found";
            }
        }
        
        @Override
        protected void onPostExecute(String address)
        {
            TextView addressView = (TextView)findViewById(R.id.address);
            addressView.setText(address);
        }
    }
        
    // Below are just the necessary methods for the GPS connection
    @Override
    protected void onStart() 
    {
        super.onStart();
        client.connect();
    }

    @Override
    protected void onStop() 
    {
        client.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        Log.i("message", "Connected");
        
        // Once a connection is made, pull in the location
        updateLocation(client.getLastLocation());
    }

    @Override
    public void onDisconnected()
    {
        Log.i("message", "Disconnected");        
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        if (connectionResult.hasResolution()) 
        {
            try 
            {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this,  CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } 
            catch (IntentSender.SendIntentException e) 
            {
                e.printStackTrace();
            }
        } 
        else 
        {
            Log.e("connectionError", connectionResult.getErrorCode() + connectionResult.toString());
        }
    }
}