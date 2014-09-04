#include <math.h>
#include "synth.h"
#include "time.h"
#include "waves.h"


#ifdef FOR_AVR

#include <avr/pgmspace.h>
PROGMEM int8_t sine_wave[] = {SINE_WAVE};
PROGMEM int8_t saw_wave[] = {SAW_WAVE};
PROGMEM int8_t ovt_wave[] = {OVT_WAVE};

#else

int8_t sine_wave[] = {SINE_WAVE};
int8_t saw_wave[] = {SAW_WAVE};
int8_t ovt_wave[] = {OVT_WAVE};

#endif


/*
 * Set up an instrument struct.  
 */

int init_instrument(struct instrument* inst, int8_t* wave_form,
		    uint16_t attack, uint16_t decay, uint16_t sustain,
		    uint16_t release) 
{
	inst->wave_form = wave_form;
	inst->attack_time = attack;
	inst->decay_time = decay;
	inst->sustain_level = sustain;
	inst->release_time = release;
	return 0;
}

/*global variables just being used to produce and manage audio */
#define MAX_NOTES 6
struct instrument piano;
struct instrument space_piano;
struct instrument oboe;
struct instrument violin;
struct note active_notes[MAX_NOTES];
uint8_t active_note_flags[MAX_NOTES];
uint8_t release_note_flags[MAX_NOTES];

uint16_t event_counter;
uint32_t last_event_time;

/*
 * Initialize the synthezier and all available instruments 
 */
int init_synth() 
{
	init_instrument(&piano, sine_wave, 20, 2000, 200, 1000);
	init_instrument(&space_piano, saw_wave, 20, 2000, 200, 1000);
	init_instrument(&oboe, ovt_wave, 2000, 2000, 512, 1000);
	init_instrument(&violin, sine_wave, 20, 400, 0, 1);
	
	event_counter = 0;
	last_event_time = 0;
	
	int a;
	for (a = 0 ; a < MAX_NOTES ; a++)
	{
		active_note_flags[a] = 0;
		release_note_flags[a] = 0;
	}

	return 0;
}

/*
 * Creates a new note from the given event 
 */
int gen_note(struct note* new_note, struct event* event)
{
	new_note->volume = event->velocity;
	
	if (event->channel == 0)
		new_note->instrument = &piano;
	else if (event->channel == 1)
		new_note->instrument = &space_piano;
	else if (event->channel == 2)
		new_note->instrument = &oboe;
	else if (event->channel == 3)
		new_note->instrument = &violin;
	else
		fprintf(stderr, "Invalid instrument!");
	
	new_note->freq_tw = pow(2,16) *	((float)(event->frequency)) / ((float)SAMPLE_RATE);
	new_note->freq_acc = 0;
	
	return 0;
} 

/*
 * Generates the wave form for the given note at the given time, creating an ADSR envelope
 */
int gen_wave(int8_t* sample, struct note* cur_note, uint32_t* cur_time)
{
	/*access the appropriate wave form to get the current raw
	  sample value. (This could probably be moved to a separate 
	  function) */
	cur_note->freq_acc += cur_note->freq_tw;
	uint8_t index = (cur_note->freq_acc >> 8);
	
#ifdef FOR_AVR
	*sample = (int8_t)pgm_read_byte_near(cur_note->instrument->wave_form + index);
#else
	*sample = cur_note->instrument->wave_form[index];
#endif

	/* adjust the volume */
	uint8_t volume;
	
	

#ifdef FOR_AVR
	if (*cur_time <= (cur_note->start_time + (cur_note->instrument->attack_time << 7)))
	{
		volume = cur_note->volume;
	}
	else
	{
		volume = (cur_note->volume * cur_note->instrument->sustain_level) /1024;
	}
#else
	if (*cur_time <= (cur_note->start_time + (cur_note->instrument->attack_time * 100)))
	{
		// Set volume for Attack period
		volume = (cur_note->volume * (*cur_time - cur_note->start_time)) / (cur_note->instrument->attack_time * 100);
	}
	else if (*cur_time <= (cur_note->start_time + (cur_note->instrument->decay_time * 100)))
	{
		// Set volume for Decay period
		uint16_t sustain_volume = (cur_note->volume * cur_note->instrument->sustain_level) / 1024;
		volume = cur_note->volume - (cur_note->volume - ((sustain_volume * (*cur_time - (cur_note->instrument->attack_time * 100))) / (cur_note->instrument->decay_time * 100)));
	}
	else
	{
		// Set volume for Sustain period
		volume = (cur_note->volume * cur_note->instrument->sustain_level) / 1024;
	}
#endif
	
	
	*sample = ((*sample) * volume) / 128;
	
	return 0;
}

/*
 * Generates the release segment of the ADSR envelope.
 *
 * Returns a 1 when a successful release volume is generated.
 * Returns a 0 when the note has been fully released.
 */
int gen_release(int8_t* sample, struct note* cur_note, uint32_t* cur_time)
{
	if (*cur_time <= (cur_note->end_time + (cur_note->instrument->release_time * 100)))
	{

		/*access the appropriate wave form to get the current raw
		  sample value. (This could probably be moved to a separate 
		  function) */
		cur_note->freq_acc += cur_note->freq_tw;
		uint8_t index = (cur_note->freq_acc >> 8);
	
	#ifdef FOR_AVR
		*sample = (int8_t)pgm_read_byte_near(cur_note->instrument->wave_form + index);
	#else
		*sample = cur_note->instrument->wave_form[index];
	#endif

		/* adjust the volume */
		uint8_t volume;
	
		uint16_t sustain_volume = (cur_note->volume * cur_note->instrument->sustain_level) / 1024;
		volume = sustain_volume - ((sustain_volume * (*cur_time - cur_note->end_time)) / (cur_note->instrument->release_time * 100));
	
		*sample = ((*sample) * volume) / 128;
		
		return 1;
	}
	else
	{
		return 0;
	}
}

int gen_sample(uint8_t* sample, uint32_t cur_time, struct song * song_ptr)
{
	struct event next_event = (song_ptr->event_array)[event_counter];
	
	/* calculate the delay for the next event in microseconds */
	int16_t tic_in_micros = (song_ptr->tick_length) * 10;
	uint32_t delay = (next_event.delta_time) * tic_in_micros;
	
	/* If the proper amount of delay for the next event has passed, process it */
	if ((cur_time - last_event_time) >= delay)
	{
		if (next_event.event_type == ON_EVENT)
		{
			int a;
			for ( a = 0 ; a < MAX_NOTES ; a++)
			{
				/* find the first unused space in the active_notes array
				   and put the new note there */
				if (active_note_flags[a] == 0)
				{
					gen_note(&(active_notes[a]), &next_event);
					(active_notes[a]).start_time = cur_time;
					active_note_flags[a] = 1;
					break;
				}
			}
			/* if it goes through the loop without finding an empty slot
			   then show an error */
			if (a == MAX_NOTES)
			{
				fprintf(stderr, "Too many simultaneous notes!\n");
			}
		}
		else if(next_event.event_type == OFF_EVENT)
		{
			/* turn off the active note with the frequency given in the event */
			uint16_t target_tw = pow(2,16) * ((float)(next_event.frequency)) / ((float)SAMPLE_RATE);
		
			//NEED TO START RELEASING NOTES INSTEAD OF FULLY TURNING THEM OFF
		
			int a;
			for (a = 0 ; a < MAX_NOTES ; a++)
			{
				if (active_note_flags[a] == 1 && active_notes[a].freq_tw == target_tw)
				{
					(active_notes[a]).end_time = cur_time;
					active_note_flags[a] = 0;
					release_note_flags[a] = 1;
					
					break;
				}
			}
		}
		else
		{
			fprintf(stderr, "Malformed Event!\t%d\n", next_event.event_type);
		}
		
		last_event_time = cur_time;
		event_counter++;		
	}	
	
	/* loop through all active notes and generate their samples */
	int8_t cur_sample;			/*sample value for one note */
	int16_t sample_total = 0;	/*sum of samples from all active notes*/
	
	int a;
	for (a = 0 ; a < MAX_NOTES ; a++)
	{
		if (active_note_flags[a] == 1)
		{
			gen_wave(&cur_sample, &(active_notes[a]), &cur_time);
		}
#ifdef FOR_AVR
#else
		else if (release_note_flags[a] == 1)
		{
			release_note_flags[a] = gen_release(&cur_sample, &(active_notes[a]), &cur_time);
		}
#endif
	
		/*add the current sample to the total for all notes*/
		sample_total += cur_sample;
	}
	
	/*adjust "0" upwards by 128 to prepare for signed->unsigned
	  conversion */
	sample_total += 128;
	
	/*clip the value so that it lies in the range of an 8-bit 
	  unsigned int */
	if (sample_total < 0) sample_total = 0;
	if (sample_total > 255) sample_total = 255;

	*sample = sample_total;
		
	if (event_counter == song_ptr->num_events)
	{
#ifdef FOR_AVR
		printf(">> Done!\n");
#else
		printf(">> Event Counter: %d\n\tended at %d\n", event_counter, cur_time);
#endif
		return 0;
	}
	else
	{
		return 1;
	}
}
