package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.Dark_Purple
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import android.media.MediaPlayer
import androidx.compose.ui.Alignment
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults.textFieldColors
import kotlin.random.Random

internal lateinit var mediaPlayer: MediaPlayer

class MathGame : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.alarm_sound_4)
            LaunchedEffect(Unit) {
                mediaPlayer.start()
            }
            LockOrientation()
            Background()
            SubHeader()
            Header()
            var count by remember { mutableStateOf(0) }
            ConstraintLayout{
                var (questionBox, textBox, submitBox) = createRefs()
                var phoneValue by remember { mutableStateOf("") }
                var num by remember { mutableStateOf(List(2) { Random.nextInt(0, 11) }) }
                var operation by remember { mutableStateOf((0..2).random()) }
                val ans = GenerateEquationAns(num[0], num[1], operation)
                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .constrainAs(questionBox) {}
                        .background(Color.Transparent)
                ) {
                    PrintGenerateEquation(num1 = num[0], num2 = num[1], operation = operation)
                }
                Box(
                    modifier = Modifier
                        .constrainAs(submitBox) {
                            top.linkTo(textBox.bottom)
                        }
                        .padding(horizontal = 5.dp)
                        .padding(vertical = 15.dp)

                ){
                    Button(
                        onClick = {
                            if (phoneValue == "$ans") {
                                if (count == 4){
                                    mediaPlayer.pause()
                                    mediaPlayer.seekTo(0)
                                    val intent = Intent(this@MathGame, MainActivity::class.java)
                                    startActivity(intent)

                                } else {
                                    count++
                                    phoneValue = ""
                                    num = List(2) { Random.nextInt(0, 13) }
                                    operation = (0..2).random()
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(4284563290),
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
                            text = "Enter",
                            color = Dark_Purple,
                            fontSize = 60.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.ExtraBold,
                        )
                    }
                }

                Text(
                    text = "  $count/5",
                    color =  Dark_Purple,
                    fontSize = 35.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.ExtraBold
                )
                TextField(
                    value = phoneValue,
                    colors = textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color(4284563290),
                        containerColor = Color(4284563290)

                    ),
                    shape = RoundedCornerShape(10.dp),
                    onValueChange = { phoneValue = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = TextStyle(
                        fontSize = 40.sp,
                        color = Dark_Purple,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.ExtraBold,
                    ),
                    modifier = Modifier
                        .constrainAs(textBox) {
                            top.linkTo(questionBox.bottom)
                        }
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(15.dp)
                )
            }
        }
    }
}
@Composable
fun GenerateEquationAns (num1: Int, num2: Int, operation:Int):Int {
    var ans = 0
    val operator = remember { listOf("+", "-", "*") }
    Box(
        modifier = Modifier
            .height(250.dp)
            .width(250.dp)
            .background(Color.Transparent)
    ){
        ans = if (operation == 0) {
            num1 + num2
        } else if (operation == 1) {
            num1 - num2
        } else {
            num1 * num2
        }
    }
    return ans
}

@Composable
fun PrintGenerateEquation (num1: Int, num2: Int, operation:Int) {
    var ans = 0
    val operator = remember { listOf("+", "-", "*") }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(250.dp)
            .width(250.dp)
            .background(Color.Transparent)
    ){
        ans = if (operation == 0) {
            num1 + num2
        } else if (operation == 1) {
            num1 -num2
        } else {
            num1 * num2
        }

        Text(
            text = "$num1 ${operator[operation]} $num2",
            color = Dark_Purple,
            fontSize = 70.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.ExtraBold,
        )
    }
}