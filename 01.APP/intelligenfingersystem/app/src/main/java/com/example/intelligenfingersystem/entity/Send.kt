package com.example.intelligenfingersystem.entity

data class Send(
    var cmd: Int,
    var en_face: Int? = null,//录入指令，如果录入成功要求设备返回{"fid":"x"}
    var en_rfid: Int? = null,//录入指令，如果录入成功要求设备返回{"frfid":"x"}
    var door: Int? = null,//开门指令，并且下发一遍开发倒计时
    var door_time: Int? = null,
    var did: Int? = null,
    var heart: Int? = null
)
