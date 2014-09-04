#include "time.h"
#include <stdlib.h>
#include <avr/interrupt.h>

volatile uint32_t num_interrupts;

void (*call)(void);
void (*error_call)(void);


void init_timer1(void (*callback)(void), void (*error_callback)(void))
{
	num_interrupts = 0;
	TCCR1B = _BV(CS10); /*no prescaling(table 15-5)*/
	TCCR1B |= _BV(WGM12); /*  set ctc mode (table 15-4) */
	OCR1A = F_CPU / SAMPLE_RATE; /*set the output compare register */
	TIMSK1 |= _BV(OCIE1A); /*enable compare A match interrupt 15.11.8*/ 

	call = callback;
	error_call  = error_callback;

	sei(); /* enable interrupts */
}

ISR(TIMER1_COMPA_vect)
{
	num_interrupts++;
	if (call != NULL)
		call();
	
	if (TIFR1 & _BV(OCF1A)) {
		//another interrupt occurred
		//while we were handling this interrupt! BAD!
		if (error_call != NULL) {
	      
			error_call();
		}
		
	}
	
}

uint32_t microsecond() 
{
	/*turn off interrupts while we get the time */
	uint8_t oldSREG = SREG;
	cli();
	
	uint32_t ret_val;
	if (TIFR1 & _BV(TOV1)) /* latest interrupt hasn't been counted yet*/
		ret_val = (num_interrupts+1) * (1000000L / SAMPLE_RATE);
	else 
		ret_val = num_interrupts * (1000000L / SAMPLE_RATE);
	
	ret_val += (TCNT1 * 1000000L) / F_CPU;

	/* restore interrupts */
	SREG = oldSREG;

	return ret_val;
	
}


void init_pwm0() {
  TCCR0A |= _BV(WGM01) | _BV (WGM00); /*set fast pwm mode (table 14-8 p. 109) */
  TCCR0A |= _BV(COM0B1); /*non-inverting mode (table 14-3) */
  TCCR0B |= _BV(CS00);   /*no prescaling(table 14-9)*/


  OCR0B = 0x00;  /*output compare register 0xFF==full duty cycle */
  DDRD = _BV(5); /* (checkout pinout on p.2 upper right.)
                    0C0B is linked to pin d5 (arduino pin5) .*/
}
