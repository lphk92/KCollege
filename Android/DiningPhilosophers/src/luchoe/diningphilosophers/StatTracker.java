package luchoe.diningphilosophers;

import java.text.DecimalFormat;
import java.util.Date;

public class StatTracker
{
    private final static DecimalFormat format = new DecimalFormat("0.00");
    
    private int thinkCounter;
    private int eatCounter;
    private int hungryCounter;
    
    private long thinkTime;
    private long eatTime;
    private long hungryTime;
    
    private Date thinkStart;
    private Date eatStart;
    private Date hungryStart;
    
    public StatTracker()
    {
        this.thinkCounter = 0;
        this.eatCounter = 0;
        this.hungryCounter = 0;
        
        this.thinkTime = 0;
        this.eatTime = 0;
        this.hungryTime = 0;
    }
    
    public void startThinkTimer()
    {
        this.thinkCounter++;
        this.thinkStart = new Date();
    }
    
    public void stopThinkTimer()
    {
        if (this.thinkStart != null)
        {
            Date now = new Date();
            this.thinkTime += now.getTime() - this.thinkStart.getTime();
        }
    }
    
    public void startEatTimer()
    {
        this.eatCounter++;
        this.eatStart = new Date();
    }
    
    public void stopEatTimer()
    {
        Date now = new Date();
        this.eatTime += now.getTime() - this.eatStart.getTime();
    }
    
    public void startHungryTimer()
    {
        this.hungryCounter++;
        this.hungryStart = new Date();
    }
    
    public void stopHungryTimer()
    {
        Date now = new Date();
        this.hungryTime += now.getTime() - this.hungryStart.getTime();
    }
    
    public String totals()
    {
        return format.format(thinkTime / 1000.0) + "sec, " +  
               format.format(eatTime / 1000.0) + "sec, " + 
               format.format(hungryTime / 1000.0) + "sec";
    }
    
    public String averages()
    {
        return format.format((thinkTime / (thinkCounter == 0 ? 1 : thinkCounter)) / 1000.0) + "sec, " +
               format.format((eatTime / (eatCounter == 0 ? 1 : eatCounter)) / 1000.0) + "sec, " + 
               format.format((hungryTime / (hungryCounter == 0 ? 1 : hungryCounter)) / 1000.0) + "sec";
    }
}
