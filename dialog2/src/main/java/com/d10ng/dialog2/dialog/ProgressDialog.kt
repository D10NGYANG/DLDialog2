package com.d10ng.dialog2.dialog

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.d10ng.dialog2.R
import com.d10ng.dialog2.databinding.ViewProgressBinding

/**
 * 进度条弹窗
 *
 * @Author: D10NG
 * @Time: 2021/5/18 4:57 PM
 */
class ProgressDialog constructor(
    act: AppCompatActivity,
    tag: String? = null
): ButtonDialog(act, tag) {

    private val viewBinding: ViewProgressBinding by lazy {
        DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_progress, null, false
        )
    }

    /**
     * 初始化
     * @return ProgressDialog
     */
    fun initView(): ProgressDialog {
        getCustomView().removeAllViews()
        viewBinding.apply {
            isHasProgressText = false
            progressText = "100%"
            progressBar.isIndeterminate = true
        }
        getCustomView().addView(viewBinding.root)
        return this
    }

    /**
     * 是否为无限循环滚动
     * @param value Boolean
     * @return ProgressDialog
     */
    fun setIsIndeterminate(value: Boolean): ProgressDialog {
        viewBinding.progressBar.isIndeterminate = value
        return this
    }

    /**
     * 是否显示进度条文本
     * @param value Boolean
     * @return ProgressDialog
     */
    fun setIsShowProgressText(value: Boolean): ProgressDialog {
        viewBinding.isHasProgressText = value
        return this
    }

    /**
     * 设置进度条最大值
     * @param value Int
     * @return ProgressDialog
     */
    fun setMax(value: Int): ProgressDialog {
        viewBinding.progressBar.max = value
        updateProgressText()
        return this
    }

    /**
     * 设置当前进度
     * @param value Int
     * @return ProgressDialog
     */
    fun setCurrentProgress(value: Int): ProgressDialog {
        viewBinding.progressBar.progress = value
        updateProgressText()
        return this
    }

    /**
     * 获取进度条最大值
     * @return Int
     */
    fun getMax(): Int = viewBinding.progressBar.max

    /**
     * 获取当前进度
     * @return Int
     */
    fun getProgress(): Int = viewBinding.progressBar.progress

    /**
     * 更新进度条文本
     */
    private fun updateProgressText() {
        val pre = viewBinding.progressBar.progress % viewBinding.progressBar.max
        viewBinding.progressText = "${pre}%"
    }
}