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
	
	int light = 0;
	while (1) 
	{
		_delay_ms(100);
		if (PIND & _BV(PORTD2))
		{
			light = ~light;
		}
		if (light == 0)
		{
			PORTB &= ~_BV(PORTB0);
			PORTB |= _BV(PORTB5);
		}
		else
		{
			PORTB |= _BV(PORTB0);
			PORTB &= ~_BV(PORTB5);
		}
	}
	return 0;
}
