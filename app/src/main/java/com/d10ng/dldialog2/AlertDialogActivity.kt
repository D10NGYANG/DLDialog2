package com.d10ng.dldialog2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.d10ng.dldialog2.databinding.ActivityAlertDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AlertDialogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlertDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alert_dialog)

        binding.btnNormal.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("标题")
                .setMessage("文本")
                .setNeutralButton("取消") { dialog, which ->
                    dialog.dismiss()
                }
                .setNegativeButton("拒绝") { dialog, which ->
                    dialog.dismiss()
                }
                .setPositiveButton("接受") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }

        binding.btnSingleSelect.setOnClickListener {
            val items = mutableListOf<String>()

            for (i in 0 .. 20) {
                items.add("选项$i")
            }

            MaterialAlertDialogBuilder(this)
                .setTitle("标题")
                .setItems(items.toTypedArray()) { dialog, which ->
                    Toast.makeText(this, "选择 ${items[which]}", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .show()
        }
    }
}