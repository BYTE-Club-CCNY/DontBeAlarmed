package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.myapplication.database.readData
import com.example.myapplication.ui.theme.Dark_Purple
import kotlinx.coroutines.delay
import java.util.Calendar

class NoGame : ComponentActivity() {
    val alarms = readData(applicationContext)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LockOrientation()
            Background()
            SubHeader()
            Header()
            var hour by remember { mutableStateOf("0") }
            var minute by remember { mutableStateOf("0") }
            var amOrPm by remember { mutableStateOf("0") }
            LaunchedEffect(Unit) {
                while (true) {
                    val cal = Calendar.getInstance()
                    hour = cal.get(Calendar.HOUR).run {
                        if (this == 1) "12" else if (this.toString().length == 1) "0$this" else "$this"
                    }
                    minute = cal.get(Calendar.MINUTE).run {
                        if (this.toString().length == 1) "0$this" else "$this"
                    }
                    amOrPm = cal.get(Calendar.AM_PM).run {
                        if (this == Calendar.AM) "AM" else "PM"
                    }

                    delay(1000)
                }
            }
            ConstraintBoxes(hour = hour, minute = minute, amOrPm = amOrPm)
        }
    }


    @Composable
    private fun ConstraintBoxes(
        hour: String,
        minute: String,
        amOrPm: String,
    ) {
        ConstraintLayout {
            val (blankBox, stopButton) = createRefs()
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .constrainAs(blankBox) {}
                    .background(Color.Transparent)
                    .padding(top = 60.dp)
                    .padding(horizontal = 20.dp)
            ) {
                DigitalClockComponent(if (hour == "00") "12" else hour, minute, amOrPm)
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .constrainAs(stopButton) {
                        top.linkTo(blankBox.bottom)
                    }
                    .padding(horizontal = 5.dp)
                    .padding(vertical = 25.dp)
                    .fillMaxSize()

            ) {
                Button(
                    onClick = {
                        val intent = Intent(this@NoGame, MainActivity::class.java)
                        startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(4294493562),
                    ),
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                        Text(
                            style = LocalTextStyle.current.merge(
                                TextStyle(
                                    platformStyle = PlatformTextStyle(
                                        includeFontPadding = false
                                    ),
                                    lineHeightStyle = LineHeightStyle(
                                        alignment = LineHeightStyle.Alignment.Center,
                                        trim = LineHeightStyle.Trim.None
                                    )
                                )
                            ),
                            text = "Stop",
                            color = Dark_Purple,
                            fontSize = 60.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.ExtraBold,
                        )
                    }
                }
            }
        }
    }