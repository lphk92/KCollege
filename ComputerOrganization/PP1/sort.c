#include "sort.h"


/*----------------------------------------------------------------
  Selection sort functions. 

  UNFINISHED

  Nathan Sprague 2007
  
  Finished 04/03/2011
  Lucas Kushner
----------------------------------------------------------------*/




/*----------------------------------------------------------------*/
/*printDoubleArray - Print an array of doubles to standard out.  
  PARAMETERS: 
    array - The array to print.
    size  - The number of elements in the array.

NOTE - The declaration of the first parameter here could be: 
"double * array" instead of "double array[]" The two are EXACTLY 
equivalent.
                                                                  */
/*----------------------------------------------------------------*/
void printDoubleArray(double array[], int size)
{
	int i = 0;
  for (i =0; i<size; i++) 
  {
    printf("%lf \n", array[i]);
  }
}

/*----------------------------------------------------------------*/
/*readDoubleArray - Read an array of doubles from standard in,
  allocating as much memory as neccesary. 
  RETURN VALUE: 
    A pointer to the beginning of the array
  PARAMETERS: 
    size - A pointer to an integer. On function return, the integer 
           will contain the size of the array. 

NOTE - Commenting the code in this function would probably be a good
exercise.
                                                                  */
/*----------------------------------------------------------------*/
double* readDoubleArray(int* size) 
{
  double cur_double;
  int alloc_size=2;
  double* ret_array = (double*)malloc(sizeof(double) * alloc_size);
  double* ret_array_cur = ret_array;
  *size = 0;

  while (scanf("%lf", &cur_double) > 0) {
    if ((*size) == alloc_size)
    { /*malloc more memory if neccessary... */
      alloc_size *= 2;
      double* tmp =  (double*)malloc(sizeof(double) * alloc_size);
      memcpy(tmp, ret_array, sizeof(double) * alloc_size / 2);     
      free(ret_array);
      ret_array = tmp;
      ret_array_cur = ret_array + (*size);
    }
    (*size)++;
    *ret_array_cur = cur_double;
    ret_array_cur++;
  }
  return ret_array;
}

/*----------------------------------------------------------------*/
/*findMin - Find the index of the smallest value array of doubles, 
            starting from a specified index.
  RETURN VALUE: 
    An integer indicating the position of the smallest element.
  PARAMETERS:
    array - An array of doubles.
    start - The position in the array to begin searching for the 
            smallest integer. (Should be < size.) 
    size  - The number of elements in array.
*/
/*----------------------------------------------------------------*/
int findMin(double array[], int start, int size)
{
  int i;
  int min_indx = start;
  double cur_min = array[start];
  for (i = start; i < size; i++) 
  {
    if (array[i] < cur_min) 
    {
      cur_min = array[i];
      min_indx = i;
    }
  }
  return min_indx;

}


/*----------------------------------------------------------------*/
/*swap - Swaps the values at the given indices of the array.

	PARAMETERS:
		array - An array of doubles.
		index1 - Index of the first element to swap;
		index2 - Index of the second element to swap;
*/	
/*----------------------------------------------------------------*/
void swap(double array[], int index1, int index2)
{
	int temp = array[index1];
	array[index1] = array[index2];
	array[index2] = temp;
}

/*----------------------------------------------------------------*/
/*sort - Sorts the given array of doubles

	PARAMETERS:
		array - An array of doubles.
		length  - The number of elements in array.
*/	
/*----------------------------------------------------------------*/
void sort(double array[], int length)
{
	int a;
	
	/* For each index of the array, find the index of the smallest number
	   remaining and swap them. */
	for (a = 0 ; a < length ; a++)
	{
		int minIndex = findMin(array, a, length);
		swap(array, a, minIndex);
	}
}
