/**
 * This class represent a thread that will be used
 * to check if a given grid of a sudoku puzzle as
 * all of the numbers 1 through 9.
 *
 * @author Lucas Kushner
 */
public class GridChecker implements Runnable
{
    private int index;
    private int left;
    private int top;

    /**
     * The constructor for the GridChecker
     */
    public GridChecker(int conditionIndex, int l, int t)
    {
        this.index = conditionIndex;
        this.left = l;
        this.top = t;
    }

    /**
     * The run function required by the Runnable interface.
     * This method runs on the thread, and uses the given
     * left and top corner of the grid to check that particular
     * grid for the numbers 1 through 9.
     */
    public void run()
    {
        // An array for checking 1 through 9
        boolean[] checks = new boolean[9];
        for (int r = this.top ; r < this.top+3 ; r++)
        {
            for (int c = this.left ; c < this.left+3 ; c++)
            {
                // If a number is repeated, then the grid is invalid
                if (checks[Driver.board[r][c]-1] == true)
                {
                    System.out.println("Invalid in grid with top-left corner at row " + top + ", column " + left);
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
