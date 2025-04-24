package com.qomunal.opensource.androidresearch.common.ext

import android.app.DatePickerDialog
import android.content.Context
import com.qomunal.opensource.androidresearch.common.view.CustomTimePicker
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Created by faisalamircs on 02/07/2024
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * -----------------------------------------
 */

const val ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val DEFAULT_FORMAT = "dd LLLL yyyy"
const val DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss"
const val DATE_TIME_FORMAT_NO_SECOND = "dd-MM-yyyy HH:mm"
const val DATE_TIME_FORMAT_NO_SECOND_TEXT = "dd LLLL yyyy HH:mm"

const val DATE_MONTH = "dd LLL"

const val DATE_TIME_FORMAT_AM_PM = "dd MMMM yyyy, H.mm a"

const val SIMPLE_DATE_FORMAT = "dd-MM-yyyy"
const val SIMPLE_DATE_FORMAT_YEAR_FIRST = "yyyy-MM-dd"
const val SIMPLE_DATE_FORMAT_YEAR_FIRST_NO_SECOND = "yyyy-MM-dd HH:mm"
const val SIMPLE_DATE_FORMAT_REVERSE = "yyyyMMdd"

const val TIME_FORMAT = "HH:mm"
const val TIME_SECOND_FORMAT = "HH:mm:ss"
const val TIME_AM_PM_FORMAT = "H.mm a"

fun getLocaleExt(): Locale {
    return Locale.getDefault()
}

fun Date.getMillisExt(to: String): Long {
    var date = this
    try {
        date = SimpleDateFormat(SIMPLE_DATE_FORMAT, getLocaleExt()).parse(to) ?: this
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return date.time
}

fun String.dateFormater(
    from: String = SIMPLE_DATE_FORMAT,
    to: String = DATE_TIME_FORMAT,
): String {
    val fromParser = SimpleDateFormat(from, Locale.getDefault())
    val toParser = SimpleDateFormat(to, Locale.getDefault())
    return toParser.format(fromParser.parse(this) as Date)
}


fun Context.showDatePickerExt(
    isUsingTimePicker: Boolean = false,
    minDate: String? = null,
    maxDate: String? = null,
    minHour: Int? = null,
    minMinute: Int? = null,
    maxHour: Int? = null,
    maxMinute: Int? = null,
    setup: (date: String, time: String?, day: Int, month: Int, year: Int, hour: Int?, minute: Int?) -> Unit,
) {
    // calender class's instance and get current date , month and year from calender
    val c = Calendar.getInstance()
    val mYear = c.get(Calendar.YEAR) // current year
    val mMonth = c.get(Calendar.MONTH) // current month
    val mDay = c.get(Calendar.DAY_OF_MONTH) // current day

    // date picker dialog
    DatePickerDialog(
        this,
        { _, year, monthOfYear, dayOfMonth -> // set day of month , month and year value in the edit text

            val dateText = "${dayOfMonth}-${monthOfYear + 1}-${year}"

            if (isUsingTimePicker) {
                this.showTimePickerExt(dateText, minHour, minMinute, maxHour, maxMinute) { _, time, hour, minute ->
                    setup(dateText, time, mDay, mMonth, mYear, hour, minute)
                }
            } else {
                setup(dateText, null, mDay, mMonth, mYear, null, null)
            }

        }, mYear, mMonth, mDay
    ).apply {
        minDate?.let { date ->
            datePicker.minDate = Date().getMillisExt(date)
        }
        maxDate?.let { date ->
            datePicker.maxDate = Date().getMillisExt(date)
        }
    }.show()

}


fun Context.showTimePickerExt(
    date: String? = null,
    minHour: Int? = null,
    minMinute: Int? = null,
    maxHour: Int? = null,
    maxMinute: Int? = null,
    setup: (date: String, time: String, hour: Int, minute: Int) -> Unit,
) {
    val c = Calendar.getInstance()
    val hour = c[Calendar.HOUR_OF_DAY]
    val minute = c[Calendar.MINUTE]
    CustomTimePicker(
        this,
        { _, selectedHour, selectedMinute ->
            val time = "$selectedHour:$selectedMinute"
            if (date != null) {
                setup(date, time.dateFormater(TIME_FORMAT, TIME_FORMAT), hour, minute)
            } else {
                setup("", time.dateFormater(TIME_FORMAT, TIME_FORMAT), hour, minute)
            }
        },
        hour,
        minute,
        true
    ).apply {
        setMin(minHour, minMinute)
        setMax(maxHour, maxMinute)
        show()
    }
}