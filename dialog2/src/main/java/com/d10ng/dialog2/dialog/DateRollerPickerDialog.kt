package com.d10ng.dialog2.dialog

import androidx.appcompat.app.AppCompatActivity
import com.d10ng.dialog2.impl.OnPickSelectListener
import java.lang.Exception
import java.util.*

/**
 * 年月日滚轮选择器
 * @Author: D10NG
 * @Time: 2021/5/18 3:46 PM
 */
open class DateRollerPickerDialog constructor(
    act: AppCompatActivity,
    tag: String? = null,
    // 开始时间 1976-01-01 00:00:00
    private val startTime: Long = 189273600000L,
    // 结束时间 最新时间
    private val endTime: Long = System.currentTimeMillis(),
    // 选择时间
    private val selectTime: Long = System.currentTimeMillis()
): RollerPickerDialog(act, tag) {

    companion object {
        private const val keyY = "year"
        private const val keyM = "month"
        private const val keyD = "day"
    }

    init {
        if (startTime >= endTime) {
            throw Exception("DatePickerDialog startTime >= endTime")
        } else if (selectTime !in startTime .. endTime) {
            throw Exception("DatePickerDialog selectTime !in startTime .. endTime")
        }
    }

    /** 前后文本 */
    private var yearStartStr = ""
    private var yearEndStr = ""
    private var monthStartStr = ""
    private var monthEndStr = ""
    private var dayStartStr = ""
    private var dayEndStr = ""

    /**
     * 创建默认的日期选择器
     * @return [DateRollerPickerDialog]
     */
    fun normalBuilder(): DateRollerPickerDialog {
        setYearPickList()
        setMonthPickList()
        setDayPickList()
        return this
    }

    /** 日期选中监听器 */
    private val datePickDialogListener: (OnPickSelectListener<RollerPickerDialog>.() -> Unit) = {
        onSelect { _, key, _, _ ->
            val selectYear = getYearPickValue()
            val selectMonth = getMonthPickValue()
            val selectDay = getDayPickValue()
            when(key) {
                keyY -> {
                    // 重新选中月份
                    val newSelectMonth = updateMonthList(selectYear, selectMonth)
                    // 重新选择日
                    updateDayList(selectYear, newSelectMonth, selectDay)
                }
                keyM -> {
                    // 重新选择日
                    updateDayList(selectYear, selectMonth, selectDay)
                }
            }
        }
    }

    /**
     * 更新月列表
     * @param selectYear Int
     * @param selectMonth Int
     * @return Int 返回选中月
     */
    private fun updateMonthList(selectYear: Int, selectMonth: Int): Int {
        if (!isHasKey(keyM)) return selectMonth
        // 获取月份列表
        val monthList = getMonthList(selectYear)
        // 重新选中月份
        val newSelectMonth = if (monthList.contains("$selectMonth")) selectMonth else monthList[0].toInt()
        removePick(keyM)
        addPick(
            keyM, "$newSelectMonth", monthList,
            monthStartStr, monthEndStr, datePickDialogListener
        )
        return newSelectMonth
    }

    /**
     * 更新日列表
     * @param selectYear Int
     * @param selectMonth Int
     * @param selectDay Int
     * @return Int 返回选中日
     */
    private fun updateDayList(selectYear: Int, selectMonth: Int, selectDay: Int): Int {
        if (!isHasKey(keyD)) return selectDay
        // 获取日列表
        val dayList = getDayList(selectYear, selectMonth)
        // 重新选择日
        val newSelectDay = if (dayList.contains("$selectDay")) selectDay else dayList[0].toInt()
        removePick(keyD)
        addPick(
            keyD, "$newSelectDay", dayList,
            dayStartStr, dayEndStr
        )
        return newSelectDay
    }

    /**
     * 设置年份选择
     * @param start
     * @param end
     * @return [DateRollerPickerDialog]
     */
    fun setYearPickList(start: String = "", end: String = ""): DateRollerPickerDialog {
        yearStartStr = start
        yearEndStr = end
        val yearList = getYearList()
        if (isHasKey(keyY)) removePick(keyY)
        addPick(keyY, "${getTimeYear(selectTime)}", yearList,
            start, end, datePickDialogListener)
        return this
    }

    /**
     * 设置月份选择
     * @param start
     * @param end
     * @return [DateRollerPickerDialog]
     */
    fun setMonthPickList(start: String = "", end: String = ""): DateRollerPickerDialog {
        monthStartStr = start
        monthEndStr = end
        val monthList = getMonthList(getTimeYear(selectTime))
        if (isHasKey(keyM)) removePick(keyM)
        addPick(keyM, "${getTimeMonth(selectTime)}", monthList,
            monthStartStr, monthEndStr, datePickDialogListener)
        return this
    }

    /**
     * 设置日期选择
     * @param start
     * @param end
     * @return [DateRollerPickerDialog]
     */
    fun setDayPickList(start: String = "", end: String = ""): DateRollerPickerDialog {
        dayStartStr = start
        dayEndStr = end
        val dayList = getDayList(getTimeYear(selectTime), getTimeMonth(selectTime))
        if (isHasKey(keyD)) removePick(keyD)
        addPick(keyD, "${getTimeDay(selectTime)}", dayList,
            dayStartStr, dayEndStr)
        return this
    }

    /**
     * 获取选中年份
     * @return [Int]
     */
    fun getYearPickValue(): Int = getPickText(keyY).toIntOrNull()?: getTimeYear(selectTime)

    /**
     * 获取选中月份
     * @return [Int]
     */
    fun getMonthPickValue(): Int = getPickText(keyM).toIntOrNull()?: getTimeMonth(selectTime)

    /**
     * 获取选中日期
     * @return [Int]
     */
    fun getDayPickValue(): Int = getPickText(keyD).toIntOrNull()?: getTimeDay(selectTime)

    /**
     * 获取年列表
     * @return [List]
     */
    fun getYearList(): List<String> {
        val list = mutableListOf<String>()
        for (i in getTimeYear(startTime) .. getTimeYear(endTime)) {
            list.add("$i")
        }
        return list
    }

    /**
     * 获取月列表
     * @param selectYear 选中年份
     * @return [List]
     */
    fun getMonthList(selectYear: Int): List<String> {
        val list = mutableListOf<String>()
        val min = when (selectYear) {
            getTimeYear(startTime) -> getTimeMonth(startTime)
            else -> 1
        }
        val max = when(selectYear) {
            getTimeYear(endTime) -> getTimeMonth(endTime)
            else -> 12
        }
        for (i in min .. max) {
            list.add("$i")
        }
        return list
    }

    /**
     * 获取日列表
     * @param selectYear 选中年份
     * @param selectMonth 选中月份
     * @return [List]
     */
    fun getDayList(selectYear: Int, selectMonth: Int): List<String> {
        val list = mutableListOf<String>()
        val min = when {
            selectYear == getTimeYear(startTime)
                    && selectMonth == getTimeMonth(startTime) -> getTimeDay(startTime)
            else -> 1
        }
        val max = when {
            selectYear == getTimeYear(endTime)
                    && selectMonth == getTimeMonth(endTime) -> getTimeDay(endTime)
            else -> getDaysOfMonth(selectYear, selectMonth)
        }
        for (i in min .. max) {
            list.add("$i")
        }
        return list
    }

    /**
     * 获取指定月份的天数
     * @param year
     * @param month
     */
    private fun getDaysOfMonth(year: Int, month: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month -1)
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    /**
     * 获取时间戳中的年份
     * @param time
     */
    private fun getTimeYear(time: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return calendar.get(Calendar.YEAR)
    }

    /**
     * 获取时间戳中的月份
     * @param time
     */
    private fun getTimeMonth(time: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return calendar.get(Calendar.MONTH) + 1
    }

    /**
     * 获取时间戳中的日期
     * @param time
     */
    private fun getTimeDay(time: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return calendar.get(Calendar.DAY_OF_MONTH)
    }
}