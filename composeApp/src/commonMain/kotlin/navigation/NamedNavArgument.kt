package navigation

fun navArgument(
    name: String,
    builder: NavArgument.Builder.() -> Unit
): NamedNavArgument = NamedNavArgument(name, NavArgument.Builder().apply(builder).build())

class NamedNavArgument internal constructor(
    val name: String,
    val argument: NavArgument
) {
    operator fun component1(): String = name
    operator fun component2(): NavArgument = argument
}