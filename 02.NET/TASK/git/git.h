#ifndef __GIT__H
#define __GIT__H

//  �豸ʹ�����趨��
#define OLED 1			// �Ƿ�ʹ��OLED
#define NET_SERVE 1		// ƽ̨ѡ��
#define NETWORK_CHAEK 1 // �Ƿ������߼��
#define KEY_OPEN 1		// �Ƿ��������Ͷ̰����
#define USART2_OPEN 1	// �Ƿ�ʹ�ô��ڶ�

// ���ݶ���
typedef unsigned char U8;
typedef signed char S8;
typedef unsigned short U16;
typedef signed short S16;
typedef unsigned int U32;
typedef signed int S32;
typedef float F32;

//  C��
#include "cjson.h"
#include <string.h>
#include <stdio.h>
// ��Ƭ��ͷ�ļ�
#include "sys.h"
#include "usart.h"
// ����Э���
#include "aly.h"
// �����豸
#include "esp8266.h"
// ������
#include "task.h"
#include "timer.h"

// Ӳ������
#include "delay.h"
#include "usart.h"
#include "usart2.h"
#include "git.h"
#include "led.h"
#include "key.h"
#include "timer.h"
#include "flash.h"
#include "adc.h"
#include "relay.h"
#include "adc.h"
#include "usart2.h"
#include "as608.h"
#include "sg90.h"
#include "TTP229.h"
#include "level.h"
#include "rc522.h"

#if OLED // OLED�ļ�����
#include "oled.h"
#endif

// ��������Ϣ
#define SSID "NET"		// ·����SSID����
#define PASS "12345678" // ·��������
#if NET_SERVE == 0
// ��Э���������Onenet�ɰ�֧�֣�
#define ServerIP "183.230.40.39" // ������IP��ַ
#define ServerPort 6002			 // ������IP��ַ�˿ں�
#elif NET_SERVE == 1
// ����������ƽ̨��������֧�֣�
#define ServerIP "iot-06z00axdhgfk24n.mqtt.iothub.aliyuncs.com" // ������IP��ַ
#define ServerPort 1883											// ������IP��ַ�˿ں�
#elif NET_SERVE == 2
// EMQXƽ̨��������
#define ServerIP "broker.emqx.io" // ������IP��ַ
#define ServerPort 1883			  // ������IP��ַ�˿ں�
#endif
// �豸��Ϣ
#define PROID "smartdevice&h9sjl42RHID"	  // ��ƷID
#define DEVID "h9sjl42RHID.smartdevice|securemode=2,signmethod=hmacsha256,timestamp=1733833708876|" // �豸ID
#define AUTH_INFO "c9bfe9ff04fb4e82e606189ee52460727f1f8adc5c70305fbb355e979767dec3"	  // ��Ȩ��Ϣ

// MQTT���� /broadcast/
#define S_TOPIC_NAME "/broadcast/h9sjl42RHID/test1" // Ӳ�����ĵ�����
#define P_TOPIC_NAME "/broadcast/h9sjl42RHID/test2" // Ӳ������������



// �Զ��岼������
typedef enum
{
	MY_TRUE,
	MY_FALSE
} myBool;

// �Զ���ִ�н������
typedef enum
{
	MY_SUCCESSFUL = 0x01, // �ɹ�
	MY_FAIL = 0x00		  // ʧ��

} mySta; // �ɹ���־λ

typedef enum
{
	OPEN = 0x01, // ��
	CLOSE = 0x00 // �ر�

} On_or_Off_TypeDef; // �ɹ���־λ

typedef enum
{
	DERVICE_SEND = 0x00, // �豸->ƽ̨
	PLATFORM_SEND = 0x01 // ƽ̨->�豸

} Send_directino; // ���ͷ���

typedef struct
{
	U8 App;			 // ָ��ģʽ
	U8 Heart;   // ���APP�Ƿ�����
	U8 Device_State; // ģʽ
	U8 Page;		 // ҳ��
	U8 Error_Time;
	U8 time; // ҳ��
	
	U8 WENDU_H;		 // ���¸�λ
	U8 WENDU_L;		 // ���µ�λ
	
	F32 temperatuer; // �¶�
	F32 humiditr;	 // ʪ��
	U8 Flage;		 // ģʽѡ��

	U16 MQ_135;		 // ��������
	
	U16 face_id;	 // ����id
	U16 face_id_copy;	 // ����id
	U16 register_id; // ע��id
	U16 delete_id;	 // ɾ��id
	U16 register_rfid; // ע��rfid
	
	U16	hand_id; // ָ��id
	U16 hand_id_copy;	 // ����id
	
} Data_TypeDef; // ���ݲ����ṹ��

typedef struct
{

	U16 somg_value;		// ������ֵ
	U16 humi_value;		// ʪ����ֵ
	U16 temp_value;		// �¶���ֵ
	U16 MQ_135_value; //����������ֵ
	U16 Distance_value; // ������ֵ

} Threshold_Value_TypeDef; // ���ݲ����ṹ��

typedef struct
{
	U8 check_device;   // ���¼�
	U8 check_open;	   // ����Ƿ�������
	U8 Key_State;	   // �������
	U8 Fall_State;	   // ˤ�����
	U16 Distance;	   // ����
	U8 location_state; // ��λ���
	
	
	U8 Error_Num; // �Դ����
	U16 DoorPwd;
	U16 door_state;
	U16 GetPwd;	  // ���������
	U16 CheckPwd; // ���������
	U8 Door_Time; // �Դ����
	U8 Error_Time;
	U8 waning;			// ����

	
	U16 open;	// ������
	U16 open_time;	// ������ʱ��

	
	
	U8 box_num;	// �򿪵Ĺ��弸
	U8 BeepOpenTime;

} Device_Satte_Typedef; // ״̬�����ṹ��

// ȫ������
extern Data_TypeDef Data_init;
extern Device_Satte_Typedef device_state_init; // �豸״̬

extern Threshold_Value_TypeDef threshold_value_init; // �豸��ֵ���ýṹ��

// ��ȡ���ݲ���
mySta Read_Data(Data_TypeDef *Device_Data);
// ��ʼ��
mySta Reset_Threshole_Value(Threshold_Value_TypeDef *Value, Device_Satte_Typedef *device_state);
// ����OLED��ʾ��������
mySta Update_oled_massage(void);
// �����豸״̬
mySta Update_device_massage(void);
// ����json����
mySta massage_parse_json(char *message);
// ѡ������ʽ
void Input_Password(void);

void Automation_Close(void);

#endif
