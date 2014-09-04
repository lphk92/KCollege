#include "time.h"
#include "serial.h"
#include "kidi.h"
#include "synth.h"
#include <avr/io.h>
#include <util/delay.h>

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
 *  The main for the AVR KIDI Synthesizer.  Currently this just sets
 *  up serial I/O, configures the necessary timers, and starts calling
 *  gen_sample.  The following modifications need to be made: 
 *    *A .bki file should be read over the serial port before the main loop.
 *    *There needs to be logic that looks for a button press and starts 
 *     (or restarts) the song when one occurs. 
 *
 *	Author: Nathan Sprague
 *
 *	Modified By: Lucas Kushner
 * 	Last Modification Date: 5/18/11
 */

int main() 
{	
	init_serial_9600();

	stderr = stdout = init_serial_write();
	stdin = init_serial_read();
	
	init_timer0(); /*pwm timer    */
	init_timer1(); /*sample timer */
	init_timer2(); /*millisecond/microsecond timer */

	init_synth();
	
	/*setup PORTB pin 5 (which is arduino pin 13) for output */
	DDRB = _BV(DDB5);
	/* turn on a light just to show that things are ready on the arduino */
	PORTB |= _BV(PORTB5);
	
	/* set PORTD pin 2 (arduino pin 2) for input*/
	DDRD &= ~_BV(PORTD2);
	PORTD |= _BV(PORTD2);
	
	printf("Please send a .bki file\n");
	struct song* song = read_kidi_binary(stdin);
	
	printf("File Loaded\n");
	
	int end = 0;
	while(1) 
	{
		/*Busy wait until it is time for the next sample...*/
		while (sample_interrupt_flag == 0) 
			;
		sample_interrupt_flag = 0;


		/*Generate the sample, and update PWM output */
		uint32_t time = microsecond();
		uint8_t sample;
		if (end == 0 && gen_sample(&sample, time, song)) 
		{
			OCR0B = sample;
		}
		else
		{
			end = 1;
		}
		
		if (PIND & _BV(PORTD2))
		{
			init_synth();
			end = 0;
		}

		/*Check for missed interrupts */
		if (sample_interrupt_flag == 1) 
		{
			/*
			  if the flag is set before we reach the end of
			  the while loop, we missed our deadline! This is 
			  probably not a problem if it happens RARELY, but
			  it is a problem if it happens on every sample.
			*/
			fprintf(stderr, "Too slow!\n");

		}
	}
	
	/* turn off light to show things are finished */
	PORTB &= ~_BV(PORTB5);
	
	printf("Thanks for playing!\n\n");
	
	return 0;
}
