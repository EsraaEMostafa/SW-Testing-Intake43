################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../HMI.c \
../keypad.c \
../lcd.c \
../timer_driver.c \
../uart_driver.c 

OBJS += \
./HMI.o \
./keypad.o \
./lcd.o \
./timer_driver.o \
./uart_driver.o 

C_DEPS += \
./HMI.d \
./keypad.d \
./lcd.d \
./timer_driver.d \
./uart_driver.d 


# Each subdirectory must supply rules for building sources it contributes
%.o: ../%.c
	@echo 'Building file: $<'
	@echo 'Invoking: AVR Compiler'
	avr-gcc -Wall -g2 -gstabs -O0 -fpack-struct -fshort-enums -ffunction-sections -fdata-sections -std=gnu99 -funsigned-char -funsigned-bitfields -mmcu=atmega16 -DF_CPU=8000000UL -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@)" -c -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


