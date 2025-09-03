#include "level.h"
#include "delay.h"

/*********************************************************************************
 * @Function	:  ��ʼ������LED��IO
 * @Input		:  None
 * @Output		:  None
 * @Return		:  None
 * @Others		:  None
 * @Date			:  2022-07-23
 **********************************************************************************/
void Level_GPIO_Config(void)
{
    GPIO_InitTypeDef GPIO_InitStructure;

    /*���������˿ڵ�ʱ��*/
    RCC_APB2PeriphClockCmd(LEVEL1_GPIO_CLK, ENABLE);

    // ѡ�񰴼�������
    GPIO_InitStructure.GPIO_Pin = LEVEL1_GPIO_PIN;
    // ���ð���������Ϊ��������
    GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPU;
    // ʹ�ýṹ���ʼ������
    GPIO_Init(LEVEL1_GPIO_PORT, &GPIO_InitStructure);

    // ѡ�񰴼�������
    GPIO_InitStructure.GPIO_Pin = LEVEL2_GPIO_PIN;
    // ���ð���������Ϊ��������
    GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPU;
    // ʹ�ýṹ���ʼ������
    GPIO_Init(LEVEL2_GPIO_PORT, &GPIO_InitStructure);

    // ѡ�񰴼�������
    GPIO_InitStructure.GPIO_Pin = LEVEL3_GPIO_PIN;
    // ���ð���������Ϊ��������
    GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPU;
    // ʹ�ýṹ���ʼ������
    GPIO_Init(LEVEL3_GPIO_PORT, &GPIO_InitStructure);
}
