/*---------------------------------------------------------------------
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>
 *----------------------------------------------------------------------*/

/*
 * aki2bki: simple utility that reads in a kidi file in ascii format
 *          and converts it to binary format. 
 * usage: 
 *           aki2bki infile.aki outfile.bki
 *
 * where infile.aki is a text file containing kidi events.  Binary output 
 * will be written to outfile.bki .
 *
 * Author: Nathan Sprague
 *
 * Creation Date:   4/5/11
 *
*/

#include "kidi.h"


int main (int argc, char* argv[]) 
{
	FILE* filePointer;
	
	if (argc != 3)
	{
		fprintf(stderr, "Must have 2 arguments: infile.aki and outfile.bki\n");
		return 1;
	}
	
	// Read the file
	filePointer = fopen(argv[1], "rt");
	struct song* test = read_kidi_ascii(filePointer);
	fclose(filePointer);
	
	// Write the file to binary
	filePointer = fopen(argv[2], "w+");	
	write_kidi_binary(filePointer, test);	
	fclose(filePointer);
	
	return 0;
}
