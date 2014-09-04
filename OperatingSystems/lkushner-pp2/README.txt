Author: Lucas Kushner

Multithreaded Sudoku Validator
------------------------------

This is a program that will validate a completed sudoku board. The validation is done through the use of 11 threads, 1 to check the columns, 1 to check the rows, and 9 threads to check each of the 3x3 grids within the board. The completed board is read through a file input, which is given as a command line argument. Example output from a valid and invalid board is given below.

From valid.dat

The sudoku puzzle is valid!

From invalid.dat

Invalid in grid with top-left corner at row 6, column 0
Invalid at column 1
Invalid at row 8


Running the program
-------------------

To run the program, simply navigate to the directory in your terminal then type the following command:

javac *.java
java Driver invalid.dat (this could also be the name of any other appropriately formatted data file)
