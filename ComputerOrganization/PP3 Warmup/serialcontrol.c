#include "serial.h"
#include <stdio.h>
#include <string.h>
#include <avr/io.h>

int main()
{
	init_serial_9600();

	FILE* reader = init_serial_read();
	FILE* writer = init_serial_write();

	/*setup PORTB pin 5 (which is arduino pin 13) for output */
	DDRB = _BV(DDB5);

	char message[10];
	while(1)
	{
		fgets(message, 10, reader);

		fprintf(writer, "Message Recieved: %s", message);
		if (strncmp(message, "on", 2) == 0)
		{
			/* light on */
			PORTB |= _BV(PORTB5);
			
			fprintf(writer, "light turned on\n");		
		}
		else if (strncmp(message, "off", 2) == 0)
		{
			/*light off */
			PORTB &= ~_BV(PORTB5);
			
			fprintf(writer, "light turned off\n");
		}
	}
}
