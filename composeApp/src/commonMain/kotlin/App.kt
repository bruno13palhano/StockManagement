import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import navigation.MainNavigation
import screens.home.HomeViewModel
import screens.sales.SaleViewModel

@Composable
fun App(appContainer: AppContainer) {
    MaterialTheme {
        Surface {
            MainNavigation(
                homeViewModel = HomeViewModel(appContainer.saleRepository),
                saleViewModel = SaleViewModel(appContainer.saleRepository)
            )
        }
    }
}