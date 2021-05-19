package com.d10ng.dialog2.dialog

import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

/**
 * 底部弹窗样式
 * @Author: D10NG
 * @Time: 2021/5/19 9:08 AM
 */
open class BaseBottomDialogFragment constructor(
    act: AppCompatActivity,
    tag: String? = null
): BaseDialogFragment(act, tag) {

    override fun onStart() {
        super.onStart()
        // 修改弹窗外部样式
        dialog?.window?.apply {
            val params = attributes?.apply {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                gravity = Gravity.BOTTOM
            }
            attributes = params
        }
    }
}