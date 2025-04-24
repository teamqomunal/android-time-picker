package com.qomunal.opensource.androidresearch.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.qomunal.opensource.androidresearch.common.base.BaseActivity
import com.qomunal.opensource.androidresearch.common.ext.dateFormater
import com.qomunal.opensource.androidresearch.common.ext.showDatePickerExt
import com.qomunal.opensource.androidresearch.common.ext.showToast
import com.qomunal.opensource.androidresearch.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : BaseActivity<ActivityMainBinding>() {

    // calender class's instance and get current date , month and year from calender
    private val c = Calendar.getInstance()
    private val mYear = c.get(Calendar.YEAR) // current year
    private val mMonth = c.get(Calendar.MONTH) // current month
    private val mDay = c.get(Calendar.DAY_OF_MONTH) // current day

    private val hour = c.get(Calendar.HOUR_OF_DAY)
    private val minute = c.get(Calendar.MINUTE)

    private val dateToday = "$mDay-${mMonth + 1}-$mYear"
    private val timeToday = "$hour:$minute"

    private var date1 = dateToday

    private var minHour = 0
    private var minMinute= 0

    private val viewModel: MainViewModel by viewModels()
    private val router: MainRouter by lazy {
        MainRouter(this)
    }

    override fun setupViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initUI() {
        binding.apply {
            btnTest1.setOnClickListener {
                showDatePickerExt(
                    minDate = date1,
                    isUsingTimePicker = true,
                    minHour = hour,
                    minMinute = minute
                ) { date, time, day, month, year, hour, minute ->
                    btnTest1.text = "$date $time"
                    date1 = date
                    minute?.let {
                        minMinute = it
                    }
                    hour?.let {
                        minHour = it
                    }

                }
            }

            btnTest2.setOnClickListener {
                showDatePickerExt(
                    minDate = date1,
                    isUsingTimePicker = true,
                    minHour = minHour,
                    minMinute = minMinute
                ) { date, time, day, month, year, hour, minute ->
                    btnTest2.text = "$date $time"
                    date1 = date
                }
            }
        }
    }

    override fun initObserver() {
        viewModel.apply {

        }
    }

}