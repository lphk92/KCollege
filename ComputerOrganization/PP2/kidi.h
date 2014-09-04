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

/*
 * Kidi Files:  data structures and associated functions. 
 *
 * This file provides the data structures and declarations for reading
 * and writing files in both binary and ascii kidi file format.
 *
 * Author: Nathan Sprague
 * Creation Date:   4/5/11
 *
*/

#ifndef  KIDI_H
#define	 KIDI_H

#include <inttypes.h>
#include <stdio.h>

#define ON_EVENT  0x90
#define OFF_EVENT  0x80
#define META_EVENT  0xff


/*
 * song - the song struct represents a collection of kidi events, 
 *        along with meta information about the song.  
*/
struct song {
	uint16_t tick_length;           
	uint16_t num_events;        /* actual nbr of events in song */
	struct event* event_array;  /* array of event structs */
	uint16_t event_capacity;    /* capacity of event_array*/
};
  

/*
 * event - a single event in a kidi format song.  Typically, these
 *          events represent starting or stopping a note. 
*/
struct event {
	uint16_t delta_time;   /* delay from previous event*/
	uint8_t event_type;    /* ON_EVENT or OFF_EVENT */
	uint8_t channel;       /* indicates instrument type */
	uint16_t frequency;    /* frequency in hz */
	uint8_t velocity;      /* volume */
};



/*
 * Allocate memory for a song struct with the indicated
 * initial capacity.  
 * 
 * Returns a pointer to the allocated song, or NULL
 * if something went wrong. 
 */
struct song* init_song_struct(int initial_capacity) ;


/*
 * Adjust the capacity of the indicated song struct. 
 * Precondition: song must be a pointer to a properly initialized   
 *               song struct.
 * 
 * Returns 0 if the song was resized successfully, -1 on error.  
 */
int resize_song_struct(struct song* song, uint16_t new_size);

/*
 * Write a song struct in ascii format. 
 * Preconditions: song must be a pointer to a properly initialized   
 *                song struct.
 *                file_ptr must be a file pointer that has already been
 *                opened for writing. 
 *
 * Returns 0 if the song was written successfully, -1 on error. 
 */
int write_kidi_ascii(FILE* file_ptr, struct song* song);

/*
 * Write a song struct in binary format. 
 * Preconditions: song must be a pointer to a properly initialized   
 *                song struct.
 *                file_ptr must be a file pointer that has already been
 *                opened for writing. 
 *
 * Returns 0 if the song was written successfully, -1 on error. 
 *
 */
int write_kidi_binary(FILE* file_ptr, struct song* song);


/*
 * Read a binary kidi file and return the resulting song struct. 
 * Preconditions: file_ptr must be a file pointer that has already been
 *                opened for reading. 
 *
 * Returns a pointer to a newly allocated song struct, or NULL if there
 *       was an error. 
 */
struct song* read_kidi_binary(FILE* file_ptr);

/*
 * Read an ascii kidi file and return the resulting song struct. 
 * Preconditions: file_ptr must be a file pointer that has already been
 *                opened for reading. 
 *
 * Returns a pointer to a newly allocated song struct, or NULL if there
 *       was an error. 
 */
struct song* read_kidi_ascii(FILE* file_ptr);

#endif
