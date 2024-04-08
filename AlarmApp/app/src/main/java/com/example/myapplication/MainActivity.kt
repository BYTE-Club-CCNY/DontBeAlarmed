package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.Dandelion
import com.example.myapplication.ui.theme.Dark_Purple
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.myapplication.ui.theme.Soft_Purple
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Background()
            SubHeader()
            Header()
            Column(
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxHeight(0.30f)
                    .fillMaxWidth()
                    .padding(top = 60.dp)
                    .padding(horizontal = 20.dp),
            ) {
                Text(
                    text = "Next Alarm in: ...",
                    color = Dark_Purple,
                    fontSize = 45.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Medium

                )
            }
            TransparentHeader()
        }
    }

    @Composable
    fun TransparentHeader() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (blankBox, scrollBox) = createRefs()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.30f)
                    .constrainAs(blankBox) {}
                    .background(Color.Transparent)
            ){
            }
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(scrollBox) {
                        top.linkTo(blankBox.bottom, margin = 25.dp)
                    }

            ){
                ScrollBoxes()
                ScrollBoxesSmooth()
            }
        }
    }

    @Preview
    @Composable
    private fun ScrollBoxes() {
        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .size(100.dp)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 25.dp)
        ) {
            repeat(10) {
                Text("Item $it", modifier = Modifier.padding(25.dp))
            }
        }
    }

    @Preview
    @Composable
    private fun ScrollBoxesSmooth() {
        val state = rememberScrollState()
        LaunchedEffect(Unit) { state.animateScrollTo(100) }

        Column(
            modifier = Modifier
                .background(Color(4281802289))
                .size(500.dp)
                .fillMaxWidth()
                .verticalScroll(state)
        ) {
            repeat(10) {
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

                ) {
                    Text(
                        text = "8:00",
                        fontSize = 45.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun Background() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(4281802289))
    ) {}
}

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(4294493562))
    ) {}
}
 @Composable
 fun SubHeader() {
     Column(
         modifier = Modifier
             .background(Color(4294019433))
             .fillMaxHeight(0.30f)
             .fillMaxWidth()
     ) {}
 }

val fontFamily = FontFamily(
    Font(R.font.lexend_black, FontWeight.Black),
    Font(R.font.lexend_bold, FontWeight.Bold),
    Font(R.font.lexend_extrabold, FontWeight.ExtraBold),
    Font(R.font.lexend_extralight, FontWeight.ExtraLight),
    Font(R.font.lexend_light, FontWeight.Light),
    Font(R.font.lexend_medium, FontWeight.Medium),
    Font(R.font.lexend_regular, FontWeight.Normal),
    Font(R.font.lexend_semibold, FontWeight.SemiBold),
    Font(R.font.lexend_thin, FontWeight.Thin)
)
