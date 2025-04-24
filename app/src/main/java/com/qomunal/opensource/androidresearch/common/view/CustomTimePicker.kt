package com.qomunal.opensource.androidresearch.common.view

import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.widget.TimePicker


/**
 * Created by faisalamircs on 24/04/2025
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * -----------------------------------------
 */


class CustomTimePicker(
    context: Context?, callBack: OnTimeSetListener?, hourOfDay: Int,
    minute: Int, is24HourView: Boolean,
) :
    TimePickerDialog(context, callBack, hourOfDay, minute, is24HourView) {
    private var mMinHour = -1
    private var mMinMinute = -1
    private var mMaxHour = 100
    private var mMaxMinute = 100
    private var mCurrentHour: Int
    private var mCurrentMinute: Int

    init {
        mCurrentHour = hourOfDay
        mCurrentMinute = minute
        // Somehow the onTimeChangedListener is not set by TimePickerDialog
        // in some Android Versions, so, Adding the listener using
        // reflections
        try {
            val superclass: Class<*> = javaClass.superclass
            val mTimePickerField = superclass.getDeclaredField("mTimePicker")
            mTimePickerField.isAccessible = true
            val mTimePicker = mTimePickerField.get(this) as TimePicker
            mTimePicker.setOnTimeChangedListener(this)
        } catch (e: NoSuchFieldException) {
        } catch (e: IllegalArgumentException) {
        } catch (e: IllegalAccessException) {
        }
    }

    fun setMin(hour: Int? = null, minute: Int? = null) {
        hour?.let {
            mMinHour = it
        }
        minute?.let {
            mMinMinute = it
        }
    }

    fun setMax(hour: Int? = null, minute: Int? = null) {
        hour?.let {
            mMaxHour = it
        }
        minute?.let {
            mMaxMinute = it
        }
    }

    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
        super.onTimeChanged(view, hourOfDay, minute)
        val validTime =
            !(((hourOfDay < mMinHour) || (hourOfDay == mMinHour && minute < mMinMinute))
                    || ((hourOfDay > mMaxHour) || (hourOfDay == mMaxHour && minute > mMaxMinute)))
        if (validTime) {
            mCurrentHour = hourOfDay
            mCurrentMinute = minute
        } else {
            updateTime(mCurrentHour, mCurrentMinute)
        }
    }
}