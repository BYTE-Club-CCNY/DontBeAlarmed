package com.example.inventory.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val alarmRepository: AlarmRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineAlarmRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [AlarmRepository]
     */
    override val alarmRepository: AlarmRepository by lazy {
        OfflineAlarmRepository(AlarmDatabase.getDatabase(context).AlarmDao())
    }
}
