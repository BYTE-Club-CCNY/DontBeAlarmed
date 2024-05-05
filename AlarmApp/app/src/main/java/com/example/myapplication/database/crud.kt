package com.example.myapplication.database

import android.content.Context
import android.util.Log
import com.example.myapplication.R
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.BufferedReader
import java.io.File
import java.io.FileWriter
import java.io.InputStreamReader
import kotlin.collections.MutableList




data class Alarmsettings(
    val title: String,
    val hour: Int,
    val minutes: Int,
    val meridiem: String,
    val on: Boolean,
    val sound: Int,
    val gameType: Int,
    val dayOfWeek: Map<String, Boolean>
)
fun readData(context: Context): MutableList<Alarmsettings>? {
    val gson = Gson()
    return try {
        context.resources.openRawResource(R.raw.data).use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                gson.fromJson(reader, Array<Alarmsettings>::class.java).toMutableList()
            }
        }
    } catch (e: JsonSyntaxException) {

        Log.e("readData", "Failed to parse the JSON data", e)
        null
    } catch (e: Exception) {

        Log.e("readData", "Error reading from raw resource", e)
        null
    }
}

private fun writeData(context: Context, data: Array<Alarmsettings>?) {
    val file = File(context.filesDir, "data.json")
    val writer = FileWriter(file)
    Gson().toJson(data, writer)
    writer.close()
}


fun createAlarmInJson(
    context: Context,
    title: String,
    hour: Int,
    minutes: Int,
    meridiem: String,
    on: Boolean,
    sound: Int,
    gameType: Int,
    dayOfWeek: Map<String, Boolean>
) {
    val alarms = readData(context) ?: mutableListOf()

    val alarm = Alarmsettings(
        title = title,
        hour = hour,
        minutes = minutes,
        meridiem = meridiem,
        on = on,
        sound = sound,
        gameType = gameType,
        dayOfWeek = dayOfWeek
    )

    alarms.add(alarm)

    val alarmsArray = alarms.toTypedArray()

    writeData(context, alarmsArray)

    Log.d("test","Alarm created successfully.")
}

fun getActiveDays(dayOfWeek: Map<String, Boolean>): String {
    val daysOrder = listOf("Mon", "Tus", "Wed", "Thu", "Fri", "Sat", "Sun")
    return daysOrder.map { day ->
        if (dayOfWeek[day] == true) day.first() else ' '
    }.joinToString(separator = "   ")
}
