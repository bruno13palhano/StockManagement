package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun NavHost(
    navController: NavController,
    startDestination: String,
    navGraph: NavGraph.() -> Unit,
) {
    val nav by remember { mutableStateOf(NavGraph(navController)) }
    navGraph(nav)

    val currentRoute by navController.currentRoute().collectAsState(initial = startDestination)

    nav.navController.navBackStackEntry.destinationsStack
        .firstOrNull { it.route == currentRoute }?.content?.let { it() }
}

class NavGraph(val navController: NavController) {
    fun addDestination(
        route: String,
        arguments: List<NamedNavArgument>,
        content: @Composable () -> Unit
    ) {
        navController.addDestination(
            Destination(
                route = route,
                arguments = arguments,
                argumentsValues = mapOf(),
                content = content
            )
        )
    }

    fun addDestination(startDestination: String, route: String) {
        navController.addDestination(startDestination = startDestination, route = route)
    }
}

inline fun NavGraph.composable(
    route: String,
    arguments: List<NamedNavArgument> = listOf(),
    crossinline content: @Composable ((NavBackStackEntry) -> Unit)
) {
    addDestination(route = route, arguments = arguments, content = { content(navController.navBackStackEntry)} )
}

inline fun NavGraph.navigation(
    startDestination: String,
    route: String,
    navGraph: NavGraph.() -> Unit,
) {
    addDestination(startDestination = startDestination, route = route)
    navGraph()
}

@Composable
fun rememberNavController() = remember { NavController() }

