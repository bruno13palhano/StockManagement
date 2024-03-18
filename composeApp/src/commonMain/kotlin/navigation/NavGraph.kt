package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun NavHost(
    navController: NavController,
    startDestination: String,
    navGraph: NavGraph.() -> Unit,
) {
    val nav by remember { mutableStateOf(NavGraph(navController)) }
    navGraph(nav)

    val a: String by navController.currentRoute().collectAsState(initial = "main")

    nav.navController.destinations.firstOrNull { it.route == a }?.content?.let { it() }
}

class NavGraph(val navController: NavController) {
    fun addDestination(route: String, content: @Composable () -> Unit) {
        navController.addDestination(Destination(route = route, content = content))
    }

    fun addDestination(startDestination: String, route: String) {
//        navController.addDestination(Destination(route = route, content = {}))
        if (!navController.navigationStack.contains(startDestination))
            navController.navigationStack.addLast(startDestination)
    }

    fun getStack() = navController.navigationStack
    fun getDestinations() = navController.destinations
}

inline fun NavGraph.composable(
    route: String,
    crossinline content: @Composable () -> Unit
) {
    addDestination(route = route, content = { content() })
    if (navController.navigationStack.size == 1) {
        navController.navigationStack.addLast(route)
    }
}

inline fun NavGraph.navigation(
    startDestination: String,
    route: String,
    navGraph: NavGraph.() -> Unit,
) {
    addDestination(startDestination = startDestination, route = route)
    navGraph()
}

class NavController {
    var navigationStack = ArrayDeque<String>()
    var destinations = mutableListOf<Destination>()

    fun navigate(route: String) {
        if (navigationStack.lastOrNull() != route)
            navigationStack.addLast(route)
    }

    fun navigateUp() {
        navigationStack.removeLastOrNull()
    }

    fun addDestination(destination: Destination) {
        val routes = destinations.map { it.route }

        if (!routes.contains(destination.route))
            destinations.add(destination)
    }

    fun currentRoute(): Flow<String> {
        return flow {
            while (true) {
                emit(
                    navigationStack.last()
                )
                delay(175)
            }
        }
    }
}

data class Destination(
    val route: String,
    internal val content: @Composable () -> Unit
)

@Composable
fun rememberNavController() = remember { NavController() }

