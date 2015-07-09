package lucas.acciocar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A app that allows the user to store the location of their car, and then
 * pull up a map that will show them their location, and their car's location,
 * making it easier to find their car after parking for an event.
 * 
 * @author Lucas Kushner
 * @since November 15th, 2013
 *
 */
public class MapActivity extends Activity
{
    MapFragment mapFrag;
    GoogleMap map;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        mapFrag = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        map = mapFrag.getMap();
        prefs = getApplicationContext().getSharedPreferences(MainActivity.PREFS, 0);
        
        Marker target;
        Marker current;
        
        // Double checks that the car's coordinates are stored properly, then creates
        // the marker on the map
        if (prefs.contains(MainActivity.TARGET_LATITUDE) && prefs.contains(MainActivity.TARGET_LONGITUDE))
        {
            double targetLatitude = prefs.getFloat(MainActivity.TARGET_LATITUDE, 0.0F);
            double targetLongitude = prefs.getFloat(MainActivity.TARGET_LONGITUDE, 0.0F);
            LatLng latlng = new LatLng(targetLatitude, targetLongitude);
            
            target = map.addMarker((new MarkerOptions()).position(latlng));
            target.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            target.setTitle("Your Car");
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15F));
        }
        
        // If the user's current location has been stored, then add a marker 
        // to the map for that as well
        if (prefs.contains(MainActivity.CURRENT_LATITUDE) && prefs.contains(MainActivity.CURRENT_LONGITUDE))
        {
            double currentLatitude = prefs.getFloat(MainActivity.CURRENT_LATITUDE, 0.0F);
            double currentLongitude = prefs.getFloat(MainActivity.CURRENT_LONGITUDE, 0.0F);
            
            current = map.addMarker((new MarkerOptions()).position(new LatLng(currentLatitude, currentLongitude)));
            current.setTitle("You Are Here");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

}
