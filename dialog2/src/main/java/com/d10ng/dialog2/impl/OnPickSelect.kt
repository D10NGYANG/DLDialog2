package com.d10ng.dialog2.impl

import com.d10ng.dialog2.dialog.BaseDialogFragment

/**
 * 单选选择
 * @Author: D10NG
 * @Time: 2021/5/18 2:58 PM
 */
interface OnPickSelect<T: BaseDialogFragment> {

    fun select(dialog: T, key: String, position: Int, select: String)
}

class OnPickSelectListener<T: BaseDialogFragment>: OnPickSelect<T> {

    private lateinit var listener: (dialog: T, key: String, position: Int, select: String) -> Unit

    fun onSelect(listener: (dialog: T, key: String, position: Int, select: String) -> Unit) {
        this.listener = listener
    }

    override fun select(dialog: T, key: String, position: Int, select: String) {
        this.listener.invoke(dialog, key, position, select)
    }
}