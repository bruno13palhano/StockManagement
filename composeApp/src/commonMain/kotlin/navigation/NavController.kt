package navigation

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NavController(val navBackStackEntry: NavBackStackEntry) {

    fun navigate(route: String, popUpInclusive: Boolean = false) {
        val params = route.split("/")
        val currentRoute = params[0]
        val argumentsValues = params.subList(1, params.size)

        val currentDestination = navBackStackEntry.destinationsStack
            .first { it.route == currentRoute }

        for (i in argumentsValues.indices) {
            navBackStackEntry.arguments.values[currentDestination.arguments[i].name] =
                NamedNavArgument(argumentsValues[i], currentDestination.arguments[i].argument)
        }

        if (popUpInclusive) {
            while (navBackStackEntry.navigationStack.lastOrNull() != currentRoute)
                navBackStackEntry.navigationStack.removeLastOrNull()

        } else {
            if (navBackStackEntry.navigationStack.lastOrNull() != currentRoute)
                navBackStackEntry.navigationStack.addLast(currentRoute)
        }
    }

    fun navigateUp() { navBackStackEntry.navigationStack.removeLastOrNull() }

    fun addDestination(destination: Destination) {
        val routes = navBackStackEntry.destinationsStack.map { it.route }

        if (!routes.contains(destination.route))
            navBackStackEntry.destinationsStack.add(destination)
    }

    fun addDestination(startDestination: String, route: String) {
        if (!navBackStackEntry.navigationStack.contains(startDestination) && navBackStackEntry.navigationStack.isEmpty()) {
            navBackStackEntry.navigationStack.addLast(startDestination)
            navBackStackEntry.navigationStack.addLast(route)
        }
    }

    fun currentRoute(): Flow<String> {
        return flow {
            while (true) {
                navBackStackEntry.navigationStack.lastOrNull()?.let { emit(it) }
                delay(175)
            }
        }
    }
}