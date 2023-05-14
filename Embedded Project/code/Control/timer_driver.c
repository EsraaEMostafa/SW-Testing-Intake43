/*
 * timer_driver.c
 *
 *  Created on: ??þ/??þ/????
 *      Author: oscar123
 */
#include "timer_driver.h"


/* Global variables to hold the address of the call back function in the application */
static volatile void (*g_callBackPtrOvf)(void) = NULL_PTR;
static volatile void (*g_callBackPtrCtc)(void) = NULL_PTR;

/* Interrupt Service Routine for timer0 normal mode */
ISR(TIMER0_OVF_vect)
{
	if(g_callBackPtrOvf != NULL_PTR)
	{
		/* Call the Call Back function in the application after the edge is detected */
		(*g_callBackPtrOvf)(); /* another method to call the function using pointer to function g_callBackPtr(); */
	}
	/*g_ovfTick ++ ;
	if (g_ovfTick  == 7813)
	{
		g_ovfTick=0 ;
		g_ovfSec =1 ;
	}*/
}
/* Interrupt Service Routine for timer0 compare mode */
ISR(TIMER0_COMP_vect)
{
	if(g_callBackPtrCtc != NULL_PTR)
	{
		/* Call the Call Back function in the application after the edge is detected */
		(*g_callBackPtrCtc)(); /* another method to call the function using pointer to function g_callBackPtr(); */
	}
	/*g_ctcTick ++ ;
	if (g_ctcTick  == 7813)
		{
		    g_ctcTick =0 ;
		    g_ctcSec  =1 ;
		}*/
}
void timer_init (const Timer_ConfigType * Config_Ptr)
{
	if (Config_Ptr->mode == NORMAL_MODE)
	{
		TCNT0 = Config_Ptr->initValue ;//Set Timer initial value
		TIMSK |= (1<<TOIE0);          // Enable Timer0 Overflow Interrupt
		/* Configure the timer control register
		 * 1. Non PWM mode FOC0=1
		 * 2. Normal Mode WGM01=0 & WGM00=0
		 * 3. Normal Mode COM00=0 & COM01=0
		 * 4. clock
		 */
		TCCR0 = (1<<FOC0) ;
		TCCR0 = (TCCR0 & 0xF8)|(Config_Ptr->Prescaler);

	}
	if(Config_Ptr->mode == COMPARE_MODE)
	{
		TCNT0 = Config_Ptr->initValue;    // Set Timer initial value
		OCR0  = Config_Ptr->compare;     // Set Compare Value
		TIMSK |= (1<<OCIE0);            // Enable Timer0 Compare Interrupt
		/* Configure timer0 control register
		 * 1. Non PWM mode FOC0=1
		 * 2. CTC Mode WGM01=1 & WGM00=0
		 * 3. No need for OC0 in this example so COM00=0 & COM01=0
		 * 4. clock
		 */
		TCCR0 = (1<<FOC0) | (1<<WGM01);
		TCCR0 =(TCCR0 & 0xF8)|(Config_Ptr->Prescaler);
	}
}
/* Description: Function to set the Call Back function address.
*/
void Ovf_setCallBack(void(*a_ptr)(void))
{
	/* Save the address of the Call back function in a global variable */
	g_callBackPtrOvf = a_ptr;
}
/* Description: Function to set the Call Back function address.
*/
void Ctc_setCallBack(void(*a_ptr)(void))
{
	/* Save the address of the Call back function in a global variable */
	g_callBackPtrCtc = a_ptr;
}


