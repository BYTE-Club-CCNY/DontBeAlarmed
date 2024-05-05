package com.example.myapplication.ui.theme

class OldAlarm (){
    // sets properties of the alarm including if its on or off, title, hour, minute
    var title = ""
    var hour = "00"
    var minute = "00"
    // AM and PM are defined as periods of the day. and integer of 1 is AM and 2 is PM
    var meridiem = "AM"
    var alarmStatus = false
    // default chooses the first sound
    var sound = 1
    // creates default no activities
    var gameType = 1
    // creates an array of 7 values which are default off
    var weekDays: MutableMap<String, Boolean> = mutableMapOf(
        "Sun" to false,
        "Mon" to false,
        "Tue" to false,
        "Wed" to false,
        "Thu" to false,
        "Fri" to false,
        "Sat" to false
    )
    // default snooze 3 times and five minutes
    var snoozeQuantity = 3
    var snoozeDuration = 5
}