package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Background()
            SubHeader()
            Header()
            TransparentHeader()
            SubHeaderText()
        }
    }
    // import app icon
    @Composable
    fun TransparentHeader() {
        ConstraintLayout {
            //add a button creating a new alarm
            val (blankBox, scrollBox) = createRefs()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp)
                    .constrainAs(blankBox) {}
                    .background(Color.Transparent)
            ) {}
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(scrollBox) {
                        top.linkTo(blankBox.bottom, margin = 25.dp)
                    }

            ) {
                ScrollButtons()
            }
        }
    }
    @Preview
    @Composable
    fun ScrollButtons() {
        val state = rememberScrollState()
        LaunchedEffect(Unit) { state.animateScrollTo(10) }
        Column(
            modifier = Modifier
                .background(Color(4281802289))
                .size(500.dp)
                .fillMaxWidth()
                .verticalScroll(state)
        ) {
            for(i in 1..12) {
                Button(
                    onClick = {
                        val navigate = Intent(this@MainActivity, AlarmSettings::class.java)
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
                ) {//horizontally constraint
                    ConstraintLayout {
                        val (alarmText, onOffButton) = createRefs()
                        Text(
                            text = "$i:00",
                            fontSize = 45.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    OnOffButton()
                }
            }
        }
    }
}
@Preview
@Composable
fun OnOffButton() {
    var checked by remember { mutableStateOf(true) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        },
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
private fun SubHeaderText() {
    Column(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxHeight(0.30f)
            .fillMaxWidth()
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
}