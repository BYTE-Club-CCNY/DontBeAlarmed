package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(alarm: Alarm)

    @Update
    suspend fun update(alarm: Alarm)

    @Delete
    suspend fun delete(alarm: Alarm)

    @Query("SELECT * from Alarms WHERE id = :id")
    fun getAlarm(id: Int): Flow<Alarm>

    @Query("SELECT * from Alarms ORDER BY title ASC")
    fun getAllItems(): Flow<List<Alarm>>

    @Query("SELECT * from Alarms WHERE timestamp = :timestamp")
    fun getTimestamps(timestamp: Long): Flow<Alarm>

}
