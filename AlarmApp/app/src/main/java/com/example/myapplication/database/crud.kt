package com.example.myapplication.database

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import kotlin.collections.MutableList





fun readData(context: Context): MutableList<tempAlarm>? {
    val gson = Gson()
    return try {
        context.openFileInput("alarms.json").use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                gson.fromJson(reader, Array<tempAlarm>::class.java).toMutableList()
            }
        }
    } catch (e: FileNotFoundException) {
        // If the file is not found, return null or an empty list depending on your logic
        null
    } catch (e: JsonSyntaxException) {
        Log.e("readData", "Failed to parse the JSON data", e)
        null
    } catch (e: Exception) {
        Log.e("readData", "Error reading from internal storage", e)
        null
    }
}

fun logAlarm(context: Context, alarm: tempAlarm) {
    val existingContent = readJsonData(context)
    val jsonArray = try {
        JSONArray(existingContent)
    } catch (e: JSONException) {
        JSONArray()
    }

    val alarmObject = JSONObject()
    try {
        alarmObject.put("title", alarm.title)
        alarmObject.put("hour", alarm.hour)
        alarmObject.put("minute", alarm.minute)
        alarmObject.put("meridiem", alarm.meridiem)
        alarmObject.put("alarmStatus", alarm.alarmStatus)
        alarmObject.put("sound", alarm.sound)
        alarmObject.put("gameType", alarm.gameType)

        val dayOfWeekJson = JSONObject()
        alarm.dayOfWeek.forEach { (key, value) ->
            dayOfWeekJson.put(key, value)
        }
        alarmObject.put("dayOfWeek", dayOfWeekJson)

        jsonArray.put(alarmObject)

        val jsonString = jsonArray.toString()

        context.openFileOutput("alarms.json", Context.MODE_PRIVATE).use { outputStream ->
            outputStream.write(jsonString.toByteArray())
        }
    } catch (e: JSONException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

private fun readJsonData(context: Context): String {
    return try {
        val fis: FileInputStream = context.openFileInput("alarms.json")
        val isr = InputStreamReader(fis)
        val bufferedReader = BufferedReader(isr)
        val sb = StringBuilder()
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            sb.append(line)
        }
        sb.toString()
    } catch (e: IOException) {
        ""
    }
}
fun clearJson(context: Context) {
    context.openFileOutput("alarms.json", Context.MODE_PRIVATE).use { outputStream ->
        outputStream.write("".toByteArray())
    }
}
fun getActiveDays(dayOfWeek: Map<String, Boolean>): String {
    val daysOrder = listOf("Mon", "Tus", "Wed", "Thu", "Fri", "Sat", "Sun")
    return daysOrder.map { day ->
        if (dayOfWeek[day] == true) day.first() else ""
    }.joinToString(separator = "   ")
}
