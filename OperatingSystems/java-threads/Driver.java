/**
* Does a speed comparison in summing all numbers between
* 0 and N using a parellel, threaded solution, versus a
* serial solution.
*
* @author Lucas Kushner
**/
import java.util.*;
import java.math.*;

public class Driver
{
    public static long[] nums;
    
    public static void main (String[] args) throws Throwable
    {
        long lower = 0;
        long upper = args[0] != null ? Long.parseLong(args[0]) : 123456789;

        // Speed test with threads        
        ArrayList<Thread> threads = new ArrayList<Thread>();
        int numThreads = Runtime.getRuntime().availableProcessors();
        long split = upper / numThreads;

        nums = new long[(int)(upper/split) + 1];
        int index = 0;

        Date tStart = new Date();

        while (lower <= upper)
        {
            Thread thread = new Thread(
                    new Summation(index, lower, upper > lower + split ? lower + split : upper));
            threads.add(thread);
            thread.start();

            lower += split+ 1;
            index++;
        }

        long total = 0;
        for (int j = 0 ; j < threads.size() ; j++)
        {
            threads.get(j).join();
            total += nums[j];
        }
        
        Date tEnd = new Date();

        System.out.println("Summattion from 0 to " + upper + " = " + total);
        System.out.println("Completed by threads in " + (tEnd.getTime() - tStart.getTime()) + " ms");
        
        // Speed test with serial
        Date sStart = new Date();
        
        total = 0;
        for (long i = 0; i <= upper ; i++)
        {
            total += i;
        }
        
        Date sEnd = new Date();
        
        System.out.println("Summattion from 0 to " + upper + " = " + total);
        System.out.println("Completed by serial in " + (sEnd.getTime() - sStart.getTime()) + " ms");

    }
}
