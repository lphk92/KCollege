#include <stdio.h>
#include <string.h>
#include <ctype.h>

int isNumeric(char* str)
{
	int a;
	for(a = 0 ; a < strlen(str) ; a++)
	{
		if (!isdigit(str[a]))
		{
			return 0;
		}
	}
	
	return 1;
}

int main(int argc, char* argv[])
{
	//Because the first argument will always be the command used
	//to call the program, we need to start looking for actual arguments
	//at index 1
	if (argc != 4)
	{
		printf("This program requires exactly 3 arguments.\nPlease make sure to place the phrase in between double quotation marks.\n");
		printf("Example: \"Hello\" 5 output.txt\n");
		return 1;
	}
	
	char* phrase;
	int num;
	int a;
	char* fileName;
	
	phrase = (char*)argv[1];
	
	if (isNumeric(((char*)argv[2])))
	{
		num = strtol(((char*)argv[2]), NULL, 0);
	}
	else
	{
		printf("The second argument must be a number.\n");
		printf("Example: \"Hello\" 5 output.txt\n");
		return 2;
	}
	
	fileName = (char*)argv[3];
	
	FILE* filePointer;
	filePointer = fopen(fileName, "w+");
	
	for (a = 0; a < num ; a++)
	{
		fprintf(filePointer, "%s\n", phrase);
	}
	
	fclose(filePointer);
	
	return 0;
}
