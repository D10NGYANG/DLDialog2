package com.d10ng.dialog2.utils

import android.content.Context

/**
 * 格式转换
 * @author D10NG
 * @date on 2020/8/11 10:01 AM
 */
fun Float.dp2px(context: Context): Float = this * context.resources.displayMetrics.density

fun Float.px2dp(context: Context): Float = this / context.resources.displayMetrics.density

