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

/* Synth - header file for KIDI AVR Synthesizer. 
 *
 * Author: Nathan Sprague
 * Creation Date:   5/1/11
 * 
 * Modified By: Lucas Kushner
 * Last Modification Date: 5/18/11
*/
#ifndef  KIDI_SYNTH_H
#define	 KIDI_SYNTH_H

#include <stdint.h>
#include "kidi.h"

/*
 * The instrument struct represents the characteristics of a particular 
 * KIDI instrument - the wave form, as well as the envelope parameters.
*/
struct instrument 
{
	int8_t* wave_form;
	uint16_t attack_time;   /* in units of .0001 seconds (100 microseconds)*/
	uint16_t decay_time;    /* in units of .0001 seconds */
	uint16_t sustain_level; /* min vol will be vol * sustain_level / 1024*/
	uint16_t release_time;  /* in units of .0001 seconds */
};

/* 
 * A note struct represents an active (playing) note. 
 */
struct note 
{
	uint16_t freq_acc; /*current position in the wave form */
	uint16_t freq_tw;   /*how far to move forward in the wave for each sample */
	uint8_t volume;       /*peak volume for this note (0-127) */
	int32_t start_time;   /*in microseconds */
	int32_t end_time;	/*in microseconds (used for the release in the ADSR envelope) */
	struct instrument* instrument;
};

/*
 * This function should be called before the first call to gen_sample.
 * It sets up any necessary global variables required by the
 * synthesize code.
 */
int init_synth(void);

/*
 * Generate the next unsigned 8-bit audio sample from the current song.
 * Parameters: 
 *    sample   -- (output) generated sample will be stored here.
 *    cur_time -- current time in microseconds.
 *    song_ptr -- the song that is being played.
 * Returns: 
 *    1 if there are more samples to come, 0 if the song has ended.    
*/
int gen_sample(uint8_t* sample, uint32_t cur_time, struct song* song_ptr);

#endif
