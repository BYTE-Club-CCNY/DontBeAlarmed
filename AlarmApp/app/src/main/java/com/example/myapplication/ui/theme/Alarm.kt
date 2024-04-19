package com.example.myapplication.ui.theme

class Alarm (
    // sets properties of the alarm number, if its on or off, title, hour, minute
    var number: Int, val alarmStatus: Boolean, var hour: Int, var minute: Int){
    // AM and PM are defined as periods of the day. and integer of 1 is AM and 2 is PM
    var period = 1
    var status = false
    var title = ""
    // creates an array of 7 values which are default off
    var weekDays = BooleanArray(8)
    // creates default no activities
    var activities = 0
    // default chooses the first sound
    var sound = 1
    // default snooze 3 times and five minutes
    var snoozeQuantity = 3
    var snoozeDuration = 5
}