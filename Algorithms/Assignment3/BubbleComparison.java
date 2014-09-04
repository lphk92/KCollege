import java.util.*;
import java.text.DecimalFormat;

/**
 * A class that will run a comparison of two Bubble Sort algorithms.
 * The first is a standard Bubble Sort algorithm with no special modifications,
 * the other is a Bubble Sort which has been improved to not redundantly check 
 * chunks of the list that may already be sorted, and were therefore no 
 * swapping will occur.
 *
 * @author Lucas Kushner
 */
public class BubbleComparison
{
    // Declare global counters for each sort
    public static int bSwap;
    public static int bCompare;
    public static int nSwap;
    public static int nCompare;

    /**
     * The main method that will be used to run the comparison of
     * the various bubble sorting algorithms.
     * 
     * This method results in a series of standard outputs that
     * display the value of N for a given run, and the average, minimum,
     * and maximum number of swaps and comparisons are displayed in a
     * tabular format.
     * 
     * @param args The list of command line arguments
     */
    public static void main(String[] args)
    {
        // Print out the header for the tabular-style output
        System.out.println("N\tBubble Swap\tBubble Comp\tNew Swap\tNew Comp");

        for (int n = 100; n <= 1000 ; n += 100)
        {
            // Initialize the averages to 0
            int bSwapAve = 0;
            int bCompareAve = 0;
            int nSwapAve = 0;
            int nCompareAve = 0;
            
            // Initialize the maximums to 0
            int bSwapMax = 0;
            int bCompareMax = 0;
            int nSwapMax = 0;
            int nCompareMax = 0;

            // Initialize the minimums to an arbitrary large number
            int bSwapMin = 987654321;
            int bCompareMin = 987654321;
            int nSwapMin = 987654321;
            int nCompareMin = 987654321;

            // Set the number of iterations to perform each search at for
            // each value of N
            int numIterations = 50;

            for (int i = 0 ; i < numIterations ; i++)
            {
                // Initialize global counters
                bSwap = 0;
                bCompare = 0;
                nSwap = 0;
                nCompare = 0;

                // Generate a randomly ordered list of numbers
                int[] list = RandomGenerator.generate(n);

                // Sort the list using each algorithm
                bubbleSort(Arrays.copyOf(list, list.length));
                newBubbleSort(Arrays.copyOf(list, list.length));

                // Add the counters to the averages
                bSwapAve += bSwap;
                bCompareAve += bCompare;
                nSwapAve += nSwap;
                nCompareAve += nCompare;

                // Change all of the max values if necessary
                if(bSwap > bSwapMax) bSwapMax = bSwap;
                if(bCompare > bCompareMax) bCompareMax = bCompare;
                if(nSwap > nSwapMax) nSwapMax = nSwap;
                if(nCompare > nCompareMax) nCompareMax = nCompare;

                //Change all of the min values if necessary
                if(bSwap < bSwapMin) bSwapMin = bSwap;
                if(bCompare < bCompareMin) bCompareMin = bCompare;
                if(nSwap < nSwapMin) nSwapMin = nSwap;
                if(nCompare < nCompareMin) nCompareMin = nCompare;
            }

            // Calculate the averages
            bSwapAve /= numIterations;
            bCompareAve /= numIterations;
            nSwapAve /= numIterations;
            nCompareAve /= numIterations;

            // Output the averages
            System.out.println(n + "\t" + bSwapAve + "\t" + bCompareAve + "\t"
                                        + nSwapAve + "\t" + nCompareAve);

            // Output the maximum values 
            System.out.println("Maxs\t" + bSwapMax + "\t" + bCompareMax + "\t"
                                        + nSwapMax + "\t" + nCompareMax);

            // Output the minimum values 
            System.out.println("Mins\t" + bSwapMin + "\t" + bCompareMin + "\t"
                                        + nSwapMin + "\t" + nCompareMin);
        }
    }

    /**
     * An implementation of the Bubble Sort algorithm. This algorithm works
     * by making the maximum value of the list "bubble" to the end of the list.
     * The process is then repeated for each item in the list until it is
     * sorted. Each item is compared to the items next to it and
     * swapped if it is greater, and these comparisons are done N times to
     * ensure that all necessary swaps occur.
     *
     * @param list The list to be sorted
     */
    public static void bubbleSort(int[] list)
    {
        int numberOfPairs = list.length;
        boolean swappedElements = true;
        // Go until no more swapping occurs for any elements
        while (swappedElements)
        {
            // Decrease the number of elements to look through
            numberOfPairs--;
            swappedElements = false;
            for (int i = 0 ; i < numberOfPairs ; i++)
            {
                bCompare++;
                // If an element is greater than the element to its right,
                // swap them
                if(list[i] > list[i+1])
                {
                    bSwap++;
                    int temp = list[i];
                    list[i] = list[i+1];
                    list[i+1] = temp;
                    swappedElements = true;
                }
            }
        }
    }

    /**
     * A more efficient implmentation of the Bubble Sort algorithm. This
     * approach increases the efficiency of the stnadard Bubble Sort by keeping
     * track of the last location in the list where a swap was made for the
     * last run through all of the elements. Anything to the right of that 
     * location is going to already be in order, so we don't need to consider
     * it in future iterations of the list, and those comparisons are cut out.
     *
     * @param list The list to be sorted
     */
    public static void newBubbleSort(int[] list)
    {
        int numberOfPairs = list.length;
        boolean swappedElements = true;
        int lastSwap = numberOfPairs-1;
        // Go until no more swapping occurs for any elements
        while (swappedElements)
        {
            // Set the number of elements to look through to be between the
            // first element and the one that was swapped last
            numberOfPairs = lastSwap;
            swappedElements = false;
            for (int i = 0; i < numberOfPairs; i++)
            {
                nCompare++;
                // If an element is greater than the element to its right,
                // swap them
                if(list[i] > list[i+1])
                {
                    nSwap++;
                    int temp = list[i];
                    list[i] = list[i+1];
                    list[i+1] = temp;
                    swappedElements = true;
                    // Mark the index of the last swap
                    lastSwap = i;
                }
            }
        }
    }
}
