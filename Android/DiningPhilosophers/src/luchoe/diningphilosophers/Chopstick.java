package luchoe.diningphilosophers;

public class Chopstick
{
    private int user;
    
    public Chopstick()
    {
        this.user = -1;
    }
    
    public void release()
    {
        this.user = -1;
    }
    
    public boolean isFree()
    {
        return this.user == -1;
    }
    
    public boolean isUsedBy(int philId)
    {
        return this.user == philId;
    }
    
    public int getUser()
    {
        return this.user;
    }
    
    public void setUser(int philId)
    {
        this.user = philId;
    }
    
    public String toString()
    {
        return "" + this.user;
    }
}
