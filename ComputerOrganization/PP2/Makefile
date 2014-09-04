
EXEC1 = aki2bki
EXEC2 = bki2aki
EXEC3 = aki2aki

SHAREDOBJ = io_utils.o  kidi.o

OBJ1 = aki2bki.o  $(SHAREDOBJ)
OBJ2 = bki2aki.o  $(SHAREDOBJ)
OBJ3 = aki2aki.o  $(SHAREDOBJ)

CFLAGS = -g -Wall

all: $(OBJ1) $(OBJ2) $(OBJ3)
	gcc $(CFLAGS) $(OBJ1) -o $(EXEC1)
	gcc $(CFLAGS) $(OBJ2) -o $(EXEC2)
	gcc $(CFLAGS) $(OBJ3) -o $(EXEC3)

.c.o:
	gcc $(CFLAGS) -c $<


io_utils.o: io_utils.h
kidi.o: kidi.h
aki2bki.o: kidi.h
bki2aki.o: kidi.h
aki2aki.o: kidi.h

.PHONY : clean

clean: 
	rm -f *.o $(EXEC1) $(EXEC2) $(EXEC3)
