package com.example.myapplication.database

import android.content.Context
import android.util.Log
import com.example.myapplication.R
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.BufferedReader
import java.io.InputStreamReader

fun readData(context: Context): Array<tempAlarm>? {
    val gson = Gson()
    return try {
        context.resources.openRawResource(R.raw.data).use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                gson.fromJson(reader, Array<tempAlarm>::class.java)
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

fun getActiveDays(dayOfWeek: Map<String, Boolean>): String {
    val daysOrder = listOf("Mon", "Tus", "Wed", "Thu", "Fri", "Sat", "Sun")
    return daysOrder.map { day ->
        if (dayOfWeek[day] == true) day.first() else ""
    }.joinToString(separator = "   ")
}
