package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.NumberPicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.myapplication.database.clearJson
import com.example.myapplication.database.logAlarm
import com.example.myapplication.database.readData
import com.example.myapplication.database.tempAlarm
import com.example.myapplication.ui.theme.Dandelion
import com.example.myapplication.ui.theme.Dark_Purple
import com.example.myapplication.ui.theme.Sunset_Orange
import kotlinx.coroutines.delay
import java.util.Calendar

var temp = tempAlarm( "", "00", "00", "AM", true, 1, 1,
    mutableMapOf(
        "Sun" to false,
        "Mon" to false,
        "Tue" to false,
        "Wed" to false,
        "Thu" to false,
        "Fri" to false,
        "Sat" to false
    ))
var copyAlarm = temp
var alarmid = -1
var backtext = ""

class AlarmSettings : ComponentActivity() {

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
                        if (this == 0) "12" else if (this.toString().length == 1) "0$this" else "$this"
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

            val buttonIndex = intent.getIntExtra("BUTTON_INDEX", -1)
            alarmid = buttonIndex
            if (alarmid != -1){
                backtext = "Delete"
            }
            else {
                backtext = "Back"
            }
            SettingsPage(hour = hour, minute = minute, amOrPm = amOrPm, buttonID = buttonIndex)
        }
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.alarm_sound_1)
    }

    @Composable
    fun SettingsPage (
        hour: String,
        minute: String,
        amOrPm: String,
        buttonID: Int
    ) {
        val alarms = remember { readData(this) }
        if (buttonID != -1) {
            // If the index is valid and the alarms list is not null
            alarms?.let { alarmsList ->
                if (buttonID < alarmsList.size) {
                    // If the index is within the range of the list
                    copyAlarm = alarmsList[buttonID]
                }
            }
        }
        else{
            copyAlarm = temp
        }
        ConstraintLayout {
            val context = LocalContext.current
            //header notifying next alarm time
            val (blankBox, titleBox, timeBox, optionBox) = createRefs()
            //blankbox = Header
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp)
                    .padding(vertical = 40.dp)
                    .constrainAs(blankBox) {}
                    .background(Color.Transparent)
            ) {
                DigitalClockComponent(if (hour == "00") "12" else hour, minute, amOrPm)
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
                var selectedHour by remember { mutableStateOf(copyAlarm.hour.toIntOrNull()!!) }
                var selectedMinute by remember { mutableStateOf(copyAlarm.minute.toIntOrNull()!!) }
                var selectedMeridiem by remember { mutableStateOf(meridiemConvertToInt(copyAlarm.meridiem)) }
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
                            selectedHour = copyAlarm.hour.toIntOrNull()!!,
                            onHourSelected = { hour ->
                                selectedHour = hour
                                copyAlarm.hour = selectedHour.toString()
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
                            selectedMinute = copyAlarm.minute.toIntOrNull()!!,
                            onMinuteSelected = { minute ->
                                selectedMinute = minute
                                copyAlarm.minute = selectedMinute.toString()
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
                        MeridiemPicker(
                            selectedPeriod = meridiemConvertToInt(copyAlarm.meridiem),
                            onPeriodSelected = { period ->
                                selectedMeridiem = period
                                copyAlarm.meridiem = meridiemConvertToString(selectedMeridiem)
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
                                    .width(200.dp)
                                    .align(Alignment.CenterVertically), contentAlignment = Alignment.Center
                            )
                            {
                                Row (){
                                    ActivityButtons()
                                }
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
                                .fillMaxWidth(),
                                contentAlignment = Alignment.Center
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
                            BackButton(context)
                            Spacer(modifier = Modifier
                                .width(125.dp))
                            SaveButton(context, buttonID)
                        }
                    }
                }
            }
        }
    }


    private @Composable
    fun BackButton(context: Context) {
        val alarms = remember { readData(context) }
        Button(
            content = {
                Text(
                    text = backtext,
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
                if (alarmid != -1) {
                    clearJson(context)
                    if (alarms != null) {
                        alarms.forEachIndexed { index, alarm ->
                            if (alarmid == index) {
                                return@forEachIndexed
                            }
                            logAlarm(context, alarm)
                        }
                    }
                }
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
    fun ActivityButtons() {
        var selectedActivity by remember { mutableStateOf(copyAlarm.gameType) }
        for (i in 1..2) {
            var selected = selectedActivity == i
            val color = if (!selected) Dark_Purple else Dandelion//off else on
            val textColor = if (!selected) Dandelion else Dark_Purple //off else on
            val activityText = if (i == 1) "None" else "Math"
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
                        color = textColor,
                        fontSize = 15.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold
                    )
                },
                onClick =
                {
                    selectedActivity = i
                    copyAlarm.gameType = selectedActivity
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = color,
                ),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .height(40.dp)
                    .width(100.dp)
                    .padding(horizontal = 10.dp)
            )
        }
    }
}

    @Composable
    fun SoundButtons() {
        var selectedSound by remember { mutableStateOf(copyAlarm.sound) }
        for (i in 1..2) {
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
                        fontSize = 15.sp,
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
                    copyAlarm.sound = selectedSound
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = color,
                ),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .height(40.dp)
                    .width(100.dp)
                    .padding(horizontal = 10.dp)
            )

        }
    }
@Composable
fun SaveButton(context: Context, buttonID: Int) { // Pass AlarmDBHelper instance
    val alarms = remember { readData(context) }
    Button(
        onClick = {

            // Your logic to save the alarm
            // Assuming firstAlarm and copyAlarm are defined elsewhere
            if (buttonID != -1 && alarms != null) {
                clearJson(context)
                alarms.forEachIndexed { index, alarm ->
                    if (buttonID == index) {
                        logAlarm(context, copyAlarm)
                        return@forEachIndexed
                    }
                    logAlarm(context, alarm)
                }
            }
            else{
                logAlarm(context, copyAlarm)
            }
            val nav = Intent(context, MainActivity::class.java)
            context.startActivity(nav)
        },
        modifier = Modifier
            .height(40.dp)
            .width(80.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Dandelion, // Change color as per your requirement
            contentColor = Color.Black
        )
    ) {
        Text(
            text = "Save",
            style = TextStyle(
                fontSize = 15.sp,
                color = Dark_Purple
            )
        )
    }
}
@Preview
@Composable
fun WeekDayButtons() {
    for ((day, status) in copyAlarm.dayOfWeek) {
        var selected by remember { mutableStateOf(status) }
        val color = if (selected) Dandelion else Dark_Purple//colorchange
        val textColor = if (selected) Dark_Purple else Dandelion //colorchange
        Button(
            content = {
                Text(
                    text = WeekDayToLetterConvert(day),
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
                copyAlarm.dayOfWeek[day] = selected
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
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AlarmTitleInputBox() {
    // auto shows original first alarm name in the text field
    var text by remember {
        mutableStateOf("${copyAlarm.title}")
    }
        TextField(
            shape = RoundedCornerShape(15.dp),
            value = text,
            onValueChange = {
                text = it
                copyAlarm.title = text },
            placeholder = { Text("${copyAlarm.title}") },
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 25.sp,
                color = Dark_Purple,
                fontFamily = fontFamily,
                fontWeight = FontWeight.ExtraBold,
            ),
            modifier = Modifier
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
                    textSize = 75f
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
                    textSize = 75f
                    textColor = 4281802289.toInt()

                    setOnValueChangedListener { _, _, newVal ->
                        onMinuteSelected(newVal) // Notify when minute selected
                    }
                    setFormatter({ i -> String.format("%02d", i) })
                }
            }
        )
    }
}

@Composable
fun MeridiemPicker(selectedPeriod: Int, onPeriodSelected: (Int) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                NumberPicker(context).apply {
                    minValue = 1 // am value
                    maxValue = 2 // pm value
                    value = selectedPeriod // Initial selected period
                    textSize = 75f
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


fun WeekDayToLetterConvert (weekDay: String): String {
    var dayLetter = ""
    if (weekDay == "Sun")
        dayLetter = "S"
    if (weekDay == "Mon")
        dayLetter = "M"
    if (weekDay == "Tue")
        dayLetter = "T"
    if (weekDay == "Wed")
        dayLetter = "W"
    if (weekDay == "Thu")
        dayLetter = "T"
    if (weekDay == "Fri")
        dayLetter = "F"
    if (weekDay == "Sat")
        dayLetter = "S"
    return dayLetter
}

fun meridiemConvertToInt(meridiem: String): Int {
    if (meridiem == "AM"){
        return 1
    }
    if (meridiem == "PM"){
        return 2
    }
    else {
        return -1
    }
}

fun meridiemConvertToString(meridiem: Int): String {
    if (meridiem == 1){
        return "AM"
    }
    if (meridiem == 2){
        return "PM"
    }
    else {
        return ""
    }
}
