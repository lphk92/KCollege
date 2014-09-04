import java.util.*;
import java.text.DecimalFormat;

/**
 * A class that will run a comparison of the Quick Sort, Merge Sort,
 * and Heap Sort algorithms.
 * 
 * Each of these sorting algorithms is run on a randomly ordered list of
 * numbers between 1 and N. A counter is used to keep track of the number of
 * comparisons and swaps, and the average amount of comparisons
 * performed by each algorithm is outputted for later analysis.
 * 
 * @author Lucas Kushner
 */
public class SortComparison
{
    // Declare global counters for each sort
    public static int qSwap;
    public static int qCompare;
    public static int mSwap;
    public static int mCompare;
    public static int hSwap;
    public static int hCompare;
    
    /**
     * The main method that will be used to run the comparison of
     * the various sorting algorithms.
     * 
     * This method results in a series of standard outputs that
     * display the value of N for a given run, and the average number
     * of comparisons for sorts is given in a tabular format.
     * 
     * @param args The list of command line arguments
     */
    public static void main(String[] args)
    {
        // Print out the header for the tabular-style output
        System.out.println("N\tQuick Swap\tQuick Comp\tMerge Swap\tMerge Comp\tHeap Swap\tHeap Comp");

        for (int n = 100; n <= 1000 ; n += 100)
        {
            // Initialize the averages to 0
            int qSwapAve = 0;
            int qCompareAve = 0;
            int mSwapAve = 0;
            int mCompareAve = 0;
            int hSwapAve = 0;
            int hCompareAve = 0;

            // Initialize the maximums to 0
            int qSwapMax = 0;
            int qCompareMax = 0;
            int mSwapMax = 0;
            int mCompareMax = 0;
            int hSwapMax = 0;
            int hCompareMax = 0;

            // Initialize the minimums to an arbitrary large number
            int qSwapMin = 987654321;
            int qCompareMin = 987654321;
            int mSwapMin = 987654321;
            int mCompareMin = 987654321;
            int hSwapMin = 987654321;
            int hCompareMin = 987654321;

            // Set the number of iterations to perform each search at for
            // each value of N
            int numIterations = 50;

            for (int i = 0 ; i < numIterations ; i++)
            {
                // Initialize global counters 
                qSwap = 0;
                qCompare = 0;
                mSwap = 0;
                mCompare = 0;
                hSwap = 0;
                hCompare = 0;

                // Initialize a list of random numbers
                int[] list = RandomGenerator.generate(n);

                // Run the various sorts all on their own copy of the list
                // so that no repeats occur
                quickSort(Arrays.copyOf(list, list.length), 0, list.length-1);
                mergeSort(Arrays.copyOf(list, list.length), 0, list.length-1);
                heapSort(Arrays.copyOf(list, list.length));

                // Add the counters to the averages
                qSwapAve += qSwap;
                qCompareAve += qCompare;
                mSwapAve += mSwap;
                mCompareAve += mCompare;
                hSwapAve += hSwap;
                hCompareAve += hCompare;

                // Change all of the max values if necessary
                if(qSwap > qSwapMax) qSwapMax = qSwap;
                if(qCompare > qCompareMax) qCompareMax = qCompare;
                if(mSwap > mSwapMax) mSwapMax = mSwap;
                if(mCompare > mCompareMax) mCompareMax = mCompare;
                if(hSwap > hSwapMax) hSwapMax = hSwap;
                if(hCompare > hCompareMax) hCompareMax = hCompare;

                //Change all of the min values if necessary
                if(qSwap < qSwapMin) qSwapMin = qSwap;
                if(qCompare < qCompareMin) qCompareMin = qCompare;
                if(mSwap < mSwapMin) mSwapMin = mSwap;
                if(mCompare < mCompareMin) mCompareMin = mCompare;
                if(hSwap < hSwapMin) hSwapMin = hSwap;
                if(hCompare < hCompareMin) hCompareMin = hCompare;
            }

            // Calculate the averages
            qSwapAve /= numIterations;
            qCompareAve /= numIterations;
            mSwapAve /= numIterations;
            mCompareAve /= numIterations;
            hSwapAve /= numIterations;
            hCompareAve /= numIterations;

            // Output the averages
            System.out.println(n + "\t" + qSwapAve + "\t" + qCompareAve + "\t"
                                        + mSwapAve + "\t" + mCompareAve + "\t"
                                        + hSwapAve + "\t" + hCompareAve);

            // Output the maximum values 
            System.out.println("Maxs\t" + qSwapMax + "\t" + qCompareMax + "\t"
                                        + mSwapMax + "\t" + mCompareMax + "\t"
                                        + hSwapMax + "\t" + hCompareMax);

            // Output the minimum values 
            System.out.println("Mins\t" + qSwapMin + "\t" + qCompareMin + "\t"
                                        + mSwapMin + "\t" + mCompareMin + "\t"
                                        + hSwapMin + "\t" + hCompareMin);
        }
    }

