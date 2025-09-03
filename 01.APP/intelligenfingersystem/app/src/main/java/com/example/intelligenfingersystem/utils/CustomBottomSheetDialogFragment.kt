package com.example.intelligenfingersystem.utils

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.intelligenfingersystem.adapter.UserListViewAdapter
import com.example.intelligenfingersystem.databinding.BottomSheetDialogFrgmentLayoutBinding
import com.example.intelligenfingersystem.db.UserDao
import com.example.intelligenfingersystem.adapter.HistoryListViewAdapter
import com.example.intelligenfingersystem.db.HistoryDao
import com.example.intelligenfingersystem.entity.History
import com.example.intelligenfingersystem.entity.Receive
import com.example.intelligenfingersystem.entity.User
import com.example.intelligenfingersystem.utils.Common.registryFlag

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class CustomBottomSheetDialogFragment(private val type: Int) : BottomSheetDialogFragment(),
    HandlerAction {
    private lateinit var binding: BottomSheetDialogFrgmentLayoutBinding
    private lateinit var sharedPreferences: SharedPreferences // 临时存储
    private lateinit var editor: SharedPreferences.Editor // 修改提交
    private lateinit var hdao: HistoryDao
    private var time = "1"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        registryFlag = false
        EventBus.getDefault().register(this)
        sharedPreferences =
            requireContext().getSharedPreferences("local", AppCompatActivity.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        // 填充底部弹窗的布局文件
        binding = BottomSheetDialogFrgmentLayoutBinding.inflate(
            inflater, container, false
        )
        when (type) {
            0 -> {
                val dao = HistoryDao(requireContext())
                val list: MutableList<Any>? = dao.query()
                if (list != null) {
                    if (list.size > 0) {
                        binding.settingList.adapter = HistoryListViewAdapter(requireContext(), list)
                    } else {
                        MToast.mToast(requireContext(), "还没有数据")
                    }
                }
            }

            else -> {
                val dao = UserDao(requireContext())
                val list = dao.query()
                if (list != null) {
                    list.removeAt(0)
                    if (list.size > 0) {
                        binding.settingList.adapter = UserListViewAdapter(requireContext(), list)
                    } else {
                        MToast.mToast(requireContext(), "还没有数据")
                    }
                } else {
                    MToast.mToast(requireContext(), "还没有11数据")
                }
            }
        }

        return binding.root
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
            val list = UserDao(requireContext()).query()!!
            if (data.face_id != null && data.face_id != "0") {
                for (d in list) {
                    val da = d as User
                    if (da.fid.toString() == data.face_id) {
                        Common.sendMessage(requireContext(), 3, "1", time)
                        hdao.insert(History(user = da.uname))
                        break
                    }
                }
            }
            if (data.rfid != null && data.rfid != "0") {
                for (d in list) {
                    val da = d as User
                    if (da.rid.toString() == data.rfid) {
                        Common.sendMessage(requireContext(), 3, "1", time)
                        hdao.insert(History(user = da.uname))
                        break
                    }
                }
            }
            if (data.pwd != null) {
                for (d in list) {
                    val da = d as User
                    if (da.pwd.toString() == data.pwd) {
                        Common.sendMessage(requireContext(), 3, "1", time)
                        hdao.insert(History(user = da.uname))
                        break
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("数据解析", e.message.toString())
            MToast.mToast(requireContext(), "数据解析失败")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
