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
 * Utility functions for communicating with Arduino over a serial
 * port.
 *
 * References: 
 * Chris Kueth's AVR tutorials:
 *  https://www.mainframe.cx/~ckuethe/avr-c-tutorial/
 *
 * Author: Nathan Sprague
 * Creation Date:   4/5/11
*/


#ifndef  AVR_SERIAL_H
#define	 AVR_SERIAL_H

#include <stdio.h>

/*
 * This function initializes serial IO to and from the arduino.
 * Settings are: 8 data bits, 1 stop bit, no parity, 9600 baud.
 */
void init_serial_9600(void);

/* 
 * Returns a file pointer that can be used with avr-libc stdio routines to 
 * to produce serial output. This file pointer will not function unless
 * init_serial_9600 has been called. 
 */
FILE* init_serial_write();

/*
 * Returns a file pointer that can be used with avr-libc stdio routines to 
 * to read serial input. This file pointer will not function unless
 * init_serial_9600 has been called. 
 */
FILE* init_serial_read();

#endif
