package com.d10ng.dldialog2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
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
            val tag = "num"
            EditDialog(this)
                .setTitle("输入框")
                .setContent("请输入您的身份证号码")
                .addEdit(tag, "")
                .setInputMaxLength(tag, 18)
                .setInputType(tag, InputType.TYPE_CLASS_NUMBER)
                .setButton0("取消")
                .setButton1("确定") {
                    onClick { dialog, _ ->
                        val input = dialog.getInputText(tag)
                        when {
                            input.isEmpty() -> {
                                dialog.setError(tag, "身份证不能为空")
                            }
                            input.length != 18 -> {
                                dialog.setError(tag, "身份证格式不正确")
                            }
                            else -> {
                                dialog.clearError(tag)
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
    }
}