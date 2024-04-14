package com.example.myapplication

import android.content.Intent
import android.graphics.Color.parseColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.Dark_Purple
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.widget.ConstraintSet
import com.example.myapplication.ui.theme.Light_Gray
import com.example.myapplication.ui.theme.Soft_Purple
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import com.example.myapplication.ui.theme.Alarm
import androidx.compose.ui.text.input.TextFieldValue
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
                .height(80.dp)
                .constrainAs(titleBox) {
                    top.linkTo(blankBox.bottom, margin = 25.dp)
                }
                .background(Color.Transparent)
                .padding(all = 5.dp)
        ) {
            AlarmTitleInputBox()
        }
        //section to input time values
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(100.dp)
                .constrainAs(timeBox) {
                    top.linkTo(titleBox.bottom, margin = 25.dp)
                }
                .background(Color.Transparent)
                .padding(all = 5.dp)
        )
        {
            Row{
                //hours
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color.White)
                        .width(70.dp)
                        .height(100.dp)
                ) {
                    Text(
                        text = "00",
                        color = Light_Gray,
                        fontSize = 45.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Light
                    )
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
                    Text(
                        text = "00",
                        color = Light_Gray,
                        fontSize = 45.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Light)
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
    var text by remember { mutableStateOf("") }

        TextField(
            value = text,
            onValueChange = {
                text = it
                firstAlarm.title = text },
            placeholder = { Text("Add Title") },
            maxLines = 1,
            textStyle = TextStyle(color = Dark_Purple, fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(5.dp)
        )
}
