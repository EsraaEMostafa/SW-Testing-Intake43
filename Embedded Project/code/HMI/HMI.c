/*
 * MC1.c
 *
 *  Created on:27/3�/2021
 *      Author: Esraa Ebrahim
 */

#include "lcd.h"
#include "keypad.h"
#include "uart_driver.h"
#include "timer_driver.h"

#define MATCH 1
#define UNMATCH 0
#define NUMBER  5
#define ENTER 13
#define EMPTY '#'
#define READY 'R'
#define PLUS '+'
#define MINUS '-'
#define DELAY 15
#define STOP 3
#define MIN 60
#define NUMBER_OF_OVERFLOWS_PER_SECOND 32
#define BLOCK 3

/* global variable contain the ticks count of the timer */
uint8 g_ovfTick = 0;
//uint8 g_ctcTick = 0;
uint8 g_ovfSec = 0;
//uint16 g_ctcSec = 0;

/* function to get the password from keypad and send it to the second MICRO*/
void sendReceive (void)
{
	/* local variables */
	uint8 i , enter;
	/*array to store the pass*/
	uint8 pass [i] ;
	/* for to get the pass from the keypad*/
	for (i=0;i<NUMBER;i++)
	{
		pass [i]=KeyPad_getPressedKey();
		/*display  * for each number */
		LCD_displayStringRowColumn(1,i,"*");
		_delay_ms (100);
	}
	/* get enter to continue */
	enter = KeyPad_getPressedKey();
	while (enter != ENTER)
	{
		enter = KeyPad_getPressedKey();
	}
	if (enter == ENTER)
	{
		/* send the pass to the second MICRO*/
		for (i=0;i<NUMBER;i++)
		{
			UART_sendByte(pass [i]);
		}
	}
}
/* call back function for normal mode*/
void App_program (void){
	/*g_ovfTick increase every call */
	g_ovfTick ++ ;
	if (g_ovfTick  == NUMBER_OF_OVERFLOWS_PER_SECOND)
	{
		g_ovfSec ++ ;
		g_ovfTick = 0 ;
	}
}
/*void App_Ctcprogram (void)
{
	g_ctcTick ++ ;
	if (g_ctcTick  == NUMBER_OF_OVERFLOWS_PER_SECOND)
		{
		    g_ctcTick =0 ;
		    g_ctcSec ++ ;
		}
}
*/
uint8 g_unmatch ;
/* function if the pass unmatched 3 times*/
void tryAgain (void)
{
	/* static local variable to count*/
	static uint8 block = 1 ;
	g_unmatch = UNMATCH ; // initial value
	/* to get the pass 2 times if still unmatched  */
	while (( block < BLOCK)&& (g_unmatch == UNMATCH))
	{
		/*increase the block*/
		block ++ ;
		LCD_clearScreen();
		/*display string on LCD*/
		LCD_displayString("please try again !");
		/*get the pass again*/
		sendReceive ();
		/*receive MATCH or UNMATCH to continue */
		g_unmatch = UART_recieveByte();
	}
	/*the user enter the pass 3 times wrong*/
	if (block == BLOCK)
	{
		/*display system locked for a minute on LCD */
		LCD_clearScreen();
		LCD_displayString("system locked !");
		g_ovfSec = 0;
	    while(g_ovfSec < MIN) ;
	}
	/* block back to zero*/
	block = 0 ;
}

int main ()
{
	/* Create configuration structure for timer driver */
	Timer_ConfigType Timer_Config = {NORMAL,0,CLOCK_1024};
	/* Set the Call back function pointer in the timer driver */
	Ovf_setCallBack(App_program);
	/* Create configuration structure for UART driver */
	Uart_ConfigType Uart_Config = {BIT_8,DISABLED,BIT_1,BAUD_9600};
	/* Initialize the LCD , UART and timer driver */
	UART_init(&Uart_Config);
	timer_init(&Timer_Config);
	LCD_init();
	/* Enable Global Interrupt I-Bit */
	SREG  |= (1<<7);
    /*local variables*/
	uint8 match = 10 , option , change = 10 , open = 10  ;
    /*display welcome message on LCD */
	LCD_displayStringRowColumn(0,4,"Door locker");
	LCD_displayStringRowColumn(1,4,"welcome");
	_delay_ms (200);

    while (1)
    {
    	/* step 2 : if two pass are matched*/
    	if ((match == MATCH))
		{
		   LCD_clearScreen();
		   LCD_displayString("+ : Change password");
		   LCD_displayStringRowColumn(1,0,"- : Open Door");
		   /*get  + or - from the user */
		   option = KeyPad_getPressedKey() ;
		   /*send + or - to second MICRO */
		   UART_sendByte(option);
           /*if the user choose + */
	       if (option == PLUS )
	       {
		     LCD_clearScreen();
		     LCD_displayString("Please enter old password");
		     /*get the pass and send */
		     sendReceive ();
		     /*receive 0 or 1 if the pass unmatched or matched*/
		     change = UART_recieveByte();
		     LCD_clearScreen();
		     /*if  the pass is unmatched*/
		     if (change == UNMATCH)
	         {
			     LCD_displayString("unMatched");
			     _delay_ms (200);
			     /*to go to step 2*/
			     match = MATCH ;
			     continue ;
		     }
		     /*if  the pass is matched*/
		     else if (change == MATCH)
		     {
			     LCD_displayString("Matched");
			     _delay_ms (200);
		     }
		 }
	     /*if the user choose - */
	     if ((option == MINUS))
	     {
		   LCD_clearScreen();
		   LCD_displayString("Please enter the password");
		   /*get the pass and send */
		   sendReceive ();
		   /*receive 0 or 1 if the pass unmatched or matched*/
		   open = UART_recieveByte();
		   LCD_clearScreen();
		   /*if  the pass is matched*/
		   if (open == MATCH)
		   {
			   /*display the massage for 18 seconds*/
			   LCD_displayString("Door is unlocking....");
			   g_ovfSec = 0;
			   while(g_ovfSec < DELAY+STOP)
			   {
				   /*LCD_clearScreen();
				   LCD_intgerToString(g_ovfSec);
				   LCD_displayStringRowColumn(1,0,"");
				   LCD_intgerToString(g_ovfTick);*/
			   }
			   /*display the massage for 15 seconds*/
			   g_ovfSec = 0;
			   LCD_clearScreen();
			   LCD_displayString("Door is locking....");
			   while(g_ovfSec < DELAY)
			   {

				  /* LCD_clearScreen();
				   LCD_intgerToString(g_ovfSec);
				   LCD_displayStringRowColumn(1,0,"");
				   LCD_intgerToString(g_ovfTick);*/

			   }
			   /*to go to step 2*/
			   match = MATCH ;
			   continue ;
		   }
		   /*if  the pass is unmatched*/
		   else if (open == UNMATCH)
		   {
			   LCD_displayString("UnMatched");
			   _delay_ms (100);
			   /*call function*/
			   tryAgain ();
			   /*if  the pass is matched*/
			   if (g_unmatch == MATCH)
			   {
				   LCD_clearScreen();
				   /*display the massage for 18 seconds*/
				   LCD_displayString("Door is unlocking....");
				   g_ovfSec = 0;
				   while(g_ovfSec < DELAY+STOP)
				   {
					   /*LCD_clearScreen();
					   LCD_intgerToString(g_ovfSec);
					   LCD_displayStringRowColumn(1,0,"");
					   LCD_intgerToString(g_ovfTick);*/
				   }
				   /*display the massage for 15 seconds*/
				   g_ovfSec = 0;
				   LCD_clearScreen();
				   LCD_displayString("Door is locking....");
				   while(g_ovfSec < DELAY)
				   {
					  /* LCD_clearScreen();
					   LCD_intgerToString(g_ovfSec);
					   LCD_displayStringRowColumn(1,0,"");
					   LCD_intgerToString(g_ovfTick);*/

				   }
				   /*to go to step 2*/
				   match = MATCH ;
				   continue ;
			   }
			   /*if  the pass is unmatched*/
			   else if (g_unmatch == UNMATCH)
			   {
				   /*to go to step 1*/
				   match = UNMATCH ;
			   }
		   }

	   }
	}
    	/* step 1 : if the second MICRO send READY
    	 * or when the 2 pass unmatched
    	 * or when the user want to change the pass */
    	if ((change == MATCH)||(match == UNMATCH) || (UART_recieveByte() == READY))
    	{
    		LCD_clearScreen();
    		/*display string on LCD*/
    		LCD_displayString("Please Enter New Pass");
    		 /*get the pass and send */
    		sendReceive ();
    		LCD_clearScreen();
    		/*display string on LCD*/
    		LCD_displayString("Please Re-Enter same Pass");
    		 /*get the pass and send */
    		sendReceive ();
    		/*receive 0 or 1 if the pass unmatched or matched*/
    		match = UART_recieveByte();
    		LCD_clearScreen();
    		/*if  the pass is unmatched*/
    		if (match == UNMATCH)
    	    {
    			/*display string on LCD*/
    		     LCD_displayString("UnMatched");
    		     _delay_ms (200);
    		}
    		/*if  the pass is matched*/
    		else if (match == MATCH)
			{
    			/*display string on LCD*/
				 LCD_displayString("Matched");
				 _delay_ms (200);
			}
    	}
   }
}
