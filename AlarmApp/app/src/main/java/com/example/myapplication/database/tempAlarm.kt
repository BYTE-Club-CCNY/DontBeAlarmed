package com.example.myapplication.database

data class tempAlarm(
    var title: String,
    var hour: String,
    var minute: String,
    var meridiem: String,
    var alarmStatus: Boolean,
    var sound: Int,
    var gameType: Int,
    var dayOfWeek: MutableMap<String, Boolean> // New variable
){}
