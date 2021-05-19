package com.d10ng.dialog2.impl

import com.d10ng.dialog2.dialog.BaseDialogFragment

/**
 * 弹窗按钮点击接口
 * @Author: D10NG
 * @Time: 2021/5/17 2:35 PM
 */
interface OnButtonClick<T : BaseDialogFragment> {
    fun click(dialog: T, action: Int)
}

class OnButtonClickListener<T : BaseDialogFragment>: OnButtonClick<T> {

    private lateinit var onClickVal: (dialog: T, action: Int) -> Unit

    fun onClick(listener: (dialog: T, action: Int) -> Unit) {
        this.onClickVal = listener
    }

    override fun click(dialog: T, action: Int) {
        this.onClickVal.invoke(dialog, action)
    }
}