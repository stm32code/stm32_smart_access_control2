package com.example.intelligenfingersystem

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.example.intelligenfingersystem.databinding.ActivityRegisterBinding
import com.example.intelligenfingersystem.db.HistoryDao
import com.example.intelligenfingersystem.db.UserDao
import com.example.intelligenfingersystem.entity.History
import com.example.intelligenfingersystem.entity.Receive
import com.example.intelligenfingersystem.entity.User
import com.example.intelligenfingersystem.utils.Common
import com.example.intelligenfingersystem.utils.Common.registryFlag
import com.example.intelligenfingersystem.utils.MToast
import com.gyf.immersionbar.ImmersionBar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.Objects

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var isReceive = false
    private lateinit var dao: UserDao
    private lateinit var hdao: HistoryDao
    private lateinit var sharedPreferences: SharedPreferences // 临时存储
    private lateinit var editor: SharedPreferences.Editor // 修改提交
    private var time = "1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("local", MODE_PRIVATE)
        editor = sharedPreferences.edit()
        dao = UserDao(this)
        initViews()
        EventBus.getDefault().register(this)
    }

    private fun initViews() {
        registryFlag = false
        setSupportActionBar(binding.toolbar)
        binding.toolbarLayout.title = "注册用户"
        ImmersionBar.with(this).init()
        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true) //添加默认的返回图标
        supportActionBar!!.setHomeButtonEnabled(true) //设置返回键可用
        val ot = sharedPreferences.getString("openTime", null)
        if (ot != null) {
            time = ot
        }
        binding.registerBtn.setOnClickListener { verifyData() }
    }

    /**
     * 解析数据
     * @param data
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveDataFormat(data: Receive) {
        try {
            if (data.fid != null && data.fid != "0" || data.frfid != null && data.frfid != "0" || data.did != null) {
                registryFlag = true
                Common.receive = data
                return
            }


            Common.receive = null
            val list = dao.query()!!
            if (data.face_id != null && data.face_id != "0") {
                for (d in list) {
                    val da = d as User
                    if (da.fid.toString() == data.face_id) {
                        Common.sendMessage(this, 3, "1")
                        hdao.insert(History(user = da.uname))
                        break
                    }
                }
            }
            if (data.rfid != null && data.rfid != "0") {
                for (d in list) {
                    val da = d as User
                    if (da.rid.toString() == data.rfid) {
                        Common.sendMessage(this, 3, "1")
                        hdao.insert(History(user = da.uname))
                        break
                    }
                }
            }
            if (data.pwd != null) {
                for (d in list) {
                    val da = d as User
                    if (da.pwd.toString() == data.pwd) {
                        Common.sendMessage(this, 3, "1")
                        hdao.insert(History(user = da.uname))
                        break
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("数据解析", e.message.toString())
            MToast.mToast(this, "数据解析失败")
        }
    }


    /***
     * 数据验证
     */
    private fun verifyData() {

        val name = binding.inputNameEdit.text.toString()
        val password = binding.inputPasswordEdit.text.toString()
//        Log.e(
//            "ceshi",
//            "item:${binding.inputRole.selectedItem} \n " +
//                    "itemID:${binding.inputRole.selectedItemId} \n" +
//                    "itemPosition:${binding.inputRole.selectedItemPosition}"
//        )
//        val phone: String = binding.inputRole.selectedItemId
        if (name.isEmpty()) {
            MToast.mToast(this, "用户名不能为空")
            return
        }
        if (password.isEmpty()) {
            MToast.mToast(this, "密码不能为空")
            return
        }

        val objects: List<Any>? = dao.query(name, "name")
        if (objects!!.isNotEmpty()) {
            MToast.mToast(this, "已有该用户，请直接登录")
            return
        }
        val user = User(
            uname = name,
            upassword = password
        )

        dao.insert(user)
        MToast.mToast(this, "添加成功")

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}