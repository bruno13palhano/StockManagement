import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import navigation.NavController
import navigation.NavHost
import navigation.composable
import navigation.navigation
import navigation.rememberNavController
import org.jetbrains.compose.resources.ExperimentalResourceApi
import screens.HomeScreen
import screens.ItemScreen

@Composable
fun App() {
    MaterialTheme {
        Surface {
            Scaffold(
                topBar = {
                    TopAppBar(title = {})
                }
            ) {
                Column(
                    modifier = Modifier.padding(it).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "main"
                    ) {
                        navigation(startDestination = "main", route = "home") {
                            composable(route = "home") {
                                HomeScreen("home") {
                                    navController.navigate(route = "item")
                                }
                            }
                            composable(route = "item") {
                                ItemScreen("item") {
                                    navController.navigateUp()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}