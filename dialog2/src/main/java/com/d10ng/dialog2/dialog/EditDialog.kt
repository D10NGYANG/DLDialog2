package com.d10ng.dialog2.dialog

import android.text.InputFilter
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.d10ng.dialog2.R
import com.d10ng.dialog2.databinding.ViewEditTextBinding
import com.d10ng.dialog2.impl.HasKeyInterface

/**
 * 带输入框弹窗
 * @Author: D10NG
 * @Time: 2021/5/17 5:33 PM
 */
open class EditDialog constructor(
    act: AppCompatActivity,
    tag: String? = null
): ButtonDialog(act, tag), HasKeyInterface {

    /** 编辑框列表 */
    private val bindingMap: MutableMap<String, ViewEditTextBinding> = mutableMapOf()

    override fun isHasKey(key: String): Boolean {
        return bindingMap.containsKey(key)
    }

    /**
     * 添加一个编辑框
     * @param key String
     * @param text String
     * @param hint String
     * @return EditDialog
     */
    fun addEdit(key: String, text: String, hint: String = "请输入") : EditDialog {
        val viewBinding: ViewEditTextBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_edit_text, null, false
        )
        viewBinding.apply {
            edt.setText(text)
            hintText = hint
            isError = false
            errorText = ""
        }
        getCustomView().addView(viewBinding.root)
        bindingMap[key] = viewBinding
        return this
    }

    /**
     * 移除编辑框
     * @param key 标签
     */
    fun removeEdit(key: String) : EditDialog {
        if (bindingMap[key] != null) {
            getCustomView().removeView(bindingMap[key]?.root)
            bindingMap.remove(key)
        }
        return this
    }

    /**
     * 设置输入类型
     * @param key 标签
     * @param type 类型
     */
    fun setInputType(key: String, type: Int) : EditDialog {
        bindingMap[key]?.edt?.inputType = type
        return this
    }

    /**
     * 设置最大输入长度
     * @param key String
     * @param length Int
     * @return EditDialog
     */
    fun setInputMaxLength(key: String, length: Int): EditDialog {
        bindingMap[key]?.edt?.filters = arrayOf(InputFilter.LengthFilter(length))
        return this
    }

    /**
     * 获取输入文本
     * @param key 标签
     */
    fun getInputText(key: String) : String {
        return bindingMap[key]?.edt?.text.toString().trim()
    }

    /**
     * 显示错误信息
     * @param key 标签
     * @param value 信息
     */
    fun setError(key: String, value: String) : EditDialog {
        bindingMap[key]?.isError = true
        bindingMap[key]?.errorText = value
        return this
    }

    /**
     * 取消错误显示
     * @param key String
     * @return EditDialog
     */
    fun clearError(key: String): EditDialog {
        bindingMap[key]?.isError = false
        bindingMap[key]?.errorText = ""
        return this
    }
}