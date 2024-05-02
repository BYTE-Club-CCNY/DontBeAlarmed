package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.Dark_Purple
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import com.example.myapplication.ui.theme.Alarm
import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import com.example.myapplication.ui.theme.Dandelion
import com.example.myapplication.ui.theme.Soft_Purple
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.ComposeCompilerApi
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import java.util.Calendar

class ButtonData : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val buttonIndex = intent.getIntExtra("BUTTON_INDEX", -1)
            var hour by remember { mutableStateOf("0") }
            var minute by remember { mutableStateOf("0") }
            var amOrPm by remember { mutableStateOf("0") }
            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.special_alarm)

            LaunchedEffect(Unit) {
                while (true) {
                    val cal = Calendar.getInstance()
                    hour = cal.get(Calendar.HOUR).run {
                        if (this.toString().length == 1) "0$this" else "$this"
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

            ButtonID(idNum = buttonIndex, hour = hour, minute = minute, amOrPm = amOrPm)
            LockOrientation()
            Background()
            SubHeader()
            Header()

        }
    }

    @Composable
    fun ButtonID(idNum: Int, hour: String, minute: String, amOrPm: String) {
        if (idNum != -1) {
            when (idNum) {
                1 -> {
                        val intent = Intent(this, AlarmSettings::class.java)
                        startActivity(intent)
                }

                2 -> {
                        val intent = Intent(this, AlarmSettings::class.java)
                        startActivity(intent)
                }
                3 -> {
                        val intent = Intent(this, AlarmSettings::class.java)
                        startActivity(intent)
                }
            }
        }
    }
}
