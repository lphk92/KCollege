/*
Author: Lucas Kushner

Creates a very simple kernel module to display the use of module
functions and a linked list macros

*/
#include <linux/init.h>
#include <linux/module.h>
#include <linux/kernel.h>

struct birthday
{
    int day;
    int month;
    int year;
    struct list_head list;
};

static LIST_HEAD(birthday_list);

// This function is called when the module is loaded.
int simple_init(void)
{
       int i;
       struct birthday *lucas;
       struct birthday *ptr;
       
       // Create the first birhtday, which will be the list heard
       lucas = kmalloc(sizeof(*lucas), GFP_KERNEL);
       lucas->day = 6;
       lucas->month = 4;
       lucas->year = 1992;
       INIT_LIST_HEAD(&lucas->list);

       list_add_tail(&lucas->list, &birthday_list);

        // Make some birthdays
       for(i = 1; i <= 4 ; i++)
       {
           struct birthday *bday;
           bday = kmalloc(sizeof(*bday), GFP_KERNEL);
           bday->day = i*17%30;
           bday->month = i*7%13;
           bday->year = 1980 + i*3;
           list_add_tail(&bday->list, &birthday_list);
       }

       // List the birhtday in the kernel output
       list_for_each_entry(ptr, &birthday_list, list)
       {
            printk(KERN_INFO "Somebody was born on %d/%d/%d", ptr->month, ptr->day, ptr->year);
       }

       printk(KERN_INFO "Loading Module\n");
       return 0;
}

// This function is called when the module is removed.
void simple_exit(void) 
{
    struct birthday *ptr, *next;

    list_for_each_entry_safe(ptr, next, &birthday_list, list)
    {
    printk(KERN_INFO "Removing %d/%d/%d", ptr->month, ptr->day, ptr->year);
    list_del(&ptr->list);
    kfree(ptr);
    }

	printk(KERN_INFO "Removing Module\n");
}

// Macros for registering module entry and exit points.
module_init( simple_init );
module_exit( simple_exit );

MODULE_LICENSE("GPL");
MODULE_DESCRIPTION("Simple Module");
MODULE_AUTHOR("SGG");

