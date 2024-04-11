package com.example.myapplication.data

sealed interface AlarmEvent {
    object SaveAlarm: AlarmEvent
    data class SetTitle(val title: String): AlarmEvent
    data class SetTimestamp(val timestamp: Long): AlarmEvent
    data class SetActivity(val activity: String): AlarmEvent
    object ShowAlarm: AlarmEvent
    object HideAlarm : AlarmEvent
    data class DeleteAlarm(val alarm: Alarm): AlarmEvent

}