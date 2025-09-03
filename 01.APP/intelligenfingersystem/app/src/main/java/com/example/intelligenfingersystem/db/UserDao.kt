package com.example.intelligenfingersystem.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.intelligenfingersystem.entity.User
import com.example.intelligenfingersystem.utils.MToast
import com.example.intelligenfingersystem.utils.TimeCycle
import com.example.intelligenfingersystem.utils.Common

class UserDao(private val context: Context) : BaseDao {
    private var helper = DBOpenHelper(context)
    private lateinit var db: SQLiteDatabase
    private val TAG = "UserDao"

    override fun insert(bean: Any): Int {
        return try {
            val user: User = bean as User
            if (user.uname == "admin" || query(user.uname!!)!!.size != 0) return 0
            if (query(user.pwd.toString(), "pwd")?.size != 0) user.pwd =
                Common.randomCipher().toInt()
            db = helper.writableDatabase
            val values = ContentValues()
            values.put("uname", user.uname)
            values.put("upassword", user.upassword)
            values.put("fid", -1)
            values.put("createDateTime", TimeCycle.getDateTime())
            db.insert("user", null, values)
            db.close()
            1
        } catch (e: Exception) {
            e.printStackTrace()
            MToast.mToast(context, "添加失败")
            -1
        }
    }

    override fun update(bean: Any, vararg data: String): Int {
        return try {
            val user = bean as User
//            if (query(user.fid.toString(), "fid")?.size != 0) return -1
//            if (query(user.rid.toString(), "rid")?.size != 0) return -1
            db = helper.writableDatabase
            val values = ContentValues()
            values.put("uname", user.uname)
            values.put("upassword", user.upassword)
            values.put("fid", user.fid)
            values.put("rid", user.rid)
            values.put("pwd", user.pwd)
            values.put("createDateTime", user.createDateTime)
            db.update("user", values, "uid=?", arrayOf(data[0]))
            db.close()
            1
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            MToast.mToast(context, "修改失败")
            -1
        }
    }

    override fun query(vararg data: String): MutableList<Any>? {
        return try {
            val result: MutableList<Any> = ArrayList()
            val cursor: Cursor
            val sql: String
            db = helper.readableDatabase
            when (data.size) {
                1 -> {
                    sql = "SELECT * from user where uid = ?"
                    cursor = db.rawQuery(sql, arrayOf(data[0]))
                }

                2 -> if (data[1] == "fid") {
                    sql = "SELECT * FROM user where fid = ?"
                    cursor = db.rawQuery(sql, arrayOf(data[0]))
                } else if (data[1] == "rid") {
                    sql = "SELECT * FROM user where rid = ?"
                    cursor = db.rawQuery(sql, arrayOf(data[0]))
                } else if (data[1] == "pwd") {
                    sql = "SELECT * FROM user where pwd = ?"
                    cursor = db.rawQuery(sql, arrayOf(data[0]))
                } else {
                    sql = "SELECT * FROM user where uname = ? and upassword =?"
                    cursor = db.rawQuery(sql, data)
                }

                else -> {
                    sql = "select * from user"
                    cursor = db.rawQuery(sql, null)
                }
            }
            while (cursor.moveToNext()) {
                val user = User(
                    cursor.getInt(cursor.getColumnIndexOrThrow("uid")),
                    cursor.getString(cursor.getColumnIndexOrThrow("uname")),
                    cursor.getString(cursor.getColumnIndexOrThrow("upassword")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("per")),
                    cursor.getString(cursor.getColumnIndexOrThrow("createDateTime")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("fid")),
                    cursor.getString(cursor.getColumnIndexOrThrow("rid")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("pwd"))
                )
                result.add(user)
            }
            cursor.close()
            db.close()
            result
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.e("查询", e.toString())
            MToast.mToast(context, "查询失败")
            null
        }
    }

    override fun delete(vararg data: String): Int {
        return try {
            if (data.isEmpty()) {
                return 0
            }
            db = helper.writableDatabase
            db.delete("user", "uid=?", arrayOf(data[0]))
            db.close()
            1
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            MToast.mToast(context, "删除失败")
            -1
        }
    }
}