Author: Lucas Kushner

Simple Kernel Module Loader
---------------------------

This is a simple program which loads a kernel module, adds a series of information using a linked list structure, and then removes the information. The information loaded is created using a struct which contains a list_head data type, and then uses library functions for interacting with the module. Example output from the program is shown below:

[ 2561.679120] Somebody was born on 4/6/1992
[ 2561.679129] Somebody was born on 7/17/1983
[ 2561.679136] Somebody was born on 1/4/1986
[ 2561.679143] Somebody was born on 8/21/1989
[ 2561.679150] Somebody was born on 2/8/1992
[ 2561.679157] Loading Module
[ 2567.452293] Removing 4/6/1992
[ 2567.452301] Removing 7/17/1983
[ 2567.452308] Removing 1/4/1986
[ 2567.452315] Removing 8/21/1989
[ 2567.452321] Removing 2/8/1992
[ 2567.452326] Removing Module

Running the program
-------------------

To run the program, simply navigate to the directory in your terminal then type the following instructions:

make
sudo dmesg -c
sudo insmod simple.ko
sudo rmmod simple
dmesg
