package luchoe.diningphilosophers;

import android.os.AsyncTask;
import android.util.Log;

public class EatTask extends AsyncTask<Long, Void, Void>
{       
    private int id;
    private long thinkTime;

    public EatTask(int id, long thinkTime)
    {
        this.id = id;
        this.thinkTime = thinkTime;
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
        MainActivity.monitor.putDown(id, thinkTime);
    }
}
