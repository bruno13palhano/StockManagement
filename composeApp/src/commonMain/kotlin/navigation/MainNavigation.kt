package navigation

import androidx.compose.runtime.Composable
import screens.customer.CustomerViewModel
import screens.customer.CustomersRoute
import screens.customer.CustomersViewModel
import screens.customer.EditCustomerRoute
import screens.customer.NewCustomerRoute
import screens.financial.FinancialRoute
import screens.home.HomeRoute
import screens.home.HomeViewModel
import screens.sales.EditSaleRoute
import screens.sales.NewSaleRoute
import screens.sales.SaleViewModel

@Composable
fun MainNavigation(
    onIconMenuClick: () -> Unit,
    homeViewModel: HomeViewModel,
    saleViewModel: SaleViewModel,
    customerViewModel: CustomerViewModel,
    customersViewModel: CustomersViewModel,
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
                    onIconMenuClick = onIconMenuClick,
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
            composable(route = Route.FINANCIAL) {
                FinancialRoute(onIconMenuClick = onIconMenuClick)
            }
            composable(route = Route.CUSTOMERS) {
                CustomersRoute(
                    onItemClick = { id ->
                        navController.navigate(route = "${Route.EDIT_CUSTOMER}/$id")
                    },
                    onIconMenuClick = onIconMenuClick,
                    onAddButtonClick = { navController.navigate(route = Route.NEW_CUSTOMER) },
                    viewModel = customersViewModel
                )
            }
            composable(route = Route.NEW_CUSTOMER) {
                NewCustomerRoute(
                    onBackClick = { navController.navigateUp() },
                    viewModel = customerViewModel
                )
            }
            composable(
                route = Route.EDIT_CUSTOMER,
                arguments = listOf(navArgument(ITEM_ID) { setType(type = NavType.LongType) })
            ) { backStackEntry ->
                backStackEntry.arguments.getLong(ITEM_ID)?.let { id ->
                    EditCustomerRoute(
                        id = id,
                        onBackClick = { navController.navigateUp() },
                        viewModel = customerViewModel
                    )
                }
            }
        }
    }
}

private const val ITEM_ID = "item_id"

object Route {
    const val MAIN = "main_route"
    const val HOME = "home_route"
    const val FINANCIAL = "financial_route"
    const val EDIT_SALE = "edit_sale_route"
    const val NEW_SALE = "new_sale_route"
    const val CUSTOMERS = "customers_route"
    const val EDIT_CUSTOMER = "edit_customer_route"
    const val NEW_CUSTOMER = "new_customer_route"
}