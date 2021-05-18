package com.d10ng.dialog2.impl

import com.d10ng.dialog2.dialog.BaseDialogFragment

/**
 * 弹窗按钮点击接口
 * @Author: D10NG
 * @Time: 2021/5/17 2:35 PM
 */
interface OnButtonClick<T : BaseDialogFragment> {
    fun click(dialog: T, button: Int)
}

class OnButtonClickListener<T : BaseDialogFragment>: OnButtonClick<T> {

    private lateinit var onClickVal: (dialog: T, button: Int) -> Unit

    fun onClick(listener: (dialog: T, button: Int) -> Unit) {
        this.onClickVal = listener
    }

    override fun click(dialog: T, button: Int) {
        this.onClickVal.invoke(dialog, button)
    }
}