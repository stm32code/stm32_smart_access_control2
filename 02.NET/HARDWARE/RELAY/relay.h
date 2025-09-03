#ifndef __RELAY_H
#define __RELAY_H

#include "stm32f10x.h"

#define BEEP_GPIO_PORT GPIOB
#define BEEP_GPIO_CLK RCC_APB2Periph_GPIOB
#define BEEP_GPIO_PIN GPIO_Pin_9

#define RELAY1_GPIO_PORT GPIOB
#define RELAY1_GPIO_CLK RCC_APB2Periph_GPIOB
#define RELAY1_GPIO_PIN GPIO_Pin_8

#define RELAY2_GPIO_PORT GPIOB
#define RELAY2_GPIO_CLK RCC_APB2Periph_GPIOB
#define RELAY2_GPIO_PIN GPIO_Pin_6

// �������˿ڶ���
#define BeepOnt PBout(9) // BEEP,�������ӿ�
#define Beepin PBin(9)   // BEEP,�������ӿ�



void RELAY_GPIO_Config(void);
void Beep_time(u16 time);

#endif /* __RELAY_H */
