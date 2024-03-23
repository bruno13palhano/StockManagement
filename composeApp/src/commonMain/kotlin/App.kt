import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import navigation.MainNavigation
import navigation.rememberNavController
import screens.components.Menu
import screens.home.HomeViewModel
import screens.sales.SaleViewModel

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
                    navController = navController,
                    homeViewModel = HomeViewModel(appContainer.saleRepository),
                    saleViewModel = SaleViewModel(appContainer.saleRepository)
                )
            }
        }
    }
}