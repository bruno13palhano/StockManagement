package navigation

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.HomeScreen
import screens.ItemScreen
import stockmanagement.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainNavigation(navController: NavController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Route.MAIN
    ) {
        navigation(startDestination = Route.MAIN, route = Route.HOME) {
            composable(route = Route.HOME) {
                HomeScreen(
                    title = stringResource(Res.string.home_label),
                    onItemClick =  { navController.navigate(route = Route.ITEM) },
                    onIconMenuClick = {

                    }
                )
            }
            composable(route = Route.ITEM) {
                ItemScreen(
                    title = stringResource(Res.string.item_label),
                    onButtonClick = {
                        navController.navigate(route = Route.HOME2)
                    },
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
        navigation(startDestination = Route.MAIN2, route = Route.HOME2) {
            composable(route = Route.HOME2) {
                HomeScreen(
                    title = "home2",
                    onItemClick = { navController.navigate(route = Route.ITEM2) },
                    onIconMenuClick = { navController.navigateUp() }
                )
            }
            composable(route = Route.ITEM2) {
                ItemScreen(
                    title = "item2",
                    onButtonClick = {
                        navController.navigate(route = Route.HOME, popUpInclusive = true)
                    },
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
    }
}

object Route {
    const val MAIN = "main_route"
    const val HOME = "home_route"
    const val ITEM = "item_route"
    const val MAIN2 = "main2_route"
    const val HOME2 = "home2_route"
    const val ITEM2 = "item2_route"
}