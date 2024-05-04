package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.myapplication.database.readData
import java.util.Calendar
import java.util.concurrent.TimeUnit


class timeCheckWorker (appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        Log.d(TAG, "Checking Time")
        TimeCheck()
        val timeCheckRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<timeCheckWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(this.applicationContext).enqueue(timeCheckRequest)
        return Result.success()
    }

    fun TimeCheck() {
// checks each alarm in json file and compares it to current time
        val cal = Calendar.getInstance()
        var currentHour = cal.get(Calendar.HOUR).run {
            if (this.toString().length == 1) "0$this" else "$this"
        }
        var currentMinute = cal.get(Calendar.MINUTE).run {
            if (this.toString().length == 1) "0$this" else "$this"
        }
        var currentAmOrPm = cal.get(Calendar.AM_PM).run {
            if (this == Calendar.AM) "AM" else "PM"
        }
        var currentDay = cal.get(Calendar.DAY_OF_WEEK).run {
            when(this) {
                1 -> "Sun"
                2 -> "Mon"
                3 -> "Tue"
                4 -> "Wed"
                5 -> "Thu"
                6 -> "Fri"
                7 -> "Sat"
                else -> ""
            }
        }
        val alarms = readData(applicationContext)
        alarms?.forEachIndexed { index, alarm ->
            val hr = alarm.hour
            val min = alarm.minutes
            val timeOfDay = alarm.meridiem
            if (currentHour == hr && currentMinute == min && currentAmOrPm == timeOfDay && alarm.dayOfWeek[currentDay] == true) {
                val intent = Intent(applicationContext, MathGame::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(applicationContext, intent, Bundle.EMPTY)
            }

        }
    }
}
