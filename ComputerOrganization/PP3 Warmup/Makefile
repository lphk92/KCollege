# This is a lightly modified version of the Makefile example from
# avr-libc documentation:
# http://www.nongnu.org/avr-libc/examples/demo/Makefile
# Nathan Sprague, 2011
# (Added "program" target for programming arduino with avrdude.)

PRG            = custom
OBJ            = custom.o time.o serial.o
MCU_TARGET     = atmega328p
OPTIMIZE       = -O2

DEFS           = "-DF_CPU=16000000L"
LIBS           =

# You should not have to change anything below here.

CC             = avr-gcc

# Override is only needed by avr-lib build system.
 
override CFLAGS        = -g -Wall $(OPTIMIZE) -mmcu=$(MCU_TARGET) $(DEFS)
override LDFLAGS       = -Wl,-Map,$(PRG).map

OBJCOPY        = avr-objcopy
OBJDUMP        = avr-objdump

all: $(PRG).elf lst text

$(PRG).elf: $(OBJ)
	$(CC) $(CFLAGS) $(LDFLAGS) -o $@ $^ $(LIBS)

clean:
	rm -rf *.o $(PRG).elf  $(PRG).srec $(PRG).hex $(PRG).bin *~
	rm -rf *.lst *.map $(EXTRA_CLEAN_FILES)

lst:  $(PRG).lst

%.lst: %.elf
	$(OBJDUMP) -h -S $< > $@

# Rules for building the .text rom images

text: hex bin srec

hex:  $(PRG).hex
bin:  $(PRG).bin
srec: $(PRG).srec

%.hex: %.elf
	$(OBJCOPY) -j .text -j .data -O ihex $< $@

%.srec: %.elf
	$(OBJCOPY) -j .text -j .data -O srec $< $@

%.bin: %.elf
	$(OBJCOPY) -j .text -j .data -O binary $< $@

#a rule for programming:
programuno: $(PRG).hex
	avrdude -pm328p -carduino -P/dev/ttyACM0 -b115200 -D -Uflash:w:$(PRG).hex:i
program: $(PRG).hex
	./pulsdtr.py /dev/ttyUSB0; avrdude -pm328p -cstk500v1 -P/dev/ttyUSB0 -b57600 -D -Uflash:w:$(PRG).hex:i


time.o: time.h
serial.o: serial.h
