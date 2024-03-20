package navigation

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NavController {
    val navBackStackEntry = NavBackStackEntry(ArrayDeque(), mutableListOf(), hashMapOf())

    fun navigate(route: String, popUpInclusive: Boolean = false) {
        val params = route.split("/")
        val currentRoute = params[0]
        val argumentsValues = params.subList(1, params.size)

        val currentDestination = navBackStackEntry.destinationsStack
            .first { it.route == currentRoute }

        for (i in argumentsValues.indices) {
            navBackStackEntry.argsValues[currentDestination.arguments[i].name] = argumentsValues[i]
        }

        if (popUpInclusive) {
            while (navBackStackEntry.navStack.lastOrNull() != currentRoute)
                navBackStackEntry.navStack.removeLastOrNull()

        } else {
            if (navBackStackEntry.navStack.lastOrNull() != currentRoute)
                navBackStackEntry.navStack.addLast(currentRoute)
        }
    }

    fun navigateUp() { navBackStackEntry.navStack.removeLastOrNull() }

    fun addDestination(destination: Destination) {
        val routes = navBackStackEntry.destinationsStack.map { it.route }

        if (!routes.contains(destination.route))
            navBackStackEntry.destinationsStack.add(destination)
    }

    fun addDestination(startDestination: String, route: String) {
        if (!navBackStackEntry.navStack.contains(startDestination) && navBackStackEntry.navStack.isEmpty()) {
            navBackStackEntry.navStack.addLast(startDestination)
            navBackStackEntry.navStack.addLast(route)
        }
    }

    fun currentRoute(): Flow<String> {
        return flow {
            while (true) {
                navBackStackEntry.navStack.lastOrNull()?.let { emit(it) }
                delay(175)
            }
        }
    }
}