package com.example.myapplication.database

data class tempAlarm(
    val title: String,
    val hour: Int,
    val minutes: Int,
    val meridiem: String,
    val On: Boolean,
    val sound: Int,
    val gameType: Int,
    val dayOfWeek: Map<String, Boolean> // New variable
)
