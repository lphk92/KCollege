package luchoe.diningphilosophers;

public class RangeGenerator
{
    private static long thinkRangeMin;
    private static long thinkRangeMax;
    private static long eatRangeMin;
    private static long eatRangeMax;
    
    public static void setRanges(long tMin, long tMax, long eMin, long eMax)
    {
        thinkRangeMin = tMin;
        thinkRangeMax = tMax;
        eatRangeMin = eMin;
        eatRangeMax = eMax;
    }
    
    public static long getThinkTime()
    {
        return (long)(Math.random() * (thinkRangeMax - thinkRangeMin)) + thinkRangeMin;
    }
    
    public static long getEatTime()
    {
        return (long)(Math.random() * (eatRangeMax - eatRangeMin)) + eatRangeMin;
    }
}
