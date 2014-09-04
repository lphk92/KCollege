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
 * Utility functions for using atmega328 clocks for timing and Pulse Width
 * Modulation. 
 *
 * References: 
 * Chris Kueth's AVR tutorials: 
 *         https://www.mainframe.cx/~ckuethe/avr-c-tutorial/
 * Martin Nawrath's Arduino Sine Wave Generator: 
 *          http://interface.khm.de/index.php/
 *                lab/experiments/arduino-dds-sinewave-generator/
 *
 * Author: Nathan Sprague
 * Creation Date:   4/5/11
*/

#ifndef  AVR_TIME_H
#define	 AVR_TIME_H
#include <inttypes.h>


/*
 * SAMPLE_RATE will be used as the sample rate for timer 1.  For most
 * accurate timing, SAMPLE_RATE should evenly divide F_CPU. E.g. if
 * F_CPU is 16,000,000, reasonable values of SAMPLE_RATE would be
 * 2000, 4000, 8000, or 10000.
 */
#define SAMPLE_RATE 2000

/*
 * This function sets up the ATMega328p 16-bit timer to interrupt
 * SAMPLE_RATE times per second. This function must be called (once)
 * before using the microsecond function below.
 *
 * Parameters:  
 *   callback  -       A pointer to a function that will be called on
 *                     every interrupt.  (I.e. It will be called
 *                     SAMPLE_RATE times/second.) This may be NULL if
 *                     no callback is desired.

 *   error_callback -  Since interrupts are disabled while the callback 
 *                     function is executing, there is a danger that
 *                     interrupts may be missed if the callback takes too
 *                     long to complete.  error_callback will be called if 
 *                     it discovered that this has occured.  This may be 
 *                     NULL if no error checking is desired. 
 */
void init_timer1(void (*callback)(void), void (*error_callback)(void));


/* 
 * Returns the number of microseconds since init_timer1() was called
 * Note that this value will roll over after 71 minutes.
 */
uint32_t microsecond(void);



/* Init timer0 for fast pulse-width-modulation. Output will be on
 * Arduino pin 5. The PWM frequency is F_CPU / 256.  Once this
 * function has been called, PWM output can be set by writing to
 * OCR0B.
 * 100% on is obtaned by writing 255 to OCR0B: 
 *      OCR0B = 0xFF;
 * 0% on is obtained by writing 0 to OCR0B: 
 *      OCR0B = 0x00;
*/
void init_pwm0(void); 

#endif
