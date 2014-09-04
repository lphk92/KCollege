#ifndef  CS230SORT_H
#define	 CS230SORT_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*----------------------------------------------------------------
Function prototypes for a set of selection sort functions. 

  UNFINISHED

  Nathan Sprague 2007
----------------------------------------------------------------*/



/*Refer to the corresponding .c file for function descriptions. */

void printDoubleArray(double array[], int size);

double* readDoubleArray(int* size);

int findMin(double array[], int start, int size);

/*Add swap and sort prototypes here.*/
void swap(double array[], int index1, int index2);

void sort(double array[], int length);

#endif
