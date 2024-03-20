package navigation

data class NavBackStackEntry(
    val navigationStack: ArrayDeque<String>,
    val destinationsStack: MutableList<Destination>,
    val arguments: NavArgValues
)