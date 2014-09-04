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


#include "kidi.h"
#include "io_utils.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>


struct song* init_song_struct(int initial_capacity) 
{
	/* This method should allocate enough memory for the 
           song struct AND for initial_capacity events.
           All fields of the newly allocated song struct should
           be appropriately initialized. */
    struct song* new_song = (struct song*)malloc(sizeof(struct song));
    
    new_song->tick_length = 0;
    new_song->num_events = 0;
    new_song->event_array = (struct event*)malloc(sizeof(struct event) * initial_capacity);
    new_song->event_capacity = initial_capacity;

	return new_song;
}

int resize_song_struct(struct song* song, uint16_t new_size)
{
	song->event_capacity = new_size;
	song->event_array = realloc(song->event_array, sizeof(struct event) *  song->event_capacity);
	if (song->event_array == NULL) 
	{
		fprintf(stderr, "Not enough memory to resize the song!\n");
		return -1;
	}
	return 0;
}

/*
Writes the given song data to the given file in binary format.
	
PARAMETERS:
	FILE* file_ptr
		The file to write to
		
	song* song
		The song data to write to the file
		
RETURN:
	0 if okay, -1 if an error occurs

*/
int write_kidi_binary(FILE* file_ptr, struct song* song)
{
	char* heading = "KThd";
	uint32_t zero = 0;
	
	// Write the header binary
	fwrite(heading, sizeof(char), 4, file_ptr);
	write_le_uint16(file_ptr, song->tick_length);
	fwrite(&zero, sizeof(char), 4, file_ptr);
	
	// Write the even binaries
	int i;
	for (i = 0 ; i < (song->event_capacity) ; i++)
	{
		struct event curr_event = (song->event_array)[i];
		
		write_le_uint16(file_ptr, curr_event.delta_time);
		fwrite(&(curr_event.event_type), sizeof(char), 1, file_ptr);
		fwrite(&(curr_event.channel), sizeof(char), 1, file_ptr);
		write_le_uint16(file_ptr, curr_event.frequency);
		fwrite(&(curr_event.velocity), sizeof(char), 1, file_ptr);
	}
	
	// Write the "done" meta-event
	uint8_t meta_type = 0xFF;
	uint8_t meta_name = 0x2F;
	
	fwrite(&zero, sizeof(char), 2, file_ptr);
	fwrite(&meta_type, sizeof(char), 1, file_ptr);
	fwrite(&meta_name, sizeof(char), 1, file_ptr);
	
    return 0;
}

/*
Writes the given song data to the given file.
	
PARAMETERS:
	FILE* file_ptr
		The file to write to
		
	song* song
		The song data to write to the file
		
RETURN:
	0 if okay, -1 if an error occurs

*/
int write_kidi_ascii(FILE* file_ptr, struct song* song) 
{
	fprintf(file_ptr, "%d\n", song->tick_length);
	
	int a;
	for (a = 0; a < (song->num_events) ; a++)
	{
		struct event curr_event = (song->event_array)[a];
		fprintf(file_ptr, "%d ", curr_event.delta_time);
		
		if (curr_event.event_type == ON_EVENT)
		{
			fprintf(file_ptr, "on ");
		}
		else
		{
			fprintf(file_ptr, "off ");
		}
		
		fprintf(file_ptr, "%d %d %d\n", curr_event.channel, curr_event.frequency, curr_event.velocity);
	}
	
	return 0;
}

/*
Reads the given kidi binary file and creates the equivalent song.

PARAMETERS:
	FILE* file_ptr
		The pointer to the file to read;
	
RETURN:
	struct song*
		A pointer to the song that is represented by that file

*/
struct song* read_kidi_binary(FILE* file_ptr) 
{
	int init_capac = 0;
	struct song* curr_song = init_song_struct(init_capac);
	
	char header[4];
	uint32_t zero;
	
	fread(header, sizeof(char), 4, file_ptr);
	read_uint16(file_ptr, &(curr_song->tick_length));
	fread(&zero, sizeof(char), 4, file_ptr);
	
	int event_counter = 0;
	while(0 == 0)
	{
		if (feof(file_ptr))
		{
			break;
		}
	
		struct event* evt = (struct event*)malloc(sizeof(struct event));
		
		read_uint16(file_ptr, &(evt->delta_time));
		read_uint8(file_ptr, &(evt->event_type));
		read_uint8(file_ptr, &(evt->channel));
		
		// Break if the "done" meta event is found
		if (evt->channel == 0x2F)
		{
			break;
		}
		
		read_uint16(file_ptr, &(evt->frequency));
		read_uint8(file_ptr, &(evt->velocity));
	
		int curr_size = curr_song->event_capacity;
		resize_song_struct(curr_song, (curr_size+1));
		(curr_song->event_array)[curr_size] = *evt;
		event_counter++;
	}	
	
	curr_song->num_events = event_counter;
	
	return curr_song;
}

/*
Give a file, this method read the KIDI data and creates a song object.

PARAMETERS:
	FILE* file_ptr
		A pointer to the file to read from
		
RETURN:
	song*
		A pointer to the song object that was created from the file

*/
struct song* read_kidi_ascii(FILE* file_ptr) 
{
	int init_capac = 0;
	struct song* curr_song = init_song_struct(init_capac);
	
	char* line = (char*)malloc(100 * sizeof(char));
	
	// Read the tick_length from the first line
	fgets(line, 100, file_ptr);	
	int tic = strtol(line, NULL, 10);	
	curr_song->tick_length = tic;
	
	int event_counter = 0;
	while(fgets(line, 100, file_ptr) != NULL)
	{
		
		if (feof(file_ptr))
		{
			break;
		}
		
		struct event* evt = (struct event*)malloc(sizeof(struct event));
		char* tokens = (char*)malloc(32 * sizeof(char));
		tokens = strtok(line, " \t");
		
		evt->delta_time = strtol(tokens, NULL, 10);
		tokens = strtok(NULL, " \t");
		
		if (strcmp(tokens, "on") == 0)
		{
			evt->event_type = ON_EVENT;
		}
		else
		{
			evt->event_type = OFF_EVENT;
		}
		tokens = strtok(NULL, " \t");
		
		evt->channel = strtol(tokens, NULL, 10);
		tokens = strtok(NULL, " \t");
		
		evt->frequency = strtol(tokens, NULL, 10);
		tokens = strtok(NULL, " \t");
		
		evt->velocity = strtol(tokens, NULL, 10);
		
		int curr_size = curr_song->event_capacity;
		resize_song_struct(curr_song, (curr_size+1));
		(curr_song->event_array)[curr_size] = *evt;
		event_counter++;
	}	
	
	curr_song->num_events = event_counter;	
	
	return curr_song;
}

