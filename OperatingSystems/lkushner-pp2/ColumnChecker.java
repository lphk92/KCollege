/**
 * This class represent a thread that will be used
 * to check if a given col of a sudoku puzzle as
 * all of the numbers 1 through 9.
 *
 * @author Lucas Kushner
 */
public class ColumnChecker implements Runnable
{
    private int index;

    /**
     * The constructor for the RowChecker 
     */
    public ColumnChecker(int conditionIndex)
    {
        this.index = conditionIndex;
    }

    /**
     * The run function required by the Runnable interface.
     * This method runs on the thread, and checks all columns in
     * the board for validity.
     */
    public void run()
    {
        for (int c = 0 ; c < 9 ; c++)
        {
            // An array for checking 1 through 9
            boolean[] checks = new boolean[9];
            for(int r = 0 ; r < 9 ; r++)
            {
                // If a number is repeated, then the column is invalid
                if (checks[Driver.board[r][c]-1] == true)
                {
                    System.out.println("Invalid at column " + c);
                    Driver.conditions[this.index] = false;
                    return;
                }

                // Flag the current number
                checks[Driver.board[r][c]-1] = true;
            }
        }

        Driver.conditions[this.index] = true;
    }
}