    /**
     * An implementation of the Quick Sort algorithm. This algorithm
     * takes the list, and sorts it recursively around a particular
     * pivot value, such that all elements to the left are less than the
     * pivot, and all elements to the right are greater than the pivot.
     *
     * @param list The list to be sorted
     * @param first The beginning index of the current chunk of 
     *              the list to be sorted.
     * @param last The ending index of the current chunk of the
     *              list to be sorted
     */
    public static void quickSort(int[] list, int first, int last)
    {
        // Only run while the beginning and end haven't gone past each other
        if (first < last)
        {
            // Pivot the list
            int pivot = pivotList(list, first, last);

            // Recurse on the left half of the list
            quickSort(list, first, pivot-1);

            // Recurse on the right half of the list
            quickSort(list, pivot+1, last);
        }
    }

    /**
     * The helper method for the Quick Sort algorithm which actually
     * pivots the list around a pivot value, and returns the index
     * of the pivot.
     */
    public static int pivotList(int[] list, int first, int last)
    {
        // Use the first value in the list as the pivot value
        int pivotValue = list[first];
        int pivotPoint = first;
        for (int i = first+1 ; i <= last ; i++)
        {
            qCompare++;
            // If the current element is less than the pivot, then move
            // it to the left. This is done by marking the pivot point as
            // moving onespot to the right, then switching the two elements
            if (list[i] < pivotValue)
            {
                // Mark the pivot point as one spot to the right
                pivotPoint++;

                qSwap++;

                // Swap the current element with the pivot
                int temp = list[pivotPoint];
                list[pivotPoint] = list[i];
                list[i] = temp;
            }
        }

        qSwap++;

        // Swap the pivot with the item that is currently actually in the
        // spot that is marked as where the new pivot should be
        int temp = list[pivotPoint];
        list[pivotPoint] = list[first];
        list[first] = temp;

        return pivotPoint;
    }

    /**
     * An implemenetation of the Merge Sort algorithm. This algorithm
     * recursively divides the list into smaller and smaller lists until
     * they are each one element in length, and then merges lists back 
     * together in sorted order until the full list has been reconstructed.
     *
     * @param list The list to be sorted
     * @param first The beginning index of the current chunk of 
     *              the list to be sorted.
     * @param last The ending index of the current chunk of the
     *              list to be sorted
     */
    public static void mergeSort(int[] list, int first, int last)
    {
        // Only run while the beginning and end haven't gone past each other
        if (first < last)
        {
            // Calculate the middle of the list
            int middle = (first + last) / 2;

            // Recurse on the left half of the list
            mergeSort(list, first, middle);

            // Recurse on the right half of the list
            mergeSort(list, middle + 1, last);

            // Merge the two halves back together
            mergeLists(list, first, middle, middle + 1, last);
        }
    }

    /**
     * The helper method for the Merge Sort, which merges the lists back
     * together after they have been separated.
     *
     * @param list The list to be sorted.
     * @param start1 The beginning index of the first sublist to be merged.
     * @param end1 The ending index of the first sublist to be merged.
     * @param start2 The starting index of the second sublist to be merged.
     * @param end2 The ending index of the first sublist to be merged.
     */
    public static void mergeLists(int[] list, int start1, int end1, int start2, int end2)
    {
        int finalStart = start1;
        int finalEnd = end2;
        int indexC = 0;
        
        // A resultant array for storing the merged values in as they 
        // come together
        int[] result = new int[list.length];
        while (start1 <= end1 && start2 <= end2)
        {
            mCompare++;
            if (list[start1] < list[start2])
            {
                // If the element from the first sublist is smaller insert it
                mSwap++;
                result[indexC] = list[start1];
                start1++;
            }
            else
            {
                // Otherwise, insert the element from the other sublist
                mSwap++;
                result[indexC] = list[start2];
                start2++;
            }
            indexC++;
        }

        // Move the part of the list that is left over
        if (start1 <= end1)
        {
            for(int i = start1 ; i <= end1 ; i++)
            {
                result[indexC] = list[i];
                indexC++;
            }
        }
        else
        {
            for (int i = start2; i <= end2 ; i++)
            {
                result[indexC] = list[i];
                indexC++;
            }
        }

        // Put the merged sections back into the list
        indexC = 0;
        for (int i = finalStart ; i <= finalEnd ; i++)
        {
            list[i] = result[indexC];
            indexC++;
        }
    }

    /**
     * An implementation of the Heap Sort algorithm.
     */
    public static void heapSort(int[] list)
    {
        // Create the inital heap structure
        for (int i = (list.length-1)/2 ; i >= 0 ; i--)
        {
            fixHeap(list, i, list[i], list.length-1);
        }
        // Do the heap-based sorting
        for (int i = list.length-1 ; i > 0 ; i--)
        {
            // The root element will always be the max
            int max = list[0];

            // Take out the root, then fix the heap again to put the new max
            // at the root position 
            fixHeap(list, 0, list[i], i-1);

            // Add the root to the end of the result vector
            list[i] = max;
        }
    }

    private static void fixHeap(int[] list, int root, int key, int bound)
    {
        int vacant = root;
        while (2*vacant + 1 <= bound)
        {
            int largerChild = 2*vacant+1;

            // Find the larger of the two children
            hCompare++;
            if (largerChild < bound && list[largerChild+1] > list[largerChild])
            {
                largerChild++;
            }

            // Check that the key doesn't belong to this child
            // If no, then move the larger child up
            if (key > list[largerChild])
            {
                break;
            }
            else
            {
                hSwap++;
                list[vacant] = list[largerChild];
                vacant = largerChild;
            }
        }

        list[vacant] = key;
    }
}
