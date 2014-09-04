import java.util.*;
import java.text.DecimalFormat;

/**
 * A class that will run a comparison of the Sequential Search 
 * and Binary Search algorithms.
 * 
 * The Sequential search is run on a randomly ordered list of numbers
 * between 1 and N, and counts for each item that it checks. 
 * 
 * The Binary search is run on a sorted list of numbers between
 * 1 and N. Because of the nature of the binary search, the list must
 * be sorted beforehand.
 * 
 * For both searches, they are run using each number between 1 and N as
 * the target, and the total number of comparisons is averages.
 * This process is then repeated for different values of N.
 * 
 * @author Lucas Kushner
 */
public class SearchComparison 
{
    // Declare global counters for each search
    public static int sCounter;
    public static int bCounter;

    // Used for formatting output
    public static DecimalFormat df = new DecimalFormat("0.00");

    /**
     * The main method that will be used to run the comparison of
     * the two algorithms.
     * 
     * This method results in a series of standard outputs that
     * display the value of N for a given run, and the average number
     * of comparisons for the Sequential and Binary searches in a
     * tabular format.
     * 
     * @param args The list of command line arguments
     */
    public static void main(String[] args)
    {
        // Print out the beginning line of output
        System.out.println("N\tSeq\tBin");
        // Run the searches for values of N between 100 and 1000
        for (int n = 100; n <= 1000 ; n += 50)
        {
            // Initialize the global counters
            sCounter = 0;
            bCounter = 0;

            // Initialize a list of random numbers
            int[] list = RandomGenerator.generate(n);

            // Sort the list of numbers for use in the binary search
            int[] sorted = Arrays.copyOf(list, list.length);
            Arrays.sort(sorted);

            for (int i = 1 ; i < n ; i++)
            {
                // Execute each of the searches for each possible number
                sequentialSearch(i, list);
                binarySearch(i, sorted);
            }

            // Calculate the average number of comparisons for sequential search
            double sAverage = (double) sCounter/ n;

            // Calculate the average number of compariusons for binary search
            double bAverage = (double) bCounter / n;

            // Print out another line of output showing the results of this search comparison
            System.out.println(n + "\t" + df.format(sAverage) + "\t" + df.format(bAverage));
        }
    }
    
    /**
     * Executes a Sequential search over the given list for
     * the given target. As it is searching, this program will
     * count the number of comparisons made for the search.
     * 
     * @param target The target number to search for
     * @param list   The list to search
     * 
     * @return The index of the number in the list if it was found.
     *         -1 if it was not found.
     */
    public static int sequentialSearch(int target, int[] list)
    {
        for(int i = 0 ; i < list.length ; i++)
        {
            // Increment the sequential search comparison counter
            sCounter++;

            // Check if we have reached the target
            if (list[i] == target)
                return i;
        }

        // Return -1 if the target was not found in the list
        return -1;
    }

    /**
     * Executes a Binary search over the given list for
     * the given target. As it is searching, this program will
     * count the number of comparisons made for the search.
     * 
     * @param target The target number to search for
     * @param list   The list to search. Note that for a binary
     *               search, this list must be sorted
     *               
     * @return The index of the number in the list if it was found.
     *         -1 if it was not found.
     */
    public static int binarySearch(int target, int[] sorted)
    {
        // Initialize the variables for the start and end of the list
        int start = 0;
        int end = sorted.length - 1;

        while(start <= end)
        {
            // Increment the binary search comparison counter
            bCounter++;

            // Calculate the middle of the current list
            int middle = (start + end) / 2;

            // Compare the middle element to the target
            switch (Integer.compare(sorted[middle], target))
            {
                // If the middle is less than target, search the right half
                case -1: start = middle+1; break;

                // If we have reached the target, return the index
                case 0: return middle;

                // If middle is greater than target, search the left half
                case 1: end = middle - 1; break;
            }
        }

        // Return -1 if the target was not found in the list
        return -1;
    }
}
