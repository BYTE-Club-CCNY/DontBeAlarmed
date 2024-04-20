package com.example.myapplication

import android.media.MediaPlayer
import android.content.Intent
import android.os.Bundle
import android.widget.NumberPicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.myapplication.ui.theme.Alarm
import com.example.myapplication.ui.theme.Dandelion
import com.example.myapplication.ui.theme.Dark_Purple
import com.example.myapplication.ui.theme.Light_Gray
import com.example.myapplication.ui.theme.Sunset_Orange


var firstAlarm = Alarm(1, true, 1, 0)
var tempAlarm = Alarm(0,true,0,0)
private lateinit var mediaPlayer: MediaPlayer

class AlarmSettings : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            tempAlarm = firstAlarm
            Background()
            SubHeader()
            Header()
            SettingsPage()
        }
    }

    @Composable
    fun SettingsPage() {
        ConstraintLayout {
            //header notifying next alarm time
            val (blankBox, titleBox, timeBox, weekBox, activityBox, soundBox, snoozeBox) = createRefs()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp)
                    .constrainAs(blankBox) {}
                    .background(Color.Transparent)
                    .padding(top = 60.dp)
                    .padding(horizontal = 20.dp)
            ) {
                Column(horizontalAlignment = AbsoluteAlignment.Right) {
                    Text(
                        text = "Next Alarm in: ...",
                        color = Dark_Purple,
                        fontSize = 45.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Medium
                    )
                    SaveButton()
                }
            }
            //section to input title for alarm
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .width(300.dp)
                    .height(60.dp)
                    .constrainAs(titleBox) {
                        // change to 25 margin once i figure out textfield formatting
                        top.linkTo(blankBox.bottom, margin = 10.dp)
                    }
                    // change to White once i figure out textfield formatting
                    .background(Color.Transparent)
            ) {
                AlarmTitleInputBox()
            }
            //section to input time values
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(100.dp)
                    .constrainAs(timeBox) {
                        // change to 25 margin once i figure out textfield formatting
                        top.linkTo(titleBox.bottom, margin = 10.dp)
                    }
                    .background(Color.Transparent)
                    .padding(all = 5.dp)
            )
            {
                Row {
                    //hours
                    var selectedHour by remember { mutableStateOf(1) }
                    var selectedMinute by remember { mutableStateOf(0) }
                    var selectedPeriod by remember { mutableStateOf(1) }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(25.dp))
                            .background(Color.White)
                            .width(70.dp)
                            .height(100.dp)
                    ) {
                        HourPicker(
                            selectedHour = tempAlarm.hour,
                            onHourSelected = { hour ->
                                selectedHour = hour
                                tempAlarm.hour = selectedHour
                            })
                    }
                    Text(
                        text = ":",
                        color = Light_Gray,
                        fontSize = 45.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Light
                    )
                    //minutes
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(25.dp))
                            .background(Color.White)
                            .width(70.dp)
                            .height(100.dp)
                    ) {
                        MinutePicker(
                            selectedMinute = tempAlarm.minute,
                            onMinuteSelected = { minute ->
                                selectedMinute = minute
                                tempAlarm.minute = selectedMinute
                            })
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(25.dp))
                            .background(Color.White)
                            .width(70.dp)
                            .height(100.dp)
                    ) {
                        DayPeriodPicker(
                            selectedPeriod = tempAlarm.period,
                            onPeriodSelected = { period ->
                                selectedPeriod = period
                                tempAlarm.period = selectedPeriod
                            })
                    }
                }
            }
            //section for week
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(60.dp)
                    .constrainAs(weekBox) {
                        top.linkTo(timeBox.bottom, margin = 25.dp)
                    }
                    .background(Sunset_Orange)
                    .padding(all = 5.dp)
            )
            {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                )
                {
                    WeekDayButtons()
                }
//            Text(
//                text = "SMTWTFS",
//                color = Dark_Purple,
//                fontSize = 40.sp,
//                fontFamily = fontFamily,
//                fontWeight = FontWeight.Bold)
            }
            // section for activity selection
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(60.dp)
                    .constrainAs(activityBox) {
                        top.linkTo(weekBox.bottom, margin = 25.dp)
                    }
                    .background(Sunset_Orange)
                    .padding(all = 5.dp)
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Activity",
                        color = Dark_Purple,
                        fontSize = 20.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        modifier = Modifier
                            .width(30.dp)
                            .height(60.dp)
                    )
                    ActivityButton()
                }
            }
            // section for sound selection
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(60.dp)
                    .constrainAs(soundBox) {
                        top.linkTo(activityBox.bottom, margin = 25.dp)
                    }
                    .background(Sunset_Orange)
                    .padding(all = 5.dp)
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Sound",
                        color = Dark_Purple,
                        fontSize = 20.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold
                    )
                    SoundButtons()
                }
            }
            // section to customize snooze duration and quantity
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(75.dp)
                    .constrainAs(snoozeBox) {
                        top.linkTo(soundBox.bottom, margin = 25.dp)
                    }
                    .background(Sunset_Orange)
                    .padding(all = 5.dp)
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Snooze",
                        color = Dark_Purple,
                        fontSize = 20.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    @Composable
    fun ActivityButton() {
        Button(
            content = {
                Text(
                    text = "New",
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
                    fontSize = 15.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold
                )
            },
            onClick =
            {
                val nav = Intent(this, ActivityChoice::class.java)
                startActivity(nav)
            },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Dandelion,
                contentColor = Dark_Purple,
            ),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .height(40.dp)
                .width(80.dp)
        )
    }

    @Composable
    fun SoundButtons() {
        for (i in 1..3) {
            Button(
                content = {
                    Text(
                        text = "Sound $i",
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
                        fontSize = 12.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold
                    )
                },
                onClick =
                {
                    tempAlarm.sound = i
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Dandelion,
                    contentColor = Dark_Purple,
                ),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .height(40.dp)
                    .width(70.dp)
            )
        }
    }
}


@Composable
fun SaveButton() {
    Button(
        content = {
            Text(
                text = "Save",
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
                fontSize = 15.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Light
            )
        },
        onClick =
        {
            firstAlarm = tempAlarm
        },
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Dandelion,
            contentColor = Dark_Purple,
        ),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .height(40.dp)
            .width(40.dp))
}
@Preview
@Composable
fun WeekDayButtons() {
    for(i in 1..7) {
        var selected by remember { mutableStateOf(false) }
        val color = if (selected) Color(4281802289) else Color.Yellow//colorchange
        val textColor = if (selected) Color.DarkGray else Color.Cyan //colorchange
        Button(
            content = {
                Text(
                    text = WeekDayConvert(i),
                    style = LocalTextStyle.current.merge(
                        TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            ),
                            lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Center,
                                trim = LineHeightStyle.Trim.None
                            ),
                            color = textColor 
                        )
                    ),
                    fontSize = 15.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Light
                )
            },
            onClick =
            {
                selected = !selected
                if (tempAlarm.weekDays[i]) {
                    tempAlarm.weekDays[i] = false
                } else
                    tempAlarm.weekDays[i] = true
            },
            colors= ButtonDefaults.buttonColors(containerColor = color),
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
        )
    }
}

@Composable
fun AlarmTitleInputBox() {
    // auto shows original first alarm name in the text field
    var text by remember {
        mutableStateOf("${tempAlarm.title}")
    }
        TextField(
            value = text,
            onValueChange = {
                text = it
                tempAlarm.title = text },
            placeholder = { Text("Add Title") },
            maxLines = 1,
            textStyle = TextStyle(
                color = Dark_Purple,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(5.dp)
        )
}

@Composable
fun HourPicker(selectedHour: Int, onHourSelected: (Int) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                NumberPicker(context).apply {
                    minValue = 1 // Minimum hour value
                    maxValue = 12 // Maximum hour value
                    value = selectedHour // Initial selected hour
                    textSize = 70f
                    textColor = 4281802289.toInt()
                    setOnValueChangedListener { _, _, newVal ->
                        onHourSelected(newVal) // Notify when hour selected
                    }
                }
            }
        )
    }
}

@Composable
fun MinutePicker(selectedMinute: Int, onMinuteSelected: (Int) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                NumberPicker(context).apply {
                    minValue = 0 // Minimum minute value
                    maxValue = 59 // Maximum minute value
                    value = selectedMinute // Initial selected minute
                    textSize = 70f
                    textColor = 4281802289.toInt()

                    setOnValueChangedListener { _, _, newVal ->
                        onMinuteSelected(newVal) // Notify when minute selected
                    }
                    setFormatter(NumberPicker.Formatter { i -> String.format("%02d", i) })
                }
            }
        )
    }
}

@Composable
fun DayPeriodPicker(selectedPeriod: Int, onPeriodSelected: (Int) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                NumberPicker(context).apply {
                    minValue = 1 // am value
                    maxValue = 2 // pm value
                    value = selectedPeriod // Initial selected period
                    textSize = 70f
                    textColor = 4281802289.toInt()
                    setOnValueChangedListener { _, _, newVal ->
                        onPeriodSelected(newVal) // Notify when period selected
                    }
                    val periods = arrayOf("AM", "PM")
                    displayedValues = periods
                }
            }
        )
    }
}


fun WeekDayConvert (weekDay: Int): String {
    var dayNumber = ""
    if (weekDay == 1)
        dayNumber = "S"
    if (weekDay == 2)
        dayNumber = "M"
    if (weekDay == 3)
        dayNumber = "T"
    if (weekDay == 4)
        dayNumber = "W"
    if (weekDay == 5)
        dayNumber = "T"
    if (weekDay == 6)
        dayNumber = "F"
    if (weekDay == 7)
        dayNumber = "S"
    return dayNumber
}
