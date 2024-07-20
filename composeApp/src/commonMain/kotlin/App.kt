import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import gkotlinmultiplatformpractice.composeapp.generated.resources.Res
import gkotlinmultiplatformpractice.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormatBuilder
import kotlinx.datetime.toLocalDateTime

/**
 * 跟着官网教程学习的代码
 * - [Create your multiplatform project](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-create-first-app.html)
 * - [Explore composable code](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-explore-composables.html)
 * - [Modify the project](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-modify-project.html)
 */
@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        var count by remember { mutableStateOf(0) }
        var time by remember { mutableStateOf(3) }
        var isTick by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            TickTodayDatetime(isTick)
            Button(onClick = { isTick = !isTick }) {
                Text("Click to tick or not tick")
            }
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!!!")
            }
            Button(
                onClick = {
                    GlobalScope.launch(Dispatchers.Default) {
                        for (i in 3 downTo 1) {
                            time = i
                            delay(1000L)
                        }
                        time = 0
                        count++
                    }
                },
            ) {
                Text("Delay 3s and count++")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting count=$count countdown=${time}s")
                }
            }
        }
    }
}

private var tickJob: Job? = null

@Composable
fun TickTodayDatetime(isTick: Boolean) {
    var tickCount by remember { mutableStateOf(0) }

    @Composable
    fun ClockText(tick: Int) {
        Text(
            text = "Current time is ${todayDatetime()}. isTick=$isTick tick=$tick",
            modifier = Modifier.padding(20.dp),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
        )
    }

    if (isTick) {
        ClockText(tickCount)
        tickJob?.cancel()
        tickJob = GlobalScope.launch {
            while (true) {
                delay(1000L)
                tickCount++
                println("Ticking...count=$tickCount")
            }
        }
    } else {
        ClockText(0)
        tickJob?.cancel()
        tickJob = null
    }
}

fun todayDatetime(): String {
    fun LocalDateTime.format() = "$date $hour:$minute:$second"

    val now = Clock.System.now()
    val zone = TimeZone.currentSystemDefault()
    return now.toLocalDateTime(zone).format()
}