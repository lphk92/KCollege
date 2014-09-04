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

#include "time.h"
#include <stdio.h>
#include <avr/io.h>


int main() {
	
	/*turn on timer1 without callbacks */
	init_timer1(NULL, NULL);
	
	/*setup PORTB pin 5 (which is arduino pin 13) for output */
	DDRB = _BV(DDB5);
	
	
	uint32_t cur_time = 0;	
	while(1){	
		cur_time = microsecond();
		
		/*blink light on pin 13 at one-second intervals */
		if ((cur_time % 2000000) > 1000000) {
			/*light off */
			PORTB &= ~_BV(PORTB5);
		} else { 
			/* light on */
			PORTB |= _BV(PORTB5);
		}
	}
	
}
