import java.math.*;
import java.util.*;

/**
 * A set of helper methods for generating pseudo-random numbers.
 * This class includes methods for generating a floating-point
 * number between 0 and 1, as well as a method for generating
 * a list of numbers in a random order.
 * 
 * The techniques for generating pseudo-random numbers used
 * in this class come from the book "Analysis of Algorithms"
 * by Jeffrey McConnell, Second Edition.
 * 
 * @author Lucas Kushner
 */
public class RandomGenerator
{    
    // The maximum value of an integer in Java (2^31 - 1)
    private static final int m = 2147483647;
    private static final int a = 16807;    
    private static final int q = m / a;
    private static final int r = m % a;
    
    // A starting seed value, obtained by using the current time
    // so that it will be different every time.
    private static int seed = (int)(new Date()).getTime() % m;

    /**
     * Generates a random number between 0 and 1.
     * After the number is generated, the seed number is set to
     * the last number that was generated, so that a new number
     * will be generated the next time this method is called.
     * 
     * @return The pseudo-random number that was generated.
     */
    public static double ranNum()
    {
        int high = seed / q;
        int low = seed % q;
        int test = a * low - r * high;

        // Perform a check that there is no overflow from the value
        // limitation of an integer data type. 
        if (test > 0)
            // If the value of test was positive, then there is no problem
            seed = test;
        else
            // If the value of test was negative, this indicates that overflow
            // ocurred, so we had m to it to make it positive and within the domain
            // of values for integers
            seed = test + m;

        // Make the new seed a value between 0 and 1
        return (double)seed / m;
    }

    /**
     * Generates a list of numbers from 1 to n that is
     * randomly ordered.
     * 
     * @return The list of numbers, randomly ordered.
     */
    public static int[] generate(int n)
    {
        // Initialize the a list of numbers with all values set to 0
        int[] nums = new int[n];
        for (int i = 0 ; i < nums.length ; i++)
            nums[i] = 0;

        int location = 1;
        int freeCount = n;

        for (int i = 1 ; i < n ; i++)
        {
            // Generate a random number of indices to skip
            int skip = (int)Math.floor(freeCount * ranNum() + 1);
            while (skip > 0)
            {
                location = (location + 1) % n;
                if (nums[location] == 0)
                {
                    skip--;
                }
            }
            
            // Set the value of the index after skipping to the number
            nums[location] = i;
            
            // Decrement the number of numbers that remain to be positioned
            freeCount--;
        }

        // Return the randomly ordered list of numbers
        return nums;
    }
}
