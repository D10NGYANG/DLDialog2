package com.d10ng.dialog2.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.d10ng.dialog2.R
import com.d10ng.dialog2.databinding.DialogJustLoadingBinding

/**
 * 仅仅是显示加载中的转圈圈弹窗
 * @Author: D10NG
 * @Time: 2021/5/18 1:56 PM
 */
open class JustLoadingDialog constructor(
    act: AppCompatActivity,
    tag: String? = null
): BaseCenterDialogFragment(act, tag) {

    private lateinit var binding: DialogJustLoadingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_just_loading, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            val params = attributes?.apply {
                // 修改背景阴影
                dimAmount = 0.5f
            }
            attributes = params
        }
        // 不能手动取消
        setCanceledOnTouchOutside(false)
    }
}