/*
 * uart_driver.h
 *
 *  Created on:26/3þ/2021
 *      Author: Esraa Ebrahim
 */

#ifndef UART_DRIVER_H_
#define UART_DRIVER_H_

#include "std_types.h"
#include "common_macros.h"
#include "micro_config.h"

typedef enum
{
	BIT_5 =0,BIT_6 =1,BIT_7 =2,BIT_8 =3,BIT_9 =7
}Number_Of_Bits;

typedef enum
{
   DISABLED =0 ,EVEN_PARITY =2 ,ODD_PARITY =3
}Parity_Mode;

typedef enum
{
	BIT_1 ,BIT_2
}Numer_Of_Stop;

typedef enum
{
	BAUD_100 =100 ,BAUD_200 =200 ,BAUD_300 =300 ,BAUD_1200 =1200 ,
	BAUD_2400 =2400 ,BAUD_4800 =4800 ,BAUD_9600 =9600 ,BAUD_19200 =19200 ,
	BAUD_38400 =38400 ,BAUD_57600 =57600 ,BAUD_115200 =115200
}Baud_Rate;

typedef struct
{
	Number_Of_Bits bits ;
	Parity_Mode parity ;
	Numer_Of_Stop stop ;
	Baud_Rate baud ;
}Uart_ConfigType;

/*
 * Description : Function to initialize the UART driver
 * 	1. Set the number of bits.
 * 	2. Set the parity mode.
 * 	3. Set the number of stop bits.
 * 	4. Set the baud rate .
 */
void UART_init(const Uart_ConfigType * Config_Ptr);

/*  send byte */
void UART_sendByte(const uint8 data);

/* Receive byte */
uint8 UART_recieveByte(void);

/* send String */
void UART_sendString(const uint8 *Str);

/*  receive String  */
void UART_receiveString(uint8 *Str); // Receive until #

#endif /* UART_DRIVER_H_ */
