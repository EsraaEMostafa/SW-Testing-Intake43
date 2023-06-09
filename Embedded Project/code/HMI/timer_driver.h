/*
 * timer_driver.h
 *
 *  Created on: ??�/??�/????
 *      Author: oscar123
 */

#ifndef TIMER_DRIVER_H_
#define TIMER_DRIVER_H_


#include "std_types.h"
#include "micro_config.h"
#include "common_macros.h"

#define NORMAL_MODE 0
#define COMPARE_MODE 1
#define NULL_PTR 0

typedef enum
{
	NORMAL , COMPARE
}Mode;
typedef enum
{
	NO_CLOCK , CLOCK , CLOCK_8 ,CLOCK_64 ,CLOCK_256 ,CLOCK_1024
}Clock_Select;

typedef struct
{
	Mode mode ;
	uint8 initValue ;
	Clock_Select Prescaler ;
	uint8 compare;
}Timer_ConfigType;

void timer_init (const Timer_ConfigType * Config_Ptr);

void Ovf_setCallBack(void(*a_ptr)(void));

void Ctc_setCallBack(void(*a_ptr)(void));

#endif /* TIMER_DRIVER_H_ */
