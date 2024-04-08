package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.Dandelion
import com.example.myapplication.ui.theme.Dark_Purple
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setContent {
                Background()
                SubHeader()
                Header()
            }
        }
    }
}

@Composable
fun Background(){
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(4281802289))
    )
    {}
}

@Composable
fun Header(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color(4294493562))
    )
    {}
}

@Composable
fun SubHeader(){
    Column(
        modifier = Modifier
            .background(Color(4294019433))
            .fillMaxHeight(0.36f)
            .fillMaxWidth()
            .padding(top = 100.dp)
            .padding(horizontal = 20.dp),
    )
    {
        Text(
            text = "Next Alarm in: ...",
            color = Dark_Purple,
            fontSize = 50.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium

        )
    }
}

val fontFamily = FontFamily(
   Font(R.font.lexend_medium, FontWeight.Medium)
)