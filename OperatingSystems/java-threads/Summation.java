public class Summation implements Runnable
{
    private int index;
    private long lower;
    private long upper;

    public Summation(int i, long l, long u)
    {
        this.index = i;
        this.lower = l;
        this.upper = u;
    }

    public void run()
    {
        System.out.println("Do sum from " + lower + " to " + upper);
        long s = 0;
        for (long i = this.lower; i <= this.upper ; i++)
        {
            s += i;
        }
        Driver.nums[index] = s;
        System.out.println("  from thread: " + s);
    }
}
