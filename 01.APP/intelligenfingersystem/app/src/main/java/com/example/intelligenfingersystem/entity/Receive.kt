package com.example.intelligenfingersystem.entity

data class Receive(
    var face_id: String? = null,// 识别到的人脸ID号，无则为零，开门使用
    var rfid: String? = null, // 识别到的ID号，无则为零，开门使用
    var pwd: String? = null,// 输入的密码，无则为零，开门使用
    var door: String? = null,// 开关门指令，1未开，0为关
    var door_time: String? = null,// 设置关门倒计时，0则为门常开（S）
    var fid: String? = null,//录入成功以后，向APP反馈录入的id号 删除同理
    var frfid: String? = null,
    var did: String? = null
)
