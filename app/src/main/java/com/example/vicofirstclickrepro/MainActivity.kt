package com.example.vicofirstclickrepro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vicofirstclickrepro.ui.theme.VicoFirstClickReproTheme
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.entry.collectAsState
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollSpec
import com.patrykandpatrick.vico.compose.component.lineComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VicoFirstClickReproTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Greeting()
                    }
                }
            }
        }
    }
}

private val producer = (1..12)
    .map {
        FloatEntry(
            x = it.toFloat(),
            y = Random(it).nextDouble(500.0).toFloat(),
        )
    }
    .let { ChartEntryModelProducer(it) }

@Composable
fun Greeting() {
    val chart = columnChart(
        columns = listOf(lineComponent()),
    )
    val modelState =
        producer.collectAsState(chartKey = chart, producerKey = producer)
    Chart(
        chart = chart,
        model = modelState.value ?: producer.getModel(),
        oldModel = modelState.previousValue,
        bottomAxis = rememberBottomAxis(
            axis = null,
            tick = null,
            guideline = null,
            label = axisLabelComponent(),
        ),
        marker = marker(),
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VicoFirstClickReproTheme {
        Greeting()
    }
}
