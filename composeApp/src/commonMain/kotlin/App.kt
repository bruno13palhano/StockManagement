import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.HomeScreen
import screens.ItemScreen
import stockmanagement.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        Surface {
            Scaffold(
                topBar = {
                    TopAppBar(title = {})
                }
            ) {
                Column(
                    modifier = Modifier.padding(it).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var screenState by remember { mutableStateOf<Screen>(Screen.Home) }

                    when (screenState) {
                        Screen.Home -> {
                            HomeScreen(title = stringResource(Res.string.home_label)) {
                                screenState = Screen.Item
                            }
                        }
                        Screen.Item -> {
                            ItemScreen(title = stringResource(Res.string.item_label)) {
                                screenState = Screen.Home
                            }
                        }
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    data object Home: Screen(route = "home")
    data object Item: Screen(route = "item")
}