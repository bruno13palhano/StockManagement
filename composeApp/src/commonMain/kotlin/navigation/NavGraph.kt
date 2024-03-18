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

    val currentRoute by navController.currentRoute().collectAsState(initial = startDestination)

    nav.navController.destinations.firstOrNull { it.route == currentRoute }?.content?.let { it() }
}

class NavGraph(val navController: NavController) {
    fun addDestination(route: String, content: @Composable () -> Unit) {
        navController.addDestination(Destination(route = route, content = content))
    }

    fun addDestination(startDestination: String, route: String) {
        navController.addDestination(startDestination = startDestination, route = route)
    }
}

inline fun NavGraph.composable(
    route: String,
    crossinline content: @Composable () -> Unit
) = addDestination(route = route, content = { content() })

inline fun NavGraph.navigation(
    startDestination: String,
    route: String,
    navGraph: NavGraph.() -> Unit,
) {
    addDestination(startDestination = startDestination, route = route)
    navGraph()
}

class NavController {
    private var navigationStack = ArrayDeque<String>()
    var destinations = mutableListOf<Destination>()
        private set

    fun navigate(route: String, popUpInclusive: Boolean = false) {
        if (popUpInclusive) {
            while (navigationStack.lastOrNull() != route)
                navigationStack.removeLastOrNull()

        } else {
            if (navigationStack.lastOrNull() != route)
                navigationStack.addLast(route)
        }
    }

    fun navigateUp() = navigationStack.removeLastOrNull()

    fun addDestination(destination: Destination) {
        val routes = destinations.map { it.route }

        if (!routes.contains(destination.route))
            destinations.add(destination)
    }

    fun addDestination(startDestination: String, route: String) {
        if (!navigationStack.contains(startDestination) && navigationStack.isEmpty()) {
            navigationStack.addLast(startDestination)
            navigationStack.addLast(route)
        }
    }

    fun currentRoute(): Flow<String> {
        return flow {
            while (true) {
                navigationStack.lastOrNull()?.let { emit(it) }
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

