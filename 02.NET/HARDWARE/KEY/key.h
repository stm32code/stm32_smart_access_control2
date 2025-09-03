#ifndef __KEY_H
#define __KEY_H

#include "stm32f10x.h"

//  ���Ŷ���
#define KEY1_GPIO_CLK RCC_APB2Periph_GPIOB
#define KEY1_GPIO_PORT GPIOB
#define KEY1_GPIO_PIN GPIO_Pin_3

#define KEY2_GPIO_CLK RCC_APB2Periph_GPIOB
#define KEY2_GPIO_PORT GPIOB
#define KEY2_GPIO_PIN GPIO_Pin_4

#define KEY3_GPIO_CLK RCC_APB2Periph_GPIOB
#define KEY3_GPIO_PORT GPIOB
#define KEY3_GPIO_PIN GPIO_Pin_5

#define KEY1 GPIO_ReadInputDataBit(KEY1_GPIO_PORT, KEY1_GPIO_PIN) // ��ȡ����0
#define KEY2 GPIO_ReadInputDataBit(KEY2_GPIO_PORT, KEY2_GPIO_PIN) // ��ȡ����1
#define KEY3 GPIO_ReadInputDataBit(KEY3_GPIO_PORT, KEY3_GPIO_PIN) // ��ȡ����2

#define KEY1_PRES 1 // KEY1����
#define KEY2_PRES 2 // KEY2����
#define KEY3_PRES 3 // KEY3����

#define Key_Scan_Time 30 // �̰�ʱ��ʱ���ж�


/** �������±��ú�
 *  ��������Ϊ�ߵ�ƽ������ KEY_ON=1�� KEY_OFF=0
 *  ����������Ϊ�͵�ƽ���Ѻ����ó�KEY_ON=0 ��KEY_OFF=1 ����
 */
#define KEY_ON 0
#define KEY_OFF 1
/*********************************************************************************
 * @Function	:  ��ʼ������LED��IO
 **********************************************************************************/
void Key_GPIO_Config(void);
/*********************************************************************************
 * @Function	:  ����Ƿ��а�������
 **********************************************************************************/
uint8_t Key_Scan(GPIO_TypeDef *GPIOx, uint16_t GPIO_Pin);
/*********************************************************************************
 * @Function	:  ����������
 **********************************************************************************/
u8 KEY_Scan(u8 mode);


#endif /* __KEY_H */
