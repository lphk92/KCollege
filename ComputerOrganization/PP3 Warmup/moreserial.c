#include "serial.h"
#include "time.h"
#include <stdio.h>
#include <string.h>
#include <avr/io.h>
#include <util/delay.h>

int main()
{
	init_serial_9600();

	FILE* reader = init_serial_read();
	FILE* writer = init_serial_write();

	/*setup PORTB pin 5 (which is arduino pin 13) for output */
	DDRB = _BV(DDB5) | _BV(DDB0);

	char message[10];
	while(1)
	{
		fgets(message, 10, reader);

		fprintf(writer, "Message Recieved: %s", message);
		if (strncmp(message, "on 1", 4) == 0)
		{
			/* light on */
			PORTB |= _BV(PORTB5);
		}
		else if (strncmp(message, "off 1", 5) == 0)
		{
			/*light off */
			PORTB &= ~_BV(PORTB5);
		}
		else if (strncmp(message, "on 2", 4) == 0)
		{
			PORTB |= _BV(PORTB0);
		}
		else if (strncmp(message, "off 2", 5) == 0)
		{
			PORTB &= ~_BV(PORTB0);
		}
		else if (strncmp(message, "on both", 7) == 0)
		{
			PORTB |= _BV(PORTB5);
			PORTB |= _BV(PORTB0);			
		}
		else if (strncmp(message, "off both", 8) == 0)
		{
			PORTB &= ~_BV(PORTB5);
			PORTB &= ~_BV(PORTB0);			
		}
	}
}
