package com.d10ng.dialog2.dialog

import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.d10ng.dialog2.R
import com.d10ng.dialog2.databinding.ViewRollerPickerBinding
import com.d10ng.dialog2.impl.HasKeyInterface
import com.d10ng.dialog2.impl.OnPickSelectListener

/**
 * 滚轮选择器
 * @Author: D10NG
 * @Time: 2021/5/18 2:50 PM
 */
open class RollerPickerDialog constructor(
    act: AppCompatActivity,
    tag: String? = null
) : ButtonDialog(act, tag), HasKeyInterface {

    override fun onStart() {
        super.onStart()
        // 改变内容排列方向
        getCustomView().orientation = LinearLayout.HORIZONTAL
    }

    /** 选择项列表 */
    private val bindingMap: MutableMap<String, ViewRollerPickerBinding> = mutableMapOf()

    override fun isHasKey(key: String): Boolean {
        return bindingMap.containsKey(key)
    }

    /**
     * 添加选择器
     * @param key String
     * @param select String
     * @param list List<String>
     * @param start String
     * @param end String
     * @param listener [@kotlin.ExtensionFunctionType] Function1<OnPickSelectListener<RollerPickerDialog>, Unit>?
     * @return RollerPickerDialog
     */
    fun addPick(
        key: String,
        select: String,
        list: List<String>,
        start: String = "",
        end: String = "",
        listener: (OnPickSelectListener<RollerPickerDialog>.() -> Unit)? = null
    ): RollerPickerDialog {
        val viewBinding: ViewRollerPickerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_roller_picker, null, false
        )
        viewBinding.apply {
            // 设置前后文本
            startText = start
            endText = end
            picker.apply {
                // 设置选项
                displayedValues = list.toTypedArray()
                minValue = 0
                maxValue = list.size -1
                // 设置选中
                value = displayedValues.indexOf(select)
            }
        }
        // 选择监听
        viewBinding.picker.setOnValueChangedListener { picker, _, newVal ->
            if (listener != null) {
                val selectItemListener = OnPickSelectListener<RollerPickerDialog>()
                selectItemListener.listener()
                selectItemListener.select(this, key, newVal, picker.displayedValues[newVal])
            }
        }
        getCustomView().removeView(bindingMap[key]?.root)
        getCustomView().addView(viewBinding.root)
        bindingMap[key] = viewBinding
        return this
    }

    /**
     * 移除选择器
     * @param key String
     * @return RollerPickerDialog
     */
    fun removePick(key: String): RollerPickerDialog {
        getCustomView().removeView(bindingMap[key]?.root)
        bindingMap.remove(key)
        return this
    }

    /**
     * 读取选择器的选择
     * @param key String
     * @return String
     */
    fun getPickText(key: String): String {
        val viewBinding = bindingMap[key]?: return ""
        return viewBinding.picker.displayedValues[viewBinding.picker.value]
    }
}