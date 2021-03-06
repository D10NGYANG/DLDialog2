package com.d10ng.dialog2.dialog

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.d10ng.dialog2.R
import com.d10ng.dialog2.databinding.ViewActionButtonBinding
import com.d10ng.dialog2.databinding.ViewContentBinding
import com.d10ng.dialog2.databinding.ViewTitleBinding
import com.d10ng.dialog2.impl.OnButtonClickListener
import kotlinx.coroutines.*

/**
 * 基础弹窗
 * @Author: D10NG
 * @Time: 2021/5/17 2:26 PM
 */
open class BaseDialogFragment constructor(
    act: AppCompatActivity,
    tag: String? = null
) : DialogFragment() {

    init {
        show(act, tag)
    }

    override fun onStart() {
        super.onStart()
        // 初始化，没有按钮，只有添加以后才出现
        getActionButtonView()?.isHasButton0 = false
        getActionButtonView()?.isHasButton1 = false
        // 没有文本内容，只有设置后才出现
        getContentView()?.isHasContent = false
    }

    /**
     * 获取标题栏控件
     * @return ViewTitleBinding?
     */
    open fun getTitleView(): ViewTitleBinding? = null

    /**
     * 获取文本内容控件
     * @return ViewContentBinding?
     */
    open fun getContentView(): ViewContentBinding? = null

    /**
     * 获取操作按钮控件
     * @return ViewActionButtonBinding?
     */
    open fun getActionButtonView(): ViewActionButtonBinding? = null
}

/**
 * 等待 Fragment 完成 ViewCreated
 * @receiver T
 * @param listener Function0<Unit>
 */
fun <T : BaseDialogFragment> T.delay2Created(listener: () -> Unit) {
    if (view != null) {
        listener.invoke()
        return
    }
    val start = System.currentTimeMillis()
    GlobalScope.launch {
        while (view == null) {
            delay(10)
            if (System.currentTimeMillis() - start > 1000) {
                println("BaseDialogFragment view 持续为 NULL，无法继续使用，操作任务已忽略。")
                return@launch
            }
        }
        withContext(Dispatchers.Main) {
            listener.invoke()
        }
    }
}

/**
 * 设置标题
 * @receiver T
 * @param title String
 * @param textAlignment Int
 * @return T
 */
fun <T : BaseDialogFragment> T.setTitle(
    title: String,
    textAlignment: Int = View.TEXT_ALIGNMENT_TEXT_START
): T {
    delay2Created {
        getTitleView()?.titleText = title
        getTitleView()?.txtTitle?.textAlignment = textAlignment
    }
    return this
}

/**
 * 设置标题
 * @receiver T
 * @param titleId Int
 * @param textAlignment Int
 * @return T
 */
fun <T : BaseDialogFragment> T.setTitle(
    titleId: Int,
    textAlignment: Int = View.TEXT_ALIGNMENT_TEXT_START
): T {
    delay2Created {
        setTitle(resources.getString(titleId), textAlignment)
    }
    return this
}

/**
 * 设置内容
 * @receiver T
 * @param content String
 * @param textAlignment Int
 * @return T
 */
fun <T : BaseDialogFragment> T.setContent(
    content: String,
    textAlignment: Int = View.TEXT_ALIGNMENT_TEXT_START
): T {
    delay2Created {
        getContentView()?.isHasContent = true
        getContentView()?.contentText = content
        getContentView()?.txtContent?.textAlignment = textAlignment
    }
    return this
}

/**
 * 设置内容
 * @receiver T
 * @param contentId Int
 * @return T
 */
fun <T : BaseDialogFragment> T.setContent(
    contentId: Int,
    textAlignment: Int = View.TEXT_ALIGNMENT_TEXT_START
): T {
    delay2Created {
        setContent(resources.getString(contentId), textAlignment)
    }
    return this
}

/**
 * 移除内容
 * @receiver T
 * @return T
 */
fun <T : BaseDialogFragment> T.removeContent(): T {
    delay2Created {
        getContentView()?.isHasContent = false
    }
    return this
}

/**
 * 设置取消按钮
 * @receiver T
 * @param text String
 * @param onButtonClick [@kotlin.ExtensionFunctionType] Function1<OnButtonClickListener<T>, Unit>?
 * @return T
 */
fun <T : BaseDialogFragment> T.setButton0(
    text: String,
    onButtonClick: (OnButtonClickListener<T>.() -> Unit)? = null
): T {
    delay2Created {
        getActionButtonView()?.apply {
            isHasButton0 = true
            button0Text = text
            btn0.setOnClickListener {
                if (onButtonClick == null) {
                    dismiss()
                } else {
                    val clickBuilder = OnButtonClickListener<T>()
                    clickBuilder.onButtonClick()
                    clickBuilder.click(this@setButton0, 0)
                }
            }
        }
    }
    return this
}

/**
 * 设置取消按钮
 * @receiver T
 * @param textId Int
 * @param onButtonClick [@kotlin.ExtensionFunctionType] Function1<OnButtonClickListener<T>, Unit>?
 * @return T
 */
fun <T : BaseDialogFragment> T.setButton0(
    textId: Int,
    onButtonClick: (OnButtonClickListener<T>.() -> Unit)? = null
): T {
    delay2Created {
        setButton0(resources.getString(textId), onButtonClick)
    }
    return this
}

/**
 * 隐藏取消按钮
 * @receiver T
 * @return T
 */
fun <T : BaseDialogFragment> T.removeButton0(): T {
    delay2Created {
        getActionButtonView()?.apply {
            isHasButton0 = false
        }
    }
    return this
}

/**
 * 设置确定按钮
 * @receiver T
 * @param text String
 * @param onButtonClick [@kotlin.ExtensionFunctionType] Function1<OnButtonClickListener<T>, Unit>?
 * @return T
 */
fun <T : BaseDialogFragment> T.setButton1(
    text: String,
    onButtonClick: (OnButtonClickListener<T>.() -> Unit)? = null
): T {
    delay2Created {
        getActionButtonView()?.apply {
            isHasButton1 = true
            button1Text = text
            btn1.setOnClickListener {
                if (onButtonClick == null) {
                    dismiss()
                } else {
                    val clickBuilder = OnButtonClickListener<T>()
                    clickBuilder.onButtonClick()
                    clickBuilder.click(this@setButton1, 0)
                }
            }
        }
    }
    return this
}

/**
 * 设置确定按钮
 * @receiver T
 * @param textId Int
 * @param onButtonClick [@kotlin.ExtensionFunctionType] Function1<OnButtonClickListener<T>, Unit>?
 * @return T
 */
fun <T : BaseDialogFragment> T.setButton1(
    textId: Int,
    onButtonClick: (OnButtonClickListener<T>.() -> Unit)? = null
): T {
    delay2Created {
        setButton1(resources.getString(textId), onButtonClick)
    }
    return this
}

/**
 * 隐藏确定按钮
 * @receiver T
 * @return T
 */
fun <T : BaseDialogFragment> T.removeButton1(): T {
    delay2Created {
        getActionButtonView()?.apply {
            isHasButton1 = false
        }
    }
    return this
}

/**
 * 设置点击其他地方能否消失弹窗
 * @receiver T
 * @param value Boolean
 * @return T
 */
fun <T : BaseDialogFragment> T.setCanceledOnTouchOutside(value: Boolean): T {
    isCancelable = value
    return this
}

/**
 * 显示弹窗
 * @receiver T
 * @param act AppCompatActivity
 * @param tag String?
 * @return T
 */
private fun <T : BaseDialogFragment> T.show(act: AppCompatActivity, tag: String? = null): T {
    // 设置样式，去掉弹窗本身自带的背景
    setStyle(DialogFragment.STYLE_NORMAL, R.style.D10ng_Dialog2_Style_Normal)
    showNow(act.supportFragmentManager, tag ?: System.currentTimeMillis().toString(16))
    return this
}