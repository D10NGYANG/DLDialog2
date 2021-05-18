package com.d10ng.dialog2.dialog

import android.text.InputFilter
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.d10ng.dialog2.R
import com.d10ng.dialog2.databinding.ViewEditTextBinding

/**
 * 带输入框弹窗
 * @Author: D10NG
 * @Time: 2021/5/17 5:33 PM
 */
class EditDialog constructor(
    act: AppCompatActivity,
    tag: String? = null
): ButtonDialog(act, tag) {

    /** 编辑框列表 */
    private val edtMap: MutableMap<String, ViewEditTextBinding> = mutableMapOf()

    /**
     * 添加一个编辑框
     * @param tag String
     * @param text String
     * @param hint String
     * @return EditDialog
     */
    fun addEdit(tag: String, text: String, hint: String = "请输入") : EditDialog {
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
        edtMap[tag] = viewBinding
        return this
    }

    /**
     * 移除编辑框
     * @param tag 标签
     */
    fun removeEdit(tag: String) : EditDialog {
        if (edtMap[tag] != null) {
            getCustomView().removeView(edtMap[tag]?.root)
            edtMap.remove(tag)
        }
        return this
    }

    /**
     * 设置输入类型
     * @param tag 标签
     * @param type 类型
     */
    fun setInputType(tag: String, type: Int) : EditDialog {
        edtMap[tag]?.edt?.inputType = type
        return this
    }

    /**
     * 设置最大输入长度
     * @param tag String
     * @param length Int
     * @return EditDialog
     */
    fun setInputMaxLength(tag: String, length: Int): EditDialog {
        edtMap[tag]?.edt?.filters = arrayOf(InputFilter.LengthFilter(length))
        return this
    }

    /**
     * 获取输入文本
     * @param tag 标签
     */
    fun getInputText(tag: String) : String {
        return edtMap[tag]?.edt?.text.toString().trim()
    }

    /**
     * 显示错误信息
     * @param tag 标签
     * @param value 信息
     */
    fun setError(tag: String, value: String) : EditDialog {
        edtMap[tag]?.isError = true
        edtMap[tag]?.errorText = value
        return this
    }

    /**
     * 取消错误显示
     * @param tag String
     * @return EditDialog
     */
    fun clearError(tag: String): EditDialog {
        edtMap[tag]?.isError = false
        edtMap[tag]?.errorText = ""
        return this
    }
}