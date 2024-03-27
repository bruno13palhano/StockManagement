import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import navigation.MainNavigation
import navigation.rememberNavController
import screens.components.Menu

@Composable
fun App(appContainer: AppContainer) {
    MaterialTheme {
        Surface {
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val navController = rememberNavController()
            val scope = rememberCoroutineScope()

            Menu(drawerState, navController) {
                MainNavigation(
                    onIconMenuClick = {
                        scope.launch {
                            if (drawerState.isClosed)
                                drawerState.open()
                        }
                    },
                    appContainer = appContainer,
                    navController = navController
                )
            }
        }
    }
}