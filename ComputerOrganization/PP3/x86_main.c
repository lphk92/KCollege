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
 * bki2pcm: Simple utility that converts binary .bki files to raw pcm
 *          (pulse code modulation) audio files.  Output files are
 *          just a sequence of unsigned 8-bit integers representing
 *          audio sample values.  These files can be read and played
 *          using Audacity by selecting: File -> Import -> Raw Data...
 *          The sampling rate is determined by the SAMPLE_RATE value 
 *          set in "time.h".
 *
 *  usage: bki2pcm infile.bki outfile.pcm
 *
 *       Where infile.bki is the binary kidi file. 
 *
 * Author: Nathan Sprague
 *
 * Creation Date:   4/28/11
 *
 * Modified By: Lucas Kushner
 * Last Modification Date: 5/18/11
 */

#include "kidi.h"
#include <stdio.h>
#include "synth.h"
#include "time.h"

int main(int argc, char* argv[]) 
{

	if (argc != 3) 
	{
		fprintf(stderr, "Two input arguments: infile.bki outfile.pcm.\n");
		return -1;
	}

	FILE* in_file;
	FILE* out_file;

	if ((in_file = fopen(argv[1], "r")) == NULL) 
	{
		fprintf(stderr, "Could not open file %s for reading.\n", argv[1]);
		return -1;
	}

	if ((out_file = fopen(argv[2], "w")) == NULL) 
	{
		fprintf(stderr, "Could not open file %s for writing.\n", argv[2]);
		return -1;
	}
	
	init_synth();

	struct song* song_ptr;
	song_ptr = read_kidi_binary(in_file);	
	
	uint32_t cur_time = 0;
	uint8_t cur_sample;

	while(gen_sample(&cur_sample, cur_time, song_ptr))
	{
		fwrite(&cur_sample, sizeof(char), 1, out_file);
		
		cur_time += 1000000 / SAMPLE_RATE;
	}


	fclose(in_file);
	fclose(out_file);
	return 0;
}
