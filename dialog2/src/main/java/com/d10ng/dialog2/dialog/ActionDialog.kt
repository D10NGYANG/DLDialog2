package com.d10ng.dialog2.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.DataBindingUtil
import com.d10ng.dialog2.R
import com.d10ng.dialog2.databinding.DialogActionBinding
import com.d10ng.dialog2.databinding.ViewActionTextBinding
import com.d10ng.dialog2.impl.OnButtonClickListener

/**
 * 底部任务弹窗
 * @Author: D10NG
 * @Time: 2021/5/19 9:28 AM
 */
class ActionDialog constructor(
    act: AppCompatActivity,
    tag: String? = null
) : BaseBottomDialogFragment(act, tag) {

    companion object{
        const val CLICK_CANCEL = -1
    }

    private lateinit var binding: DialogActionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_action, container, false)
        binding.isHasCancel = false
        return binding.root
    }

    /** 控件列表 */
    private val bindingList: MutableList<ViewActionTextBinding> = mutableListOf()

    /**
     * 获取自定义位置父控件
     * @return LinearLayoutCompat
     */
    fun getCustomView(): LinearLayoutCompat {
        return binding.llView
    }

    /**
     * 设置取消按钮
     * @param text String
     * @param onButtonClick [@kotlin.ExtensionFunctionType] Function1<OnButtonClickListener<ActionDialog>, Unit>?
     * @return ActionDialog
     */
    fun setCancel(
        text: String = "取消",
        onButtonClick: (OnButtonClickListener<ActionDialog>.() -> Unit)? = null
    ): ActionDialog {
        binding.cancelText = text
        binding.isHasCancel = true
        binding.btnCancel.setOnClickListener {
            if (onButtonClick == null) {
                dismiss()
            } else {
                val clickBuilder = OnButtonClickListener<ActionDialog>()
                clickBuilder.onButtonClick()
                clickBuilder.click(this, CLICK_CANCEL)
            }
        }
        return this
    }

    /**
     * 添加按钮
     * @param text String
     * @param onButtonClick [@kotlin.ExtensionFunctionType] Function1<OnButtonClickListener<ActionDialog>, Unit>?
     * @return ActionDialog
     */
    fun addAction(
        text: String,
        onButtonClick: (OnButtonClickListener<ActionDialog>.() -> Unit)? = null
    ): ActionDialog {
        val viewBinding: ViewActionTextBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_action_text, null, false
        )
        viewBinding.apply {
            actionText = text
            btnAction.setOnClickListener {
                if (onButtonClick == null) {
                    dismiss()
                } else {
                    val clickBuilder = OnButtonClickListener<ActionDialog>()
                    clickBuilder.onButtonClick()
                    clickBuilder.click(this@ActionDialog, bindingList.indexOf(this))
                }
            }
        }
        getCustomView().addView(viewBinding.root)
        bindingList.add(viewBinding)
        return this
    }

    /**
     * 添加动作列表
     * @param list List<String>
     * @param onButtonClick [@kotlin.ExtensionFunctionType] Function1<OnButtonClickListener<ActionDialog>, Unit>?
     * @return ActionDialog
     */
    fun addActionList(
        list: List<String>,
        onButtonClick: (OnButtonClickListener<ActionDialog>.() -> Unit)? = null
    ): ActionDialog {
        for (text in list) {
            addAction(text, onButtonClick)
        }
        return this
    }
}