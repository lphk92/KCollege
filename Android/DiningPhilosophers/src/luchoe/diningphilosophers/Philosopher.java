package luchoe.diningphilosophers;

import android.util.Log;

public class Philosopher
{    
    public final static long INDEFINITELY = -1;
    
    private int id;
    private PhilosopherState state;
    private HungerListener listener;
    private StatTracker statTracker;    

    public Philosopher(int id)
    {
        this.id = id;
        this.state = PhilosopherState.THINKING;
        this.statTracker = new StatTracker();
        
        this.hunger();
    }
    
    public void think(long time)
    {
        statTracker.stopEatTimer();

        Log.i("stat", "Phil " + id + ": " + statTracker.averages());
        
        this.state = PhilosopherState.THINKING;
        
        if (this.listener != null)
            listener.onFull();
        
        if (time != INDEFINITELY)
        {
            statTracker.startThinkTimer();
            ThinkTask task = new ThinkTask(id);
            task.execute(time);
        }
    }
    
    public void eat()
    {
        this.eat(RangeGenerator.getEatTime());
    }
    
    public void eat(long time)
    {
        // Can only eat if it was hungry
        if (this.state == PhilosopherState.HUNGRY)
        {
            statTracker.stopHungryTimer();
            this.state = PhilosopherState.EATING;
            
            if (this.listener != null)
                listener.onBeginMeal();            
            
            EatTask task = new EatTask(id, RangeGenerator.getThinkTime());
            
            statTracker.startEatTimer();
            task.execute(time);
        }
    }
    
    public void hunger()
    {
        // Can only hunger if it was thinking
        if (this.state == PhilosopherState.THINKING)
        {
            statTracker.stopThinkTimer();
            
            this.state = PhilosopherState.HUNGRY;
            
            if (this.listener != null)
                this.listener.onHunger();

            statTracker.startHungryTimer();
        }
    }
    
    public void setHungerListener(HungerListener hl)
    {
        this.listener = hl;
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public PhilosopherState getState()
    {
        return this.state;
    }
    
    public String getStats()
    {
        return "Philosopher " + id + "\nTotal Thinking, Eating, Hungry\n" + statTracker.totals() + "\nAverage Thinking, Eating, Hungry\n" + statTracker.averages();
    }
}