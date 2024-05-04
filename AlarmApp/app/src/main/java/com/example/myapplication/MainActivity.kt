package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.myapplication.database.getActiveDays
import com.example.myapplication.database.readData
import com.example.myapplication.ui.theme.Dark_Purple
import kotlinx.coroutines.delay
import java.util.Calendar


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var hour by remember { mutableStateOf("0") }
            var minute by remember { mutableStateOf("0") }
            var second by remember { mutableStateOf("-1") }
            var amOrPm by remember { mutableStateOf("0") }
            LockOrientation()
            Background()
            SubHeader()
            Header()
            ConstraintBoxes(hour = hour, minute = minute, second = second, amOrPm = amOrPm)
            LaunchedEffect(Unit) {
                while (true) {
                    val cal = Calendar.getInstance()
                    hour = cal.get(Calendar.HOUR).run {
                        if (this.toString().length == 1) "0$this" else "$this"
                    }
                    minute = cal.get(Calendar.MINUTE).run {
                        if (this.toString().length == 1) "0$this" else "$this"
                    }
                    second = cal.get(Calendar.SECOND).toString()
                    amOrPm = cal.get(Calendar.AM_PM).run {
                        if (this == Calendar.AM) "AM" else "PM"
                    }
                    delay(1000)
                }
            }

//            val hr = "09"
//            val min = "54"
//            val timeOfDay = "PM"
//            if (hour == hr && minute == min && amOrPm == timeOfDay && second == "0") {
//                val intent = Intent(this, MathGame::class.java)
//                startActivity(intent)
//            }
        }

        Log.d(TAG, "Starting Periodic Work")
        val workManager = WorkManager.getInstance(application)
        workManager.enqueue(OneTimeWorkRequestBuilder<HelloWorker>().build())
        val timeCheckRequest: OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<timeCheckWorker>()
                .build()
        workManager.enqueue(timeCheckRequest)

    }

    @Composable
    private fun ConstraintBoxes(
        hour: String,
        minute: String,
        second: String,
        amOrPm: String,
    ) {
        ConstraintLayout {
            val (blankBox, scrollBox, newAlarm) = createRefs()
            Box(
                contentAlignment = Alignment.TopEnd,
                modifier = Modifier
                    .constrainAs(newAlarm) {}
                    .background(Color.Transparent)
                    .height(100.dp)
                    .fillMaxWidth()
            ) {
                NewAlarm()
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
                    text = "+",
                    color =  Dark_Purple,
                    fontSize = 35.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .padding(end = 31.dp)
                )
            }
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
                DigitalClockComponent(hour = hour, minute = minute, amOrPm = amOrPm)
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
                    .constrainAs(scrollBox) {
                        top.linkTo(blankBox.bottom, margin = 25.dp)
                    }
            ) {
                ScrollButtons()
            }
        }
    }

    @Composable
    fun NewAlarm(){
        Button(
            onClick = {
                val navigate = Intent(this@MainActivity, AlarmSettings::class.java)
                startActivity(navigate)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(4294019433),
            ),
            modifier = Modifier
                .padding(5.dp)
                .height(38.dp)
                .width(75.dp)

        ) {}
    }

    @Composable
    fun ScrollButtons() {
        val state = rememberScrollState()
        LaunchedEffect(Unit) { state.animateScrollTo(10) }
        val alarms = remember { readData(this) }
        Column(
            modifier = Modifier
                .background(Color(4281802289))
                .height(500.dp)
                .fillMaxWidth()
                .verticalScroll(state)
        ) {
            if (alarms != null) {
                alarms.forEachIndexed { index, alarm ->
                    val activeDays = getActiveDays(alarm.dayOfWeek)
                    Button(
                        onClick = {
                            val navigate = Intent(this@MainActivity, AlarmSettings::class.java)
                            navigate.putExtra("BUTTON_INDEX", index)
                            startActivity(navigate)
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(4284563290),
                            contentColor = Color(4281802289)
                        ),
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        ConstraintLayout {
                            val (textBox, switchButton, daysBox) = createRefs()
                            Box(
                                modifier = Modifier
                                    .background(Color.Transparent)
                                    .fillMaxWidth(0.75f)
                                    .fillMaxHeight(0.70f)
                                    .constrainAs(textBox) {
                                        bottom.linkTo(daysBox.top)
                                    }
                            ){
                                Text(
                                    text = "${alarm.hour}:${alarm.minutes} ${alarm.meridiem}",
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
                                    color = Dark_Purple,
                                    fontSize = 40.sp,
                                    fontFamily = fontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(4294493562))
                                    .height(20.dp)
                                    .width(175.dp)
                                    .constrainAs(daysBox) {
                                        top.linkTo(textBox.bottom)
                                    }
                            ) {
                                Text(
                                    style = LocalTextStyle.current.merge(
                                        TextStyle(
                                            platformStyle = PlatformTextStyle(includeFontPadding = false),
                                            lineHeightStyle = LineHeightStyle(
                                                alignment = LineHeightStyle.Alignment.Center,
                                                trim = LineHeightStyle.Trim.None
                                            )
                                        )
                                    ),
                                    text = activeDays,
                                    textAlign = TextAlign.Center,
                                    color = Dark_Purple,
                                    fontSize = 15.sp,
                                    fontFamily = fontFamily,
                                    fontWeight = FontWeight.Bold)
                            }
                            Box(
                                modifier = Modifier
                                    .padding(15.dp)
                                    .constrainAs(switchButton) {}
                            ) {
                                OnOffButton()
                            }
                            createHorizontalChain(textBox,switchButton)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OnOffButton() {
    var checked by remember { mutableStateOf(true) }
    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        },
        Modifier.scale(2f),
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color(4294493562),
            checkedTrackColor = Color(4281802289),
            uncheckedBorderColor = Color(4281802289),
            uncheckedThumbColor = Color(4281802289),
            uncheckedTrackColor = Color(4284563290)
        )

    )
}

@Composable
fun DigitalClockComponent (
    hour: String,
    minute: String,
    amOrPm: String,
) {
    Text(
        text = "$hour:$minute $amOrPm",
        color = Dark_Purple,
        fontSize = 60.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight.ExtraBold
    )
}
/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val buttonIndex = intent.getIntExtra("BUTTON_INDEX", -1)
            ButtonID(idNum = buttonIndex)

        }
    }

    @Composable
    fun ButtonID(idNum: Int) {
        if (idNum != -1) {
            when (idNum) {
                idNum -> {
                    val intent = Intent(this, AlarmSettings::class.java)
                    startActivity(intent)
                }
            }
        }
    }
 */


class HelloWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    override fun doWork(): Result {

        Log.d(TAG, "hello world")

        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }
}