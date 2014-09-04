#include "serial.h"
#include <avr/io.h>

/*we want 8 data bits, 1 stop bit, no parity*/
void init_serial_9600(void) 
{
  /*this code taken from documentation for <util/setbaud.h> */
#define BAUD 9600
#include <util/setbaud.h>
  /*these are the baud rate registers*/
  UBRR0H = UBRRH_VALUE;
  UBRR0L = UBRRL_VALUE;
#if USE_2X
  UCSR0A |= (1 << U2X0);
#else
  UCSR0A &= ~(1 << U2X0);
#endif

  /*set 8 data bits ( sec 19.4, p. 203, table 19.10)*/
  /*1 stop bit and no parity are defaults */
  UCSR0C = _BV(UCSZ00) | _BV(UCSZ01);

  /*Enable receiver and transmitter */
  UCSR0B = _BV(RXEN0) | _BV(TXEN0);
  
}

/*cput is the function for sending a single character to serial out */
int cput (char c, FILE* file) 
{
  /*19.6.1*/
  loop_until_bit_is_set(UCSR0A, UDRE0);
  UDR0 = c;
  return 0;
  
}

/*
 *cget is the function for reading a single character from the serial port
 *this will busy-wait until data is available
 * 19.7.1 p. 187 
 */
int cget(FILE* file)
{
  loop_until_bit_is_set(UCSR0A, RXC0);
  return UDR0;
}

FILE* init_serial_write()
{
  return fdevopen(cput, NULL);
}

FILE* init_serial_read() 
{
  return fdevopen(NULL, cget);
  
}
