package com.example.myapplication

import android.os.Bundle
import android.widget.NumberPicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.myapplication.ui.theme.Alarm
import com.example.myapplication.ui.theme.Dark_Purple
import com.example.myapplication.ui.theme.Light_Gray
import com.example.myapplication.ui.theme.Sunset_Orange


val firstAlarm = Alarm(1, true, 1, 0)

class AlarmSettings : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Background()
            SubHeader()
            Header()
            SettingsPage()
        }
    }
}

@Preview
@Composable
fun SettingsPage () {
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
            Text(
                text = "Next Alarm in: ...",
                color = Dark_Purple,
                fontSize = 45.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium

            )
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
            Row{
                //hours
                var selectedHour by remember { mutableStateOf(1) }
                var selectedMinute by remember { mutableStateOf(0) }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color.White)
                        .width(70.dp)
                        .height(100.dp)
                ) {
                    HourPicker(
                        selectedHour = selectedHour,
                        onHourSelected = { hour ->
                            selectedHour = hour
                            firstAlarm.hour = selectedHour
                        })
//                    Text(
//                        text = "00",
//                        color = Light_Gray,
//                        fontSize = 45.sp,
//                        fontFamily = fontFamily,
//                        fontWeight = FontWeight.Light
//                    )
                }
                Text(
                    text = ":",
                    color = Light_Gray,
                    fontSize = 45.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Light)
                //minutes
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color.White)
                        .width(70.dp)
                        .height(100.dp)
                ){
                    MinutePicker(
                        selectedMinute = selectedMinute,
                        onMinuteSelected = { minute ->
                            selectedMinute = minute
                            firstAlarm.minute = selectedMinute
                        })
//                    Text(
//                        text = "00",
//                        color = Light_Gray,
//                        fontSize = 45.sp,
//                        fontFamily = fontFamily,
//                        fontWeight = FontWeight.Light)
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
            Text(
                text = "SMTWTFS",
                color = Dark_Purple,
                fontSize = 40.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold)
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
            Row{
                Text(
                    text = "Activities",
                    color = Dark_Purple,
                    fontSize = 20.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold)
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
            Row{
                Text(
                    text = "Sound",
                    color = Dark_Purple,
                    fontSize = 20.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold)
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
            Row{
                Text(
                    text = "Snooze",
                    color = Dark_Purple,
                    fontSize = 20.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun AlarmTitleInputBox() {
    // auto shows original first alarm name in the text field
    var text by remember {
        mutableStateOf("${firstAlarm.title}")
    }
        TextField(
            value = text,
            onValueChange = {
                text = it
                firstAlarm.title = text },
            placeholder = { Text("Add Title") },
            maxLines = 1,
            textStyle = TextStyle(
                color = Dark_Purple,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(5.dp)
        )
}

@Preview
@Composable

fun ScrollBoxesSmooth() {
    // Smoothly scroll 100px on first composition
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(vertical = 20.dp)
            .verticalScroll(state)
    ) {
        for(i in 1..12) {
            Text("$i", modifier = Modifier.padding(12.dp))
        }
    }
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
                }
            }
        )
    }
}