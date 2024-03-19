package navigation

import androidx.compose.runtime.Composable

data class Destination(
    val route: String,
    val arguments: List<NamedNavArgument>,
    val argumentsValues: Map<String, String>,
    internal val content: @Composable () -> Unit
)