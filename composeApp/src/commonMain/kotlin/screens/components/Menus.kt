package screens.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import navigation.NavController
import navigation.Route
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import stockmanagement.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun Menu(
    drawerState: DrawerState,
    navController: NavController,
    content: @Composable () -> Unit
) {
    val menuItems = listOf(
        MenuScreen.Home,
        MenuScreen.Financial,
        MenuScreen.Customers
    )

    val navBackStackEntry by remember { mutableStateOf(navController.navBackStackEntry.navigationStack) }
    val currentItem by navController.currentRoute().collectAsState(initial = Route.HOME)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = RectangleShape
            ) {
                LazyColumn {
                    stickyHeader {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            text = stringResource(Res.string.app_name),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    items(items = menuItems, key = { item -> item.route }) { item ->
                        NavigationDrawerItem(
                            shape = RoundedCornerShape(0, 50, 50, 0),
                            icon = { Icon(imageVector = item.icon, contentDescription = null) },
                            label = { Text(stringResource(item.resourceId)) },
                            selected = item.route == currentItem,
                            onClick = {
                                if (navBackStackEntry.lastOrNull() != item.route) {
                                    navController.navigate(route = item.route)
                                }
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            modifier = Modifier
                                .padding(top = 4.dp, bottom = 4.dp, end = 8.dp)
                                .height(52.dp)
                        )
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
    data object Customers: MenuScreen(
        route = Route.CUSTOMERS,
        icon = Icons.Filled.Person,
        resourceId = Res.string.customers_label
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onDismissBottomSheet: () -> Unit,
    content: @Composable (ColumnScope.() -> Unit)
) {
    val bottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        modifier = Modifier.padding(bottom = 48.dp).fillMaxWidth(),
        onDismissRequest = onDismissBottomSheet,
        sheetState = bottomSheetState,
        content = content
    )
}

@Composable
fun MoreOptionsMenu(
    items: Array<String>,
    expanded: Boolean,
    onDismissRequest: (expanded: Boolean) -> Unit,
    onClick: (index: Int) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissRequest(false) }
    ) {
        items.forEachIndexed { index, item ->
            DropdownMenuItem(
                text = { Text(text = item) },
                onClick = {
                    onClick(index)
                    onDismissRequest(false)
                }
            )
        }
    }
}