package com.example.myapplication

import androidx.core.graphics.isSrgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Alarm
import com.example.myapplication.data.AlarmDao
import com.example.myapplication.data.AlarmEvent
import com.example.myapplication.data.AlarmState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AlarmViewModel(
    private val dao: AlarmDao
): ViewModel() {

    private val _state = MutableStateFlow(AlarmState())
    private val _alarms = dao.getAllItems()
    val state = combine(_state, _alarms) { state, alarms ->
        state.copy(
            alarms = alarms

        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AlarmState())

    fun onEvent(event: AlarmEvent){
        when(event) {
            is AlarmEvent.DeleteAlarm -> {
                viewModelScope.launch {
                dao.delete(event.alarm)
                }
            }
            is AlarmEvent.SetActivity -> {
                _state.update { it.copy(
                    activity = event.activity
                ) }
            }
            is AlarmEvent.SetTimestamp -> {
                _state.update { it.copy(
                    timestamp = event.timestamp
                ) }
            }
            is AlarmEvent.SetTitle -> {
                _state.update { it.copy(
                    title = event.title
                ) }
            }
            AlarmEvent.ShowAlarm -> {
                _state.update { it.copy(
                    isAddingAlarm = true
                ) }
            }
            AlarmEvent.SaveAlarm -> {
                val title = state.value.title
                val timestamp = state.value.timestamp
                val activity = state.value.activity

                if(title.isBlank() || timestamp <= 0 || activity.isBlank()){
                    return
                }

                val alarm = Alarm(
                    title = title,
                    timestamp = timestamp,
                    activity = activity
                )
                viewModelScope.launch {
                    dao.insert(alarm)
                }
                _state.update { it.copy(
                    isAddingAlarm = false,
                    title = "",
                    timestamp = 0,
                    activity = ""

                ) }
            }
        }
    }
}