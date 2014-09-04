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
 *
 *   Portions of this code are copied directly from : 
 *     wiring.c - Partial implementation of the Wiring API for the ATmega8.
 *     Part of Arduino - http://www.arduino.cc/
 *     Copyright (c) 2005-2006 David A. Mellis
 *
 *----------------------------------------------------------------------*/

/*
 * Utility functions for using atmega328 clocks for timing and Pulse Width
 * Modulation. 
 *
 * References: 
 * Chris Kueth's AVR tutorials: 
 *         https://www.mainframe.cx/~ckuethe/avr-c-tutorial/
 * Martin Nawrath's Arduino Sine Wave Generator: 
 *          http://interface.khm.de/index.php/
 *                lab/experiments/arduino-dds-sinewave-generator/
 * David A. Mellis's wiring.c
 *          Part of Arduino - http://www.arduino.cc/
 *
 * Author: Nathan Sprague
 * Creation Date:   4/5/11
*/

#include <stdint.h>


/*
 * For most accurate timing, SAMPLE_RATE should evenly divide both
 * F_CPU.  E.g. if F_CPU is 16000000, reasonable values of
 * SAMPLE_RATE would be 2000, 4000, 8000, or 10000.
 */

#define SAMPLE_RATE 8000


extern volatile int sample_interrupt_flag;

/*
 * This function sets up the ATMega328p 16-bit timer to interrupt
 * SAMPLE_RATE times per second.  sample_interrupt_flag will be
 * set to 1 on every interrupt. 
 */
void init_timer1(void);


/* Init timer0 for fast pulse-width-modulation. Output will be on
 * Arduino pin 5. The PWM frequency is F_CPU / 256.  Once this
 * function has been called, PWM output can be set by writing to
 * OCR0B.
 * 100% on is obtaned by writing 255 to OCR0B: 
 *      OCR0B = 0xFF;
 * 0% on is obtained by writing 0 to OCR0B: 
 *      OCR0B = 0x00;
*/
void init_timer0(void); 


/*
 * Init timer 2 to support microsecond and millisecond functions. 
 */
void init_timer2(void);

/* 
 * Returns the number of microseconds since init_timer2() was called
 * Note that this value will roll over after 71 minutes.  This has
 * four microsecond resolution. 
 */
uint32_t microsecond(void);


/* 
 * Returns the number of milliseconds since init_timer2() was called.
 */
uint32_t millisecond(void);
