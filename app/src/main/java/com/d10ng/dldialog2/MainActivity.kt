package com.d10ng.dldialog2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.d10ng.dialog2.dialog.*
import com.d10ng.dldialog2.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnAlertDialog.setOnClickListener {
            startActivity(Intent(this, AlertDialogActivity::class.java))
        }

        binding.btnDialog2Button.setOnClickListener {
            ButtonDialog(this)
                .setTitle("标题")
                .setContent("这是一句内容可以分行，还能多行；\n进行其他样式接入需要自己编写。\n\n-- 内容")
                .setButton0("取消")
                .setButton1("确定") {
                    onClick { dialog, _ ->
                        dialog.dismiss()
                        Toast.makeText(this@MainActivity, "你点击了确定", Toast.LENGTH_SHORT).show()
                    }
                }
                .setCanceledOnTouchOutside(false)
        }

        binding.btnDialog2Edit.setOnClickListener {
            val key = "num"
            EditDialog(this)
                .setTitle("输入框")
                .setContent("请输入您的身份证号码")
                .addEdit(key, "")
                .setInputMaxLength(key, 18)
                .setInputType(key, InputType.TYPE_CLASS_NUMBER)
                .setButton0("取消")
                .setButton1("确定") {
                    onClick { dialog, _ ->
                        val input = dialog.getInputText(key)
                        when {
                            input.isEmpty() -> {
                                dialog.setError(key, "身份证不能为空")
                            }
                            input.length != 18 -> {
                                dialog.setError(key, "身份证格式不正确")
                            }
                            else -> {
                                dialog.clearError(key)
                                dialog.dismiss()
                            }
                        }
                    }
                }
                .setCanceledOnTouchOutside(false)
        }

        binding.btnDialog2JustLoading.setOnClickListener {
            val dialog = JustLoadingDialog(this)
            GlobalScope.launch {
                delay(3000)
                withContext(Dispatchers.Main) {
                    dialog.dismiss()
                }
            }
        }

        binding.btnDialog2RollerPicker.setOnClickListener {
            val key = "roller"
            val list = listOf("张三", "里斯", "编剧", "鼠标", "手机", "吉萨", "沙雕", "戊午")
            RollerPickerDialog(this)
                .setTitle("滚轮选择器", View.TEXT_ALIGNMENT_CENTER)
                .setContent("请选择您的联系人", View.TEXT_ALIGNMENT_CENTER)
                .addPick(key, "", list) {
                    onSelect { _, _, _, select ->
                        Log.e("测试", "你选择了【${select}】")
                    }
                }
                .setButton0("取消")
                .setButton1("确定") {
                    onClick { dialog, _ ->
                        dialog.dismiss()
                        val select = dialog.getPickText(key)
                        Toast.makeText(this@MainActivity, "你点击了确定，选择了【${select}】", Toast.LENGTH_SHORT).show()
                    }
                }
                .setCanceledOnTouchOutside(false)
        }

        binding.btnDialog2DateRollerPicker.setOnClickListener {
            DateRollerPickerDialog(this)
                .setTitle("日期选择器", View.TEXT_ALIGNMENT_CENTER)
                .setContent("请选择您的离校日期", View.TEXT_ALIGNMENT_CENTER)
                .setYearPickList(end = "年")
                .setMonthPickList(end = "月")
                .setDayPickList()
                .setButton0("取消")
                .setButton1("确定") {
                    onClick { dialog, _ ->
                        dialog.dismiss()
                        val year = dialog.getYearPickValue()
                        val month = dialog.getMonthPickValue()
                        val day = dialog.getDayPickValue()
                        Toast.makeText(this@MainActivity, "$year 年 $month 月 $day 日", Toast.LENGTH_SHORT).show()
                    }
                }
                .setCanceledOnTouchOutside(false)
        }
    }
}