#ifndef __FACE_H
#define __FACE_H

#include "stm32f10x.h"
#include "git.h"

// �����������յ������ݣ�hex��
void massage_face_hex(char *message);
// �������ָ��
void check_face_id(void);
// ע������ָ��
void register_face_id(void);

// ɾ��ָ������ָ��
void delete_face_id(U16 ID);
// ɾ��ȫ������ָ��
void deleteall_face_id(void);

#endif /* __FACE_H */
