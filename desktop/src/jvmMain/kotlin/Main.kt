import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import com.milad.dall_e.Greeting

fun main() {
    application {
        val windowState = rememberWindowState()

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "My Project"
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                GreetingView(Greeting().greet())
            }
        }
    }
}


@Composable
fun GreetingView(text: String) {
    Text(text = text)
}
