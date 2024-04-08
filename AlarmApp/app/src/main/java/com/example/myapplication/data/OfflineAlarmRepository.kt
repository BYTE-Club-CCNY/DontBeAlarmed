/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.data

import kotlinx.coroutines.flow.Flow

class OfflineAlarmRepository(private val alarmDao: AlarmDao) : AlarmRepository {
    override fun getAllAlarmsStream(): Flow<List<Alarm>> = alarmDao.getAllItems()

    override fun getAlarmStream(id: Int): Flow<Alarm?> = alarmDao.getAlarm(id)

    override fun getTimestamp(timestamp: Long): Flow<Alarm> = alarmDao.getTimestamp(timestamp)

    override suspend fun insertAlarm(alarm: Alarm) = alarmDao.insert(alarm)

    override suspend fun deleteAlarm(alarm: Alarm) = alarmDao.delete(alarm)

    override suspend fun updateAlarm(alarm: Alarm) = alarmDao.update(alarm)
}