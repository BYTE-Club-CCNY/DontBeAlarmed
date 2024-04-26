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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import com.example.myapplication.ui.theme.Dandelion
import com.example.myapplication.ui.theme.Soft_Purple
import androidx.compose.material3.Button
import androidx.compose.material3.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LockOrientation()
            Background()
            SubHeader()
            Header()
            ConstraintBoxes()
        }
    }
@Preview
    @Composable
    fun ConstraintBoxes() {
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
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
                    fontWeight = FontWeight.Bold
                )
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
        Column(
            modifier = Modifier
                .background(Color(4281802289))
                .height(500.dp)
                .fillMaxWidth()
                .verticalScroll(state)
        ) {
            for (i in 1..12) {
                Button(
                    onClick = {
                        val navigate = Intent(this@MainActivity, ButtonData::class.java)
                        navigate.putExtra("BUTTON_INDEX", i)
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
                                text = "$i:00 PM",
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
                                        platformStyle = PlatformTextStyle(
                                            includeFontPadding = false
                                        ),
                                        lineHeightStyle = LineHeightStyle(
                                            alignment = LineHeightStyle.Alignment.Center,
                                            trim = LineHeightStyle.Trim.None
                                        )
                                    )
                                ),
                                text = " S   M   T   W   T   F   S",
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
