import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import navigation.MainNavigation

@Composable
fun App() { MaterialTheme { Surface { MainNavigation() } } }