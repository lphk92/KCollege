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

#include "io_utils.h"

int write_le_uint16(FILE* file, uint16_t num ) 
{
	if (fwrite(&(((char*)(&num))[1]), sizeof(char), 1, file) != 1)
		return -1;
	if (fwrite(&(((char*)(&num))[0]), sizeof(char), 1, file) != 1)
		return -1;
	return 0;
}

int read_uint8(FILE* file, uint8_t* num) 
{
	if (fread(num, sizeof(char), 1, file) != 1)
		return -1;
	else
		return 0;
}

int read_uint16(FILE* file, uint16_t* num) {
	uint8_t byte;
	if (fread(&byte, sizeof(char), 1, file) != 1)
		return -1;
	*num = byte << 8;
	if (fread(&byte, sizeof(char), 1, file) != 1)
		return -1;
	*num |= byte;
	return 0;
}
