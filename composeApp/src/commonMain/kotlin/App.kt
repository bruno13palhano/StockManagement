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
import screens.customer.CustomerViewModel
import screens.customer.CustomersViewModel
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
                    saleViewModel = SaleViewModel(appContainer.saleRepository, appContainer.customerRepository),
                    customerViewModel = CustomerViewModel(appContainer.customerRepository),
                    customersViewModel = CustomersViewModel(appContainer.customerRepository)
                )
            }
        }
    }
}