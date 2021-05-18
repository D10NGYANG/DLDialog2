package com.d10ng.dialog2.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import androidx.core.content.ContextCompat
import com.d10ng.dialog2.R
import com.d10ng.dialog2.utils.dp2px
import com.d10ng.dialog2.utils.px2dp

/**
 * 自定义数字选择器
 *
 * @author D10NG
 * @date on 2020/7/2 7:38 PM
 */
class CustomNumberPicker constructor(
    context: Context,
    attrs: AttributeSet? = null
) : NumberPicker(context, attrs) {

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)
        if (child is EditText) {
            child.isFocusable = false
            // 设置文字的颜色
            child.setTextColor(ContextCompat.getColor(context, R.color.colorDialogContentText))
            // 设置文字的大小
            child.textSize = 16f
            // 设置文字不可选择
            child.setTextIsSelectable(false)
        }
    }
}