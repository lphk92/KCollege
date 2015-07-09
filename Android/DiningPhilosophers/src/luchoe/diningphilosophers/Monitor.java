package luchoe.diningphilosophers;

import android.util.Log;

public class Monitor
{
    private int n;
    private boolean run;
    private Chopstick[] chopsticks;
    private Philosopher[] philosophers;
    
    public Monitor(int n)
    {
        this.n = n;
        this.run = true;
        chopsticks = new Chopstick[n];
        philosophers = new Philosopher[n];
        for (int i = 0 ; i < n ; i++)
        {
            chopsticks[i] = new Chopstick();
            philosophers[i] = new Philosopher(i);
        }
    }
    
    public Philosopher[] getPhilosophers()
    {
        return philosophers;
    }
    
    public Philosopher getPhilosopher(int i)
    {
        return philosophers[i];
    }
    
    public void putDown(int i, long thinkTime)
    {
        Log.i("putdown", "putdown for philosopher " + i + printChops());
        int right = (i + n - 1) % n;
        
        if (chopsticks[i].isUsedBy(i))
        {
            chopsticks[i].release();
        }
        
        if (chopsticks[right].isUsedBy(i))
        {
            chopsticks[right].release();
        }
        
        philosophers[i].think(thinkTime);
        
        test((i + 1) % n);
        test(right);
    }
    
    public void test(int i)
    {
        if (run)
        {
            if (philosophers[(i + n - 1) % n].getState() != PhilosopherState.EATING &&
                philosophers[i].getState() == PhilosopherState.HUNGRY &&
                philosophers[(i + 1) % n].getState() != PhilosopherState.EATING)
            {
                chopsticks[i].setUser(i);
                chopsticks[(i + n - 1) % n].setUser(i);
                philosophers[i].eat();
            }
        }
    }
    
    public void pickup(int i)
    {
        Log.i("pickup", "pickup for philosopher " + i + printChops());
        philosophers[i].hunger();
        test(i);
        
        /*int left = i;
        int right = (i + n - 1) % n;
        
        if (i % 2 == 0)
        {
            if (chopsticks[left].isFree() || chopsticks[left].isUsedBy(i))
            {
                chopsticks[left].setUser(i);
                if (chopsticks[right].isFree() || chopsticks[right].isUsedBy(i))
                {
                    chopsticks[right].setUser(i);
                    philosophers[i].eat();
                }
            }
        }
        else
        {
            if (chopsticks[right].isFree() || chopsticks[right].isUsedBy(i))
            {
                chopsticks[right].setUser(i);
                if (chopsticks[left].isFree() || chopsticks[left].isUsedBy(i))
                {
                    chopsticks[left].setUser(i);
                    philosophers[i].eat();
                }
            }
        }*/
    }
    
    public void stop()
    {
        this.run = false;
        for (int i = 0 ; i < philosophers.length ; i++)
        {
            putDown(i, Philosopher.INDEFINITELY);
        }
    }
    
    private String printChops()
    {
        String result = " -- ";
        for(Chopstick c : chopsticks)
            result += c.toString() + " ";
        return result;
    }
}
