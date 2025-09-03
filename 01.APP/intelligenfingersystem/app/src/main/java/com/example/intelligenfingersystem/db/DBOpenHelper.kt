package com.example.intelligenfingersystem.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.intelligenfingersystem.utils.TimeCycle

class DBOpenHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        const val DB_NAME = "my.db"
        const val DB_VERSION = 1
    }

    // 创建数据库时触发
    override fun onCreate(p0: SQLiteDatabase?) {
        var sql = "create table `history` (" +
                "`hid` INTEGER primary key autoincrement," +
                "`user` varchar(255),"+
                "`createDateTime` VARCHAR(255))"
        p0?.execSQL(sql) //执行sql语句，
        sql = "create table `user` (" +
                "`uid` INTEGER primary key autoincrement," +
                "`uname` VARCHAR(20)," +
                "`upassword` VARCHAR(20)," +
                "`per` INTEGER," +
                "`pwd` INTEGER," +
                "`rid` VARCHAR(255)," + // rf 的id
                "`fid` INTEGER," + // 人脸id
                "`createDateTime` VARCHAR(255))"
        p0?.execSQL(sql) //执行sql语句，
        var values = ContentValues()
        values.put("uname", "admin")
        values.put("upassword", "123456")
        values.put("per", 1)
        values.put("fid", -1)
        values.put("pwd", -1)
        values.put("createDateTime", TimeCycle.getDateTime())
        p0?.insert("user", null, values)
    }

    // 更新数据库时触发
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}