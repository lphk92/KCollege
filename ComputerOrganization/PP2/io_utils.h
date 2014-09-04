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
 * Kidi Files:  data structures and associated functions. 
 *
 * This file provides the data structures and declarations for reading
 * and writing files in both binary and ascii kidi file format.
 *
 * Author: Nathan Sprague
 *
 * Creation Date:   4/5/11
 *
*/

#ifndef  IO_UTILS_H
#define	 IO_UTILS_H
#include<stdio.h>
#include <inttypes.h>

/* 
 * Write (in binary) a uint16 to a file in big-endian format: highest order
 *     byte first.  Handles switching byte order on a little endian computer.
 * Preconditions: file_ptr must be a file pointer that has already been
 *                opened for writing. 
 *                Platform must be little endian. 
 *
 * Returns 0 if successful, -1 on error. 
 */
int write_le_uint16(FILE* file, uint16_t num );

/* 
 * Reads a binary uint8_t from a file. Result is stored in the location
 *        pointed to by the num argument. 
 * Preconditions: file_ptr must be a file pointer that has already been
 *                opened for reading. 
 *                num must be a valid uint8_t pointer. 
 *
 * Returns 0 if successful, -1 on error. 
 */
int read_uint8(FILE* file, uint8_t* num);

/* 
 * Reads a binary uint16_t from a file.  Result is stored in the location
 *       pointed to by the num argument. This function assumes that the 
 *       uint16_t is stored in big-endian format, highest order byte first.
 * Preconditions: file_ptr must be a file pointer that has already been
 *                opened for reading. 
 *                num must be a valid uint16_t pointer
 *
 * Returns 0 if successful, -1 on error. 
 */
int read_uint16(FILE* file, uint16_t* num);


#endif
