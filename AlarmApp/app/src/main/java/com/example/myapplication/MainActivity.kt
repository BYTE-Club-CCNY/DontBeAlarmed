package com.example.datetimeui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.DayOfWeek

class MainActivity : AppCompatActivity() {
    private lateinit var alarmTimeInput: Spinner
    private lateinit var dayOfWeekInput: Spinner
    private lateinit var soundInput: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        alarmTimeInput = findViewById(R.id.alarmTimeInput)
        dayOfWeekInput = findViewById((R.id.dayOfWeekInput))
        soundInput = findViewById((R.id.soundInput))


    }
}
