package com.example.myapplication.data

data class AlarmState(
    val alarms: List<Alarm> = emptyList(),
    val title: String = "",
    val timestamp: Long = 0,
    val activity: String = "",
    val isAddingAlarm: Boolean = false

    )
