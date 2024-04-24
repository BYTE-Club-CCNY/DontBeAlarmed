package com.example.myapplication

import android.content.Intent
import android.media.MediaPlayer
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
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.alarm_sound_1)
    }

    @Preview
    @Composable
    fun SettingsPage() {
        ConstraintLayout {
            //header notifying next alarm time
            val (blankBox, titleBox, timeBox, optionBox, nextBox) = createRefs()
            //blankbox = Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp)
                    .constrainAs(blankBox) {}
                    .background(Color.Transparent)
                    .padding(top = 60.dp)
                    .padding(horizontal = 20.dp)
            ) {
                Column() {
                    Text(
                        text = "Next Alarm in: ...",
                        color = Dark_Purple,
                        fontSize = 45.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            //section to input title for alarm
            //title box = name of the alarm box
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .constrainAs(titleBox) {
                        top.linkTo(blankBox.bottom, margin = 10.dp)
                    }
                    .background(Color.Transparent)
            ) {
                AlarmTitleInputBox()
            }
            //section to input time values
            //time box = hour, min, am/pm
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .constrainAs(timeBox) {
                        // change to 25 margin once i figure out textfield formatting
                        top.linkTo(titleBox.bottom, margin = 10.dp)
                    }
                    .background(Color.Transparent)
            ) {
                var selectedHour by remember { mutableStateOf(1) }
                var selectedMinute by remember { mutableStateOf(0) }
                var selectedPeriod by remember { mutableStateOf(1) }
                ConstraintLayout {
                    val (hourBox, colonBox, minuteBox, timeOfDay, spacerBox) = createRefs()
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(25.dp))
                            .background(Color.White)
                            .width(85.dp)
                            .height(100.dp)
                            .constrainAs(hourBox) {}

                    ) {
                        HourPicker(
                            selectedHour = tempAlarm.hour,
                            onHourSelected = { hour ->
                                selectedHour = hour
                                tempAlarm.hour = selectedHour
                            }
                        )
                    }
                    Box(
                        modifier = Modifier
                            .constrainAs(colonBox) {}
                            .padding(13.dp)
                    ) {
                        Text(
                            text = ":",
                            color = Dandelion,
                            fontSize = 45.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(25.dp))
                            .background(Color.White)
                            .width(85.dp)
                            .height(100.dp)
                            .constrainAs(minuteBox) {}
                    ) {
                        MinutePicker(
                            selectedMinute = tempAlarm.minute,
                            onMinuteSelected = { minute ->
                                selectedMinute = minute
                                tempAlarm.minute = selectedMinute
                            }
                        )
                    }
                    Box (
                        modifier = Modifier
                            .constrainAs(spacerBox) {}
                    ){
                        Text(
                            text = "          "
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(25.dp))
                            .background(Color.White)
                            .width(85.dp)
                            .height(100.dp)
                            .constrainAs(timeOfDay) {}
                    ) {
                        DayPeriodPicker(
                            selectedPeriod = tempAlarm.period,
                            onPeriodSelected = { period ->
                                selectedPeriod = period
                                tempAlarm.period = selectedPeriod
                            }
                        )
                    }
                    createHorizontalChain(hourBox,colonBox,minuteBox,spacerBox, timeOfDay)
                }
            }
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 15.dp)
                    .constrainAs(optionBox) {
                        top.linkTo(timeBox.bottom, margin = 10.dp)
                    }
            ) {
                ConstraintLayout {
                    val (weekBox, activityBox, soundBox,  nextBox) = createRefs()
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(325.dp)
                            .height(60.dp)
                            .constrainAs(weekBox) {
                                bottom.linkTo(activityBox.top, margin = 25.dp)
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
                    }
                    // section for activity selection
                    Box(
                        modifier = Modifier
                            .width(325.dp)
                            .height(60.dp)
                            .constrainAs(activityBox) {
                                top.linkTo(weekBox.bottom, margin = 25.dp)
                            }
                            .background(Sunset_Orange)
                            .padding(all = 5.dp)
                    )
                    {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Spacer(
                                modifier = Modifier
                                    .width(15.dp)
                                    .height(60.dp)
                            )
                            Text(
                                text = "Activity",
                                color = Dark_Purple,
                                fontSize = 20.sp,
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(15.dp)
                                    .height(60.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .width(195.dp)
                                    .align(Alignment.CenterVertically), contentAlignment = Alignment.Center
                            )
                            {
                                ActivityButton()
                            }
                        }
                    }
                    // section for sound selection
                    Box(
                        modifier = Modifier
                            .width(325.dp)
                            .height(60.dp)
                            .constrainAs(soundBox) {
                                top.linkTo(activityBox.bottom, margin = 25.dp)
                            }
                            .background(Sunset_Orange)
                            .padding(all = 5.dp)
                    )
                    {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Spacer(
                                modifier = Modifier
                                    .width(15.dp)
                                    .height(60.dp)
                            )
                            Text(
                                text = "Sound",
                                color = Dark_Purple,
                                fontSize = 20.sp,
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(10.dp)
                                    .height(60.dp)
                            )
                            Box(modifier = Modifier
                                .fillMaxWidth()
                            )
                            {
                                Row(){
                                    SoundButtons()
                                }
                            }
                        }
                    }
                    // section to customize snooze duration and quantity
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(325.dp)
                            .height(60.dp)
                            .constrainAs(nextBox) {
                                top.linkTo(soundBox.bottom, margin = 10.dp)
                            }
                            .background(Color.Transparent)
                            .padding(all = 0.dp)
                    )
                    {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            BackButton()
                            Spacer(modifier = Modifier
                                .width(125.dp))
                            SaveButton()
                        }
            }
            }
            }
        }
    }

    private @Composable
    fun BackButton() {
        Button(
            content = {
                Text(
                    text = "Back",
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
                val nav = Intent(this, MainActivity::class.java)
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
                .width(80.dp))
    }

@Preview
    @Composable
    fun ActivityButton() {
    var activityText = ""
    if(tempAlarm.activities == 0){
        activityText = "New"
    }
    else
    {
        activityText = "${tempAlarm.activities}"
    }
        Button(
            content = {
                Text(
                    text = activityText,
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
        var selectedSound by remember { mutableStateOf(tempAlarm.sound) }
        for (i in 1..3) {
            var selected = selectedSound == i
            val color = if (!selected) Dark_Purple else Dandelion//off else on
            val textColor = if (!selected) Dandelion else Dark_Purple //off else on
            val abutton = Button(
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
                        color = textColor,
                        fontSize = 12.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold
                    )
                },
                onClick =
                {
                    if (mediaPlayer.isPlaying){
                        mediaPlayer.pause()
                        mediaPlayer.seekTo(0)
                    }
                    mediaPlayer.start()
                    selectedSound = i
                    tempAlarm.sound = selectedSound
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = color,
                ),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .height(40.dp)
                    .width(70.dp))

                }
        }

    override fun onDestroy() {
        super.onDestroy()
    }
}

@Preview
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
            .width(80.dp))
}
@Preview
@Composable
fun WeekDayButtons() {
    for(i in 1..7) {
        var selected by remember { mutableStateOf(tempAlarm.weekDays[i]) }
        val color = if (selected) Dark_Purple else Dandelion//colorchange
        val textColor = if (selected) Dandelion else Dark_Purple //colorchange
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
                    fontWeight = FontWeight.Medium
                )
            },
            onClick =
            {
                selected = !selected
                tempAlarm.weekDays[i] = selected
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
@Preview
@Composable
fun AlarmTitleInputBox() {
    // auto shows original first alarm name in the text field
    var text by remember {
        mutableStateOf("${tempAlarm.title}")
    }
        TextField(
            shape = RoundedCornerShape(15.dp),
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
            modifier = Modifier
                .padding(5.dp)
                .width(325.dp)
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


