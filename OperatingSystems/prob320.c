#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define MIN_PID 300
#define MAX_PID 5000

static unsigned char* map;

int allocate_map(void)
{
    // Allocate bitmap for managing pids
    map = malloc(sizeof(char) * (MAX_PID - MIN_PID)/sizeof(char));
}

int allocate_pid(void)
{
    int i;
    char* ptr = map;

    for (i = 0; i < MAX_PID - MIN_PID ; i++)
    {
        if (i > 0 && i % 8 == 0)
        {
            ptr++;
        }

        if (!((*ptr >> i%8) & 1))
        {
            *ptr |= 1 << i%8;
            return MIN_PID + i;
        }
    }

    return -1;
}

void release_pid(int pid)
{
    int i;
    char* ptr = map;

    for (i = 0 ; i < (pid - MIN_PID) / 8 ; i++)
    {
        ptr++;
    }

    *ptr &= ~(1 << ((pid - MIN_PID) % 8));

    printf("Released pid %d\n", pid);
}

int main(void)
{
    int i;

    allocate_map();

    for (i = 0 ; i < 2 ; i++)
    {
        int pid = allocate_pid();
        printf("Allocated new pid: %d\n", pid);
    }

    release_pid(301);
    release_pid(304);
    printf("Allocated new pid: %d\n", allocate_pid());
    printf("Allocated new pid: %d\n", allocate_pid());
    printf("Allocated new pid: %d\n", allocate_pid());
    printf("Allocated new pid: %d\n", allocate_pid());
    printf("Allocated new pid: %d\n", allocate_pid());
    release_pid(306);
    printf("Allocated new pid: %d\n", allocate_pid());
    printf("Allocated new pid: %d\n", allocate_pid());
    printf("Allocated new pid: %d\n", allocate_pid());
    printf("Allocated new pid: %d\n", allocate_pid());
    printf("Allocated new pid: %d\n", allocate_pid());
    release_pid(301);
    printf("Allocated new pid: %d\n", allocate_pid());
    printf("Allocated new pid: %d\n", allocate_pid());
}

