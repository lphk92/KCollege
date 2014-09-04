/*---------------------------------------------------------------------
 *  See time.h for license information. 
 *   Portions of this file are copied from : 
 *   wiring.c - Partial implementation of the Wiring API for the ATmega8.
 *   Part of Arduino - http://www.arduino.cc/
 *
 *   Copyright (c) 2005-2006 David A. Mellis
 *
 *----------------------------------------------------------------------*/



#include "time.h"
#include <stdlib.h>
#include <avr/interrupt.h>

volatile uint32_t num_interrupts = 0;

volatile int sample_interrupt_flag;

void init_timer1(void)
{
	TCCR1B = _BV(CS10); /*no prescaling(table 15-5)*/
	TCCR1B |= _BV(WGM12); /*  set ctc mode (table 15-4) */
	OCR1A = F_CPU / SAMPLE_RATE; /*set the output compare register */
	TIMSK1 |= _BV(OCIE1A); /*enable compare A match interrupt 15.11.8*/ 
	
	sei(); /* enable interrupts */
}

ISR(TIMER1_COMPA_vect)
{
	sample_interrupt_flag = 1;
}


void init_timer2() {
	TCCR2B = _BV(CS22);   /*  prescaling 64*/
	TIMSK2 |= _BV(TOIE2);  /*  enable overflow interrupt */
	sei(); /* enable interrupts */
}


void init_timer0() {
  TCCR0A = _BV(WGM01) | _BV (WGM00); /*set fast pwm mode (table 14-8 p. 109) */
  TCCR0A |= _BV(COM0B1); /*non-inverting mode (table 14-3) */
  TCCR0B = _BV(CS00);   /*no prescaling(table 14-9)*/

  OCR0B = 0x00;  /*output compare register 0xFF==full duty cycle */
  DDRD |= _BV(5); /* (checkout pinout on p.2 upper right.)
                    0C0B is linked to pin d5 (arduino pin5) .*/
}


/*--------from here down code is taken from wiring.c --------*/

#define CLOCK_CYCLES_PER_MICROSECOND (F_CPU / 1000000)


// the prescaler is set so that timer2 ticks every 64 clock cycles, and the
// the overflow handler is called every 256 ticks.
#define MICROSECONDS_PER_TIMER2_OVERFLOW (CLOCK_CYCLES_PER_MICROSECOND * 64 * 256)

// the whole number of milliseconds per timer2 overflow
#define MILLIS_INC (MICROSECONDS_PER_TIMER2_OVERFLOW / 1000)

// the fractional number of milliseconds per timer2 overflow. we shift right
// by three to fit these numbers into a byte. (for the clock speeds we care
// about - 8 and 16 MHz - this doesn't lose precision.)
#define FRACT_INC ((MICROSECONDS_PER_TIMER2_OVERFLOW % 1000) >> 3)
#define FRACT_MAX (1000 >> 3)

volatile unsigned long timer2_overflow_count = 0;
volatile unsigned long timer2_millis = 0;
static unsigned char timer2_fract = 0;

SIGNAL(TIMER2_OVF_vect)
{
	// copy these to local variables so they can be stored in registers
	// (volatile variables must be read from memory on every access)
	unsigned long m = timer2_millis;
	unsigned char f = timer2_fract;

	m += MILLIS_INC;
	f += FRACT_INC;
	if (f >= FRACT_MAX) {
		f -= FRACT_MAX;
		m += 1;
	}

	timer2_fract = f;
	timer2_millis = m;
	timer2_overflow_count++;
}

uint32_t millisecond()
{
	unsigned long m;
	uint8_t oldSREG = SREG;

	// disable interrupts while we read timer0_millis or we might get an
	// inconsistent value (e.g. in the middle of a write to timer2_millis)
	cli();
	m = timer2_millis;
	SREG = oldSREG;

	return m;
}

uint32_t microsecond() {
	unsigned long m;
	uint8_t oldSREG = SREG, t;
	
	cli();
	m = timer2_overflow_count;
	t = TCNT2;

#ifdef TIFR2
	if ((TIFR2 & _BV(TOV2)) && (t < 255))
		m++;
#else
	if ((TIFR & _BV(TOV2)) && (t < 255))
		m++;
#endif

	SREG = oldSREG;
	
	return ((m << 8) + t) * (64 / CLOCK_CYCLES_PER_MICROSECOND);
}




