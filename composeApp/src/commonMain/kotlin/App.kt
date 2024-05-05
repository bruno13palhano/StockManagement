import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import navigation.AppNavGraph
import screens.components.Menu
import theme.StockManagementTheme

@Composable
fun App(appContainer: AppContainer) {
    StockManagementTheme {
        Surface {
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val navController = rememberNavController()
            val scope = rememberCoroutineScope()

            Menu(drawerState, navController) {
                AppNavGraph(
                    onIconMenuClick = {
                        scope.launch {
                            if (drawerState.isClosed)
                                drawerState.open()
                        }
                    },
                    appContainer = appContainer,
                    navController = navController,
                )
            }
        }
    }
}