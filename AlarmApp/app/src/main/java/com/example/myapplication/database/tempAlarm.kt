package com.example.myapplication.database

data class tempAlarm(
    val title: String,
    val hour: String,
    val minutes: String,
    val meridiem: String,
    val On: Boolean,
    val sound: Int,
    val gameType: Int,
    val dayOfWeek: Map<String, Boolean> // New variable
)
