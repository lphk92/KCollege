#include "sort.h"


/*----------------------------------------------------------------
   Read in an array of doubles from standard in and sort them.  Print
   the result to standard out.  No error checking is performed on the
   input                                                          
 dfd
  UNFINISHED

  Nathan Sprague 2007
  
  Finished 04/03/2011
  Lucas Kushner
----------------------------------------------------------------*/

int main () 
{
  int size;
  int min_indx;

  double* array = readDoubleArray(&size);
  
  sort(array, size);

  printDoubleArray(array, size);

  free(array);
}


