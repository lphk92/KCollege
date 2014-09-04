/**
 * This class is contains the main method for the Sudoku validator.
 * It will load up the Sudoku puzzle to validate from a data file
 * and run the necessary operations.
 *
 * @author Lucas Kushner
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;


public class Driver
{
    // The total number of threads (simply for reference)
    public static final int NUM_THREADS = 11;

    // The sudoku board
    public static int[][] board = new int[9][9];

    // A global array of boolean variables. There is one for eah thread
    // to use to mark the if its particular section was valid or not
    public static boolean[] conditions = new boolean[NUM_THREADS];

    /**
     * The main method that will run the sudoku validation check.
     * The sudoku board is ran from an input file which is formatted as a 9X9
     * grid of space-separated numbers. 11 threads are then created to check
     * the columns, the rows, and each of the 3x3 grids within the board.
     */
    public static void main(String[] args) throws Throwable
    {
        // Read the sudoku board from file as a command-line argument
        String filename = "";
        try
        {
            filename = args[0];
        }
        catch (Exception ex)
        {
            System.err.println("A filename must be provided as a command-line argument");
            return;
        }

        Scanner scan = new Scanner(System.in);
        
        try
        {
            File file = new File(filename); 
            scan = new Scanner(file);
        }
        catch (Exception ex)
        {
            System.err.println("There was an error reading the given file.");
            ex.printStackTrace();
            return;
        }

        // Read in the board
        int row = 0;
        int col = 0;
        while(scan.hasNext())
        {
            board[col][row] = scan.nextInt();
            if (row == 8)
                col++;
            row = (row + 1) % 9;
        }
        
        // Dispatch the threads
        ArrayList<Thread> threads = new ArrayList<Thread>();

        int threadCount = 0;
        for (int i = 0 ; i < 9 ; i += 3)
        {
            for (int j = 0 ; j < 9 ; j+= 3)
            {
                Thread thread = new Thread(new GridChecker(threadCount, i, j));
                threads.add(thread);
                thread.start();
                threadCount++;
            }
        }

        Thread colThread = new Thread(new ColumnChecker(threadCount));
        Thread rowThread = new Thread(new RowChecker(threadCount + 1));

        colThread.start();
        rowThread.start();

        // Join the threads
        for (Thread thread : threads)
        {
            thread.join();
        }
        colThread.join();
        rowThread.join();

        // Check the validity of the board
        boolean valid = true;
        for (boolean b : conditions)
            if (!b) valid = false;
        if (valid)
            System.out.println("The sudoku puzzle is valid!");
    }
}
