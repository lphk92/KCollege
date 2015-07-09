package luchoe.diningphilosophers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends Activity
{
    public final static String EXTRA_STATS = "STATS";
    public static Monitor monitor;
    
    public MainActivity()
    {
        super();
    }
    
    private void setTable(int numPhilosophers)
    {
        Log.i("info", "Setting the table...");
        
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.layout);
        
        int tableRadius = 70;
        int width = 230;
        int height = 330;
        
        int midWidth = width / 2;
        int midHeight = height / 2;
        
        Log.i("circle", "Dimensions = " + midWidth + ", " + midHeight);
        
        double dif = 2.0 * Math.PI / numPhilosophers;
        
        monitor = new Monitor(numPhilosophers);
        for (int i = 1 ; i <= numPhilosophers ; i++)
        {
            double x = Math.cos(dif*i) * tableRadius + midWidth;
            double y = Math.sin(dif*i) * tableRadius + midHeight;
            
            Log.i("circle", "Generating philosopher at position " + x + ", " + y);
            
            PhilosopherView pv = PhilosopherView.spawn(this, x, y, monitor.getPhilosopher(i-1));
            layout.addView(pv);
        }
    }
    
    public void stop(View view)
    {
        Log.i("stat", "stopping...");
        monitor.stop();
        
        String stats = "";
        for (Philosopher p : monitor.getPhilosophers())
        {
            stats += p.getStats() + "\n\n";
        }
        
        Intent intent = new Intent(this, StatisticsActivity.class);
        intent.putExtra(EXTRA_STATS, stats);
        startActivity(intent);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Intent intent = getIntent();
        
        int num = Integer.parseInt(intent.getStringExtra(SettingsActivity.EXTRA_NUMBER));
        RangeGenerator.setRanges(100, 5000, 100, 5000);
        
        this.setTable(num);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
