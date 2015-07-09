package luchoe.diningphilosophers;

import android.os.AsyncTask;
import android.util.Log;

public class ThinkTask extends AsyncTask<Long, Void, Void>
{       
    private int id;

    public ThinkTask(int id)
    {
        this.id = id;
    }

    @Override
    protected Void doInBackground(Long... params)
    {
        try
        {
            Thread.sleep(params[0]);
        }
        catch (Exception ex)
        {
            Log.e("sleep", ex.getMessage());
        }
        return null;
    }
    
    @Override
    protected void onPostExecute(Void result)
    {
        MainActivity.monitor.pickup(id);
    }
}
