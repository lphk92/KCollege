#include "time.h"
#include <stdio.h>
#include <avr/io.h>


int main() 
{	
	/*turn on timer1 without callbacks */
	init_timer1(NULL, NULL);
	
	/*setup PORTB pin 5 and 0 (which is arduino pin 13 and 8) for output */
	DDRB = _BV(DDB5) | _BV(DDB0);
	
	uint32_t cur_time = 0;	
	while(1)
	{	
		cur_time = microsecond();
		
		/*blink light on pin 13 at one-second intervals */
		if ((cur_time % 2000000) > 1000000) 
		{
			/*light off */
			PORTB &= ~_BV(PORTB5);

			/*light on*/
			PORTB |= _BV(PORTB0);
		} 
		else 
		{ 
			/*light on*/
			PORTB |= _BV(PORTB5);

			/*light off*/
			PORTB &= ~_BV(PORTB0);
		}
	}
}
