package navigation

import androidx.compose.runtime.Composable
import screens.home.HomeRoute
import screens.home.HomeViewModel
import screens.sales.EditSaleRoute
import screens.sales.NewSaleRoute
import screens.sales.SaleViewModel

@Composable
fun MainNavigation(
    homeViewModel: HomeViewModel,
    saleViewModel: SaleViewModel,
    navController: NavController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Route.MAIN
    ) {
        navigation(startDestination = Route.MAIN, route = Route.HOME) {
            composable(route = Route.HOME) {
                HomeRoute(
                    onItemClick =  { id ->
                        navController.navigate(route = "${Route.EDIT_SALE}/$id")
                    },
                    onIconMenuClick = {},
                    onAddButtonClick = { navController.navigate(route = Route.NEW_SALE) },
                    viewModel = homeViewModel
                )
            }
            composable(route = Route.NEW_SALE) {
                NewSaleRoute(
                    onDoneButtonClick = { navController.navigateUp() },
                    onBackClick = { navController.navigateUp() },
                    viewModel = saleViewModel
                )
            }
            composable(
                route = Route.EDIT_SALE,
                arguments = listOf(navArgument(ITEM_ID) { setType(type = NavType.LongType) })
            ) { backStackEntry ->
                backStackEntry.arguments.getLong(ITEM_ID)?.let { id ->
                    EditSaleRoute(
                        id = id,
                        onDoneButtonClick = { navController.navigateUp() },
                        onBackClick = { navController.navigateUp() },
                        viewModel = saleViewModel
                    )
                }
            }
        }
    }
}

private const val ITEM_ID = "item_id"

private object Route {
    const val MAIN = "main_route"
    const val HOME = "home_route"
    const val EDIT_SALE = "edit_sale_route"
    const val NEW_SALE = "new_sale_route"
}