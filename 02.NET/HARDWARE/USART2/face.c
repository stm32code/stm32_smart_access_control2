#include "face.h"
#include "usart.h"
#include "usart2.h"
#include "led.h"
#include "relay.h"
#include "delay.h"
//  C��
#include "cjson.h"
#include <string.h>
#include <stdio.h>

extern u8 USART2_RX_BUF[UART2_RXBUF_SIZE]; // ���ջ���,���USART3_MAX_RECV_LEN���ֽ�.
extern U16 Uart2_RecvCount;				   // ���ڽ��ռ���
// �����������յ������ݣ�hex��
void massage_face_hex(char *message)
{
	if (Uart2_RecvCount)
	{
		switch (USART2_RX_BUF[7])
		{
		case 0x12: // ʶ��ɹ�
			if (USART2_RX_BUF[8] == 0x00)
			{
				// ��app������Ϣ
				if(	Data_init.face_id != USART2_RX_BUF[10] + 1){
						Data_init.face_id = USART2_RX_BUF[10] + 1;
						Data_init.face_id_copy = Data_init.face_id;
				
						// ��������
						if(device_state_init.check_open<3){
							Data_init.App=1;
							Beep_time(30);
							
							OLED_Clear();						
							device_state_init.check_open =10;
							device_state_init.open =1;
							Data_init.Page = 6;
							OLED_ShowCH(16, 6, "       K#�ر�");
						}
						
				}
			
			}
			else
			{
				Data_init.face_id = 0;
			}

			break;
		case 0x13: // ע��
			if (USART2_RX_BUF[8] == 0x00)
			{
				Data_init.face_id = USART2_RX_BUF[10] + 1;
				Data_init.register_id = Data_init.face_id;
				//printf("\r\nע��ID:%d", Data_init.face_id);
				// ������������
				BeepOnt = 1;
				delay_ms(100);
				BeepOnt = 0;
				delay_ms(100);
				BeepOnt = 1;
				delay_ms(100);
				BeepOnt = 0;
				Data_init.App = 2;
			}
			else if (USART2_RX_BUF[8] == 0x09)
			{
				Data_init.register_id = 0; // ���id
			}
			else
			{
				Data_init.register_id = 0; // ���id
			}

			break;
		case 0x20: // ɾ��
			if (USART2_RX_BUF[8] == 0x00)
			{

				//printf("\r\nɾ���ɹ�");
				// ������������
				BeepOnt = 1;
				delay_ms(200);
 				BeepOnt = 0;
				Data_init.App = 4;
			}
			else
			{
				Data_init.delete_id = 0; // ���id
			}
			break;
		default:
			break;
		}
		// printf("\r\n");
		// for (i = 0; i < Uart2_RecvCount; i++)
		// {
		// 	printf("%02X ", USART2_RX_BUF[i]);
		// }
	}


}
// 8λ�ĺ�Ч��
U8 SumCheck(U8 *Dat, U8 Len)
{
	U8 Sum = 0;
	U8 i;
	for (i = 0; i < Len; i++)
	{
		Sum += Dat[i];
	}

	return Sum;
}
// �������ָ��
void check_face_id(void)
{
	U8 check_face_cmd[8] = {0xEF, 0xAA, 0x12, 0x00, 0x00, 0x00, 0x00, 00};
	check_face_cmd[7] = SumCheck(check_face_cmd + 2, 6);
	Usart_SendArray(USART_2, (char *)check_face_cmd, 8);
}
// ע������ָ��
void register_face_id(void)
{
	U8 check_face_cmd[8] = {0xEF, 0xAA, 0x13, 0x00, 0x00, 0x00, 0x00, 00};
	check_face_cmd[7] = SumCheck(check_face_cmd + 2, 6);
	Usart_SendArray(USART_2, (char *)check_face_cmd, 8);
}
// ɾ��ָ������ָ��
void delete_face_id(U16 ID)
{
	U8 check_face_cmd[10] = {0xEF, 0xAA, 0x20, 0x00, 0x00, 0x00, 0x02, 0, 0, 0};
	// EF AA 20 00 00 00 02 00 01 23
	// EFAA20000000 02 00 00 22

	if (ID <= 255)
	{
		check_face_cmd[8] = ID;
	}
	check_face_cmd[9] = SumCheck(check_face_cmd + 2, 8);
	Usart_SendArray(USART_2, (char *)check_face_cmd, 10);
}
// ɾ��ָ������ָ��
void deleteall_face_id(void)
{
	U8 check_face_cmd[8] = {0xEF, 0xAA, 0x21, 0x00, 0x00, 0x00, 0x00, 0};
	check_face_cmd[7] = SumCheck(check_face_cmd + 2, 6);
	Usart_SendArray(USART_2, (char *)check_face_cmd, 8);
}
