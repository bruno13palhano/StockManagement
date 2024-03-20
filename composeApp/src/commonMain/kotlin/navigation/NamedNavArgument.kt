package navigation

fun navArgument(
    name: String,
    builder: NavArgument.Builder.() -> Unit
): NamedNavArgument {
    val navArgument = NavArgument.Builder()
    builder.invoke(navArgument)

    return NamedNavArgument(name)
}

class NamedNavArgument internal constructor(val name: String)