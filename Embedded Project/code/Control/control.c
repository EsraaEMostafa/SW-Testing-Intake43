/*
 * MC2.c
 *
 *  Created on: 27�/3�/2021
 *      Author: Esraa Ebrahim
 */

#include "external_eeprom.h"
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
#define NUMBER_OF_OVERFLOWS_PER_SECOND 32
#define DELAY 15
#define STOP 3
#define MIN 60

/* global variable contain the ticks count of the timer */
uint8 g_ovfTick = 0;
//uint8 g_ctcTick = 0;
uint8 g_ovfSec = 0;
//uint16 g_ctcSec = 0;

/* call back function for normal mode*/
void App_program (void){
	/*g_ovfTick increase every call */
	g_ovfTick ++ ;
	if (g_ovfTick  == NUMBER_OF_OVERFLOWS_PER_SECOND)
	{
		g_ovfSec ++ ;
		g_ovfTick=0 ;
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

int main()
{

	/* Create configuration structure for timer driver */
	Timer_ConfigType Timer_Config = {NORMAL,0,CLOCK_1024};
	//Timer_ConfigType Ctc_Timer_Config = {COMPARE,0,CLOCK_1024,255};
	/* Set the Call back function pointer in the timer driver */
	Ovf_setCallBack(App_program);
	/* Create configuration structure for UART driver */
	Uart_ConfigType Uart_Config = {BIT_8,DISABLED,BIT_1,BAUD_9600};
	/* Initialize the EEPROM , UART and timer driver */
	UART_init(&Uart_Config);
	timer_init(&Timer_Config);
	EEPROM_init();
	/* Enable Global Interrupt I-Bit */
	SREG  |= (1<<7);
	/* configure pin PB0 , PB1 and PB2 as output pins */
	DDRB |= 0x07;
	/* Motor is stop at the beginning */
	PORTB &= 0xFC;
	/* out high to enable pin*/
	PORTB |= (1<<PB2);
	/* configure pin PD3 as output pin */
	DDRD |=(1<<PD3);
	/* buzzer is stop at the beginning */
	PORTD &= ~(1<<PD3);
	/*local variables*/
	uint8 i ,val , count = 0 ,change =5 , open = 5, option,match =10;
	/*array to store the pass for the first time */
	uint8 password [NUMBER];
	/*array to store the pass for the second time */
	uint8 re_password [NUMBER];
	/* variable to handle the EEPROM address */
	uint16 address ;
	/*save the EMPTY to address 0x0311 */
	EEPROM_writeByte(0x0311 ,EMPTY);
	_delay_ms (10);
	/*send READY to the first MICRO to start*/
	if (EEPROM_readByte(0x0311 , &val))
	{
		 if (val == EMPTY)
		 {
			 UART_sendByte(READY);
		 }
	}

    while (1)
    {
    	/* step 1 : if the MICRO is ready and send EMPTY to the firstMICRO
    	 *   or when the 2 pass unmatched
    	 *   or when the user want to change the pass */
        if ((change == MATCH)||(match == UNMATCH) ||(val == EMPTY))
        {
        	count = 0 ;
        	/*to receive the pass and save it in the array*/
        	for (i=0;i<NUMBER;i++)
        	{
        		password[i]= UART_recieveByte();
        	}
        	/*to save the pass in EEPROM*/
        	for (i=0;i<NUMBER;i++)
        	{
        		address = (0x0311)+16*i;
        		EEPROM_writeByte(address , password[i]);
        		_delay_ms(10);
        	}
        	/*to receive the pass for the second time and save it in the array*/
        	for (i=0;i<NUMBER;i++)
        	{
        		re_password[i]= UART_recieveByte();
        	}
        	/*to compare the re_password and the pass in EEPROM*/
        	for (i=0;i<NUMBER;i++)
        	{
        		address = (0x0311)+16*i;
        		EEPROM_readByte(address , &val);
        		_delay_ms(10);
        		if (val == re_password[i])
        		{
        			count ++ ;
        		}
        	}
        	/*if the two pass are matched*/
        	if (count == NUMBER)
        	{
        		/*send MATCH to the first MICRO */
        		UART_sendByte(MATCH);
        		/*to go to step 2*/
        		match = MATCH ;
        	}
        	/*if the two pass are unmatched*/
        	else if (count != NUMBER)
        	{
        		/*send UNMATCH to the first MICRO */
        		UART_sendByte(UNMATCH);
        		/*to go to step 1*/
        		match = UNMATCH ;
        	}
        }
        /* step 2 : if two pass are matched*/
        if ((match == MATCH))
        {
        	count = 0 ;
        	/*receive + or - from the first MICRO */
        	option = UART_recieveByte();
        	/* receive the pass*/
        	for (i=0;i<NUMBER;i++)
			{
				re_password[i]= UART_recieveByte();
			}
        	/*to compare the re_password and the pass in EEPROM*/
			for (i=0;i<NUMBER;i++)
			{
				address = (0x0311)+16*i;
				EEPROM_readByte(address , &val);
				_delay_ms(10);
				if (val == re_password[i])
				{
				   count ++ ;
				}
			}
        }
        /*when received  + */
		if (option ==PLUS)
		{
			 /*if  the two pass are unmatched*/
			if (count != NUMBER)
			{
				/*send UNMATCH to first MICRO*/
				UART_sendByte(UNMATCH);
				 /*to go to step 2*/
				match = MATCH ;
				continue ;
			}
			/*if  the two pass are matched*/
			else if (count == NUMBER)
			{
				/*send MATCH to first MICRO*/
				UART_sendByte(MATCH);
				 /*to go to step 1*/
				change = MATCH ;
			}
			count =0 ;
		}
		/*when received  - */
		else if ((option == MINUS))
		{
			/*if  the two pass are matched*/
			 if (count == NUMBER)
			{
				/*send MATCH to first MICRO*/
				UART_sendByte(MATCH);
				/*to go to step 2*/
				open = MATCH ;
				match = MATCH ;
				/* Rotate the motor --> clock wise for 15 seconds*/
				g_ovfSec = 0;
				while(g_ovfSec < DELAY)
				{
					PORTB &= (~(1<<PB1));
					PORTB |= (1<<PB0);
				}
				g_ovfSec=0;
				/* Stop the motor for 3 seconds*/
				while(g_ovfSec < STOP)
				{
					PORTB &= (~(1<<PB0));
					PORTB &= (~(1<<PB1));
				}
				g_ovfSec =0 ;
				/* Rotate the motor --> anti-clock wise for 15 seconds*/
				while(g_ovfSec < DELAY)
				{
					PORTB &= (~(1<<PB0));
					PORTB |= (1<<PB1);
				}
				/* Stop the motor */
				PORTB &= (~(1<<PB0));
				PORTB &= (~(1<<PB1));
				count =0 ;
				continue ;
			}
			 /*if  the two pass are unmatched*/
			else if (count != NUMBER)
			{
				/*send UNMATCH to first MICRO*/
				UART_sendByte(UNMATCH);
				/* static local variable to count*/
				static uint8 block = 1 ;
				/*to compare the two pass if still unmatched */
				while (( block < 3) && (count != NUMBER))
				{
					count = 0; //initial count to zero
					block ++ ;
					/*to receive the pass and save it in the array*/
					for (i=0;i<NUMBER;i++)
					{
						re_password[i]= UART_recieveByte();
					}
					/*to compare the re_password and the pass in EEPROM*/
					for (i=0;i<NUMBER;i++)
					{
						address = (0x0311)+16*i;
						EEPROM_readByte(address , &val);
						_delay_ms(10);
						if (val == re_password[i])
						{
						   count ++ ;
						}
					}
					 /*if  the two pass are unmatched*/
					if ((count != NUMBER))
					{
						/*send UNMATCH to first MICRO*/
						UART_sendByte(UNMATCH);
					}
					 /*if  the two pass are matched*/
					else if ((count == NUMBER))
					{
						/*send MATCH to first MICRO*/
						UART_sendByte(MATCH);
						/* Rotate the motor --> clock wise for 15 seconds*/
						g_ovfSec = 0;
						while(g_ovfSec < DELAY)
						{
							PORTB &= (~(1<<PB1));
							PORTB |= (1<<PB0);
						}
						g_ovfSec=0;
						/* Stop the motor for 3 seconds*/
						while(g_ovfSec < STOP)
						{
							PORTB &= (~(1<<PB0));
							PORTB &= (~(1<<PB1));
						}
						g_ovfSec =0 ;
						/* Rotate the motor --> anti-clock wise for 15 seconds*/
						while(g_ovfSec < DELAY)
						{
							PORTB &= (~(1<<PB0));
							PORTB |= (1<<PB1);
						}
						/* Stop the motor */
						PORTB &= (~(1<<PB0));
						PORTB &= (~(1<<PB1));
						count =0 ;
						continue ;
					}
				}
				/*the user enter the pass 3 times wrong*/
				if (block == 3)
				{
					/*trigger buzzer for a minute*/
					g_ovfSec = 0;
					while(g_ovfSec < MIN)
					{
						PORTD |= (1<<PD3 );
					}
				}
				/* stop the buzzer*/
				PORTD &= ~(1<<PD3 );
				 /*to go to step 1*/
				match = UNMATCH ;
				continue ;
			   }
			}
	   }

}
