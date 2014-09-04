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
 * bki2aki: Simple utility that converts binary .bki files to their
 *             ascii equivalent. 
 * usage: 
 *           bki2aki infile.bki [outfile.aki]
 *
 * Where infile.kid is the binary kidi file.  If the optional second argument
 * is provided, output will be written to the indicated file.  Otherwise
 * output will be sent to stdout.
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
	
	if (argc != 2)
	{
		fprintf(stderr, "Must have at least 1 argument: infile.aki [outfile.aki]\n");
		return 1;
	}
	
	// Read the binary file
	filePointer = fopen(argv[1], "rt");
	struct song* test = read_kidi_binary(filePointer);
	fclose(filePointer);
	
	// Write the file in ascii
	if (argc == 3)
		filePointer = fopen(argv[2], "w+");	
	else
		filePointer = stdout;
	write_kidi_ascii(filePointer, test);	
	fclose(filePointer);
	
	return 0;

}
