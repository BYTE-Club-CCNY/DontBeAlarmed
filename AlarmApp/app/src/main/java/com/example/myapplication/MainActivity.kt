package com.example.myapplication

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.myapplication.ui.theme.Soft_Purple
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.myapplication.data.Alarm
import com.example.myapplication.data.AlarmDatabase
import com.example.myapplication.data.AlarmEvent
import com.example.myapplication.data.AlarmState

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AlarmDatabase::class.java,
            "alarm.db"
        ).build()
    }

    private val viewModel by viewModels<AlarmViewModel>(
        factoryProducer = {
            @Suppress("UNCHECKED_CAST")
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AlarmViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.state.collectAsState()
            AlarmScreen(state = state, onEvent = viewModel::onEvent)
        }
    }
    @Composable
    fun AlarmScreen(
        state: AlarmState,
        onEvent: (AlarmEvent) -> Unit
    ){
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    onEvent(AlarmEvent.ShowAlarm)
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Alarm")
                }
            }
        ) { padding ->
            if(state.isAddingAlarm) {
                AddAlarmDialog(state = state, onEvent = onEvent)
            }
            LazyColumn(
                contentPadding = padding,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                items(state.alarms) {alarm ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Text(
                            text = alarm.title
                        )
                        IconButton(onClick = {
                            onEvent(AlarmEvent.DeleteAlarm(alarm))
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete alarm"
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                        }
                    }
                }
            }

        }
    }
@Composable
fun AddAlarmDialog(
    state: AlarmState,
    onEvent: (AlarmEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {
            onEvent(AlarmEvent.HideAlarm)
        },
        title = {
            Text(text = "Add alarm")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.title,
                    onValueChange = {
                        onEvent(AlarmEvent.SetTitle(it))
                    },
                    label = {
                        Text(text = "Title")
                    }
                )
                TextField(
                    value = state.activity,
                    onValueChange = {
                        onEvent(AlarmEvent.SetActivity(it))
                    },
                    label = {
                        Text(text = "What activity would you like to do when you wake up?")
                    }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onEvent(AlarmEvent.SaveAlarm)
            }) {
                Text(text = "Save")
            }
        },
        dismissButton = {
            Button(onClick = {
                onEvent(AlarmEvent.HideAlarm)
            }) {
                Text(text = "Cancel")
            }
        },
        modifier = modifier
    )
}


/*@Composable
fun TransparentHeader() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.30f)
    ) {
        val (blankBox, alarmButton1, alarmButton2) = createRefs()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .constrainAs(blankBox) {}
        ) {}
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
                .padding(top = 35.dp)
                .height(125.dp)
                .fillMaxWidth()
                .constrainAs(alarmButton1) {
                    top.linkTo(blankBox.bottom)
                    bottom.linkTo(alarmButton2.top)
                }

        ) {
            Text(
                text = "8:00",
                fontSize = 45.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold
            )
        }
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
                .padding(top = 25.dp)
                .height(125.dp)
                .fillMaxWidth()
                .constrainAs(alarmButton2) {
                    top.linkTo(alarmButton1.bottom)
                }

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
 */
/*
    @Preview
    @Composable
    private fun ScrollBoxes() {
        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .size(100.dp)
                .verticalScroll(rememberScrollState())
        ) {
            repeat(10) {
                Text("Item $it", modifier = Modifier.padding(2.dp))
            }
        }
    }

    @Preview
    @Composable
    private fun ScrollBoxesSmooth() {
        // Smoothly scroll 100px on first composition
        val state = rememberScrollState()
        LaunchedEffect(Unit) { state.animateScrollTo(100) }

        Column(
            modifier = Modifier
                .background(Color(4281802289))
                .size(250.dp)
                .padding(horizontal = 25.dp)
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
                        .height(125.dp)
                        .fillMaxWidth()

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
*/

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
}
