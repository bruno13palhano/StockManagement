package screens.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DrawerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Insights
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import navigation.NavController
import navigation.Route
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import stockmanagement.composeapp.generated.resources.Res

@OptIn(
    ExperimentalMaterialApi::class, ExperimentalResourceApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun Menu(
    drawerState: DrawerState,
    navController: NavController,
    content: @Composable () -> Unit
) {
    val menuItems = listOf(
        MenuScreen.Home,
        MenuScreen.Financial
    )

    val navBackStackEntry by remember { mutableStateOf(navController.navBackStackEntry.navigationStack) }
    val currentItem by navController.currentRoute().collectAsState(initial = Route.HOME)
    val scope = rememberCoroutineScope()

    ModalDrawer(
        drawerState = drawerState,
        drawerShape = DrawerMenuShape(widthOffset = 0.dp, scale = .7F),
        drawerContent = {
            LazyColumn {
                stickyHeader {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        text = stringResource(Res.string.app_name),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.h6
                    )
                }
                items(items = menuItems, key = { item -> item.route }) { item ->
                    ListItem(
                        modifier = Modifier
                            .background(
                                color = if (currentItem == item.route) {
                                    MaterialTheme.colors.primary
                                } else {
                                    MaterialTheme.colors.surface
                                }
                            )
                            .clickable {
                                if (navBackStackEntry.lastOrNull() != item.route) {
                                    navController.navigate(route = item.route)
                                }
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = stringResource(item.resourceId)
                            )
                        }
                    ) {
                        Text(text = stringResource(item.resourceId))
                    }
                }
            }
        },
        content = content
    )
}

@OptIn(ExperimentalResourceApi::class)
sealed class MenuScreen(val route: String, val icon: ImageVector, val resourceId: StringResource) {
    data object Home: MenuScreen(
        route = Route.HOME,
        icon = Icons.Filled.Home,
        resourceId = Res.string.home_label
    )
    data object Financial: MenuScreen(
        route = Route.FINANCIAL,
        icon = Icons.Filled.Insights,
        resourceId = Res.string.financial_label
    )
}

private class DrawerMenuShape(
    private val widthOffset: Dp,
    private val scale: Float
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(
            Rect(
                Offset.Zero,
                Offset(
                    size.width * scale + with(density) { widthOffset.toPx() },
                    size.height
                )
            )
        )
    }
}