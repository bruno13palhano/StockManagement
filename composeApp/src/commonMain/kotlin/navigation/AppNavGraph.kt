package navigation

import AppContainer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import screens.customer.CustomerViewModel
import screens.customer.CustomersRoute
import screens.customer.CustomersViewModel
import screens.customer.EditCustomerRoute
import screens.customer.NewCustomerRoute
import screens.financial.FinancialRoute
import screens.financial.FinancialViewModel
import screens.home.HomeRoute
import screens.home.HomeViewModel
import screens.sales.EditSaleRoute
import screens.sales.NewSaleRoute
import screens.sales.SaleViewModel
import screens.sales.SalesRoute
import screens.sales.SalesViewModel

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    appContainer: AppContainer,
    onIconMenuClick: () -> Unit,
    navController: NavHostController,
    startDestination: String = Route.MAIN
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        navigation(
            startDestination = Route.HOME,
            route = Route.MAIN
        ) {
            composable(route = Route.HOME) {
                HomeRoute(
                    onIconMenuClick = onIconMenuClick,
                    onAddButtonClick = { navController.navigate(route = Route.SALES) },
                    viewModel = viewModel {
                        HomeViewModel(saleRepository = appContainer.saleRepository)
                    }
                )
            }
            composable(route = Route.SALES) {
                SalesRoute(
                    onItemClick = { id ->
                        navController.navigate(route = "${Route.EDIT_SALE}/$id")
                    },
                    onAddButtonClick = { navController.navigate(route = Route.NEW_SALE) },
                    onBackClick = { navController.navigateUp() },
                    viewModel = viewModel {
                        SalesViewModel(saleRepository = appContainer.saleRepository)
                    }
                )
            }
            composable(route = Route.NEW_SALE) {
                NewSaleRoute(
                    onDoneButtonClick = { navController.navigateUp() },
                    onBackClick = { navController.navigateUp() },
                    viewModel = viewModel {
                        SaleViewModel(
                            saleRepository = appContainer.saleRepository,
                            customerRepository = appContainer.customerRepository
                        )
                    }
                )
            }
            composable(
                route = "${Route.EDIT_SALE}/{$ITEM_ID}",
                arguments = listOf(navArgument(ITEM_ID) { type = NavType.LongType })
            ) { backStackEntry ->
                backStackEntry.arguments?.getLong(ITEM_ID)?.let { id ->
                    EditSaleRoute(
                        id = id,
                        onDoneButtonClick = { navController.navigateUp() },
                        onBackClick = { navController.navigateUp() },
                        viewModel = viewModel {
                            SaleViewModel(
                                saleRepository = appContainer.saleRepository,
                                customerRepository = appContainer.customerRepository
                            )
                        }
                    )
                }
            }
            composable(route = Route.FINANCIAL) {
                FinancialRoute(
                    viewModel = viewModel {
                        FinancialViewModel(saleRepository = appContainer.saleRepository)
                    },
                    onIconMenuClick = onIconMenuClick
                )
            }
            composable(route = Route.CUSTOMERS) {
                CustomersRoute(
                    onItemClick = { id ->
                        navController.navigate(route = "${Route.EDIT_CUSTOMER}/$id")
                    },
                    onIconMenuClick = onIconMenuClick,
                    onAddButtonClick = { navController.navigate(route = Route.NEW_CUSTOMER) },
                    viewModel = viewModel {
                        CustomersViewModel(
                            customerRepository = appContainer.customerRepository
                        )
                    }
                )
            }
            composable(route = Route.NEW_CUSTOMER) {
                NewCustomerRoute(
                    onBackClick = { navController.navigateUp() },
                    viewModel = viewModel {
                        CustomerViewModel(
                            customerRepository = appContainer.customerRepository
                        )
                    }
                )
            }
            composable(
                route = "${Route.EDIT_CUSTOMER}/{$ITEM_ID}",
                arguments = listOf(navArgument(ITEM_ID) { type = NavType.LongType })
            ) { backStackEntry ->
                backStackEntry.arguments?.getLong(ITEM_ID)?.let { id ->
                    EditCustomerRoute(
                        id = id,
                        onBackClick = { navController.navigateUp() },
                        viewModel = viewModel {
                            CustomerViewModel(
                                customerRepository = appContainer.customerRepository
                            )
                        }
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
    const val SALES = "sales_route"
    const val EDIT_SALE = "edit_sale_route"
    const val NEW_SALE = "new_sale_route"
    const val CUSTOMERS = "customers_route"
    const val EDIT_CUSTOMER = "edit_customer_route"
    const val NEW_CUSTOMER = "new_customer_route"
}