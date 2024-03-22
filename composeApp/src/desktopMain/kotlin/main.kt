import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.bruno13palhano.DriverFactory
import com.bruno13palhano.createDatabase

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "StockManagement") {
        App(appContainer)
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    App(appContainer)
}