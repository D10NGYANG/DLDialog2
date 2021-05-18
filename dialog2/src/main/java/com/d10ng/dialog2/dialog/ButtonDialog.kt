package com.d10ng.dialog2.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.d10ng.dialog2.R
import com.d10ng.dialog2.databinding.DialogButtonBinding
import com.d10ng.dialog2.databinding.ViewActionButtonBinding
import com.d10ng.dialog2.databinding.ViewContentBinding
import com.d10ng.dialog2.databinding.ViewTitleBinding

/**
 * 普通按钮弹窗
 * @Author: D10NG
 * @Time: 2021/5/17 11:23 AM
 */
open class ButtonDialog constructor(
    act: AppCompatActivity,
    tag: String? = null
): BaseCenterDialogFragment(act, tag) {

    private lateinit var binding: DialogButtonBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_button, container, false)
        return binding.root
    }

    override fun getTitleView(): ViewTitleBinding {
        return binding.viewTitle
    }

    override fun getContentView(): ViewContentBinding {
        return binding.viewContent
    }

    override fun getActionButtonView(): ViewActionButtonBinding {
        return binding.viewActionButton
    }

    /**
     * 获取自定义位置父控件
     * @return LinearLayout
     */
    fun getCustomView(): LinearLayout {
        return binding.llView
    }
}