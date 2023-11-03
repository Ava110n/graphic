import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.MouseInfo
import java.awt.Point
import java.awt.event.MouseEvent


@OptIn(ExperimentalTextApi::class)
@Composable
@Preview
fun App() {
    var xMin by remember { mutableStateOf(-10) }
    var xMax by remember { mutableStateOf( 10) }
    var yMinMax by remember{mutableStateOf(0f)}
    val textMeasurer = rememberTextMeasurer()
    var points by remember { mutableStateOf(mutableListOf<Point>()) }
    Canvas(modifier = Modifier.fillMaxSize().clickable(onClick ={points.add(Point(5,5))} ),
        onDraw = {
            //ось OX
            drawLine(color = Color.Black,
                start = Offset(0f, this.size.height*(1+yMinMax)/2),
                end = Offset(this.size.width, this.size.height*(1+yMinMax)/2))
            //ось OY
            drawLine(color = Color.Black,
                start = Offset(-this.size.width*xMin/(xMax-xMin), 0f),
                end = Offset(-this.size.width*xMin/(xMax-xMin), this.size.height))
            for(point in points) {
                drawCircle(
                    color = Color.Green,
                    radius = 10f,
                    center = Offset(point.x.toFloat(), point.y.toFloat())
                )
            }
            for(i in xMin .. xMax) {
                drawText(textMeasurer = textMeasurer, text = i.toString(),
                    topLeft = Offset(this.size.width*(i-xMin)/(xMax-xMin),
                        this.size.height*(1+yMinMax)/2))
            }
        })

    Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
        Row(modifier = Modifier.padding(10.dp, 10.dp).fillMaxWidth(0.7f)){
            Box(modifier = Modifier.padding(10.dp, 10.dp).fillMaxWidth(0.3f)){
                TextField(value = xMin.toString(),
                    onValueChange = { value -> xMin = value.toIntOrNull() ?:-10 })
            }
            Box(modifier = Modifier.padding(10.dp, 10.dp).fillMaxWidth(0.3f)) {
                TextField(value = xMax.toString(),
                    onValueChange = { value -> xMax = value.toIntOrNull() ?: 0 })
            }
            Box(modifier = Modifier.padding(10.dp, 10.dp).fillMaxWidth(0.3f)) {
                Text(text = "${yMinMax}", fontSize = 10.sp)
                Slider(value = yMinMax,
                    valueRange = -1f..1f,
                    steps = 9,
                    onValueChange = { yMinMax = it })
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
