
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import ui.SearchScreen
import java.awt.Dimension
import java.awt.Toolkit

fun main() {
    application {
        Window(
            alwaysOnTop = true,
            onCloseRequest = ::exitApplication,
            state = WindowState(
                position = WindowPosition.Aligned(Alignment.BottomEnd),
                size = getPreferredWindowSize(400, 800)
            ),
            title = "Dall E"
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SearchScreen()
            }
        }
    }
}

fun getPreferredWindowSize(desiredWidth: Int, desiredHeight: Int): DpSize {
    val screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize
    val preferredWidth: Int = (screenSize.width * 0.8f).toInt()
    val preferredHeight: Int = (screenSize.height * 0.8f).toInt()
    val width: Int = if (desiredWidth < preferredWidth) desiredWidth else preferredWidth
    val height: Int = if (desiredHeight < preferredHeight) desiredHeight else preferredHeight
    return DpSize(width.dp, height.dp)
}