#include "time.h"
#include <stdio.h>
#include <avr/io.h>
#include <util/delay.h>

int main() 
{
	/* set PORTB for output*/
	DDRB = 0xFF;
	/* set PORTD for input*/
	DDRD &= ~_BV(PORTD2);
	PORTD |= _BV(PORTD2);
	
	int dance = 0;
	while (1) 
	{
		_delay_ms(100);
		if (PIND & _BV(PORTD2))
		{
			dance = ~dance;
		}
		if (dance == 0)
		{
			PORTB |= _BV(PORTB0);
			PORTB |= _BV(PORTB5);
		}
		else
		{
			// Makes the lights to a "dance" which gets
			// progressively slower.
			int a;
			for (a = 1 ; a <= 10; a++)
			{
				int b;
				for (b = 0; b <= a; b++)
				{
					PORTB |= _BV(PORTB0);
					PORTB &= ~_BV(PORTB5);
					
					_delay_ms(50);
					
					PORTB &= ~_BV(PORTB0);
					PORTB |= _BV(PORTB5);
					
					_delay_ms(50);
				}
				
				_delay_ms(a * 100);
			}
			dance = 0;
		}
	}
	return 0;
}
