package screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import stockmanagement.composeapp.generated.resources.Res

@Composable
fun HomeRoute(
    onIconMenuClick: () -> Unit,
    onAddButtonClick: () -> Unit,
    viewModel: HomeViewModel
) {
    HomeScreen(
        onIconMenuClick = onIconMenuClick,
        onAddButtonClick = onAddButtonClick
    )
}

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    onIconMenuClick: () -> Unit,
    onAddButtonClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(Res.string.home_label)) },
                navigationIcon = {
                    IconButton(onClick = onIconMenuClick) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = stringResource(Res.string.drawer_menu_label)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = onAddButtonClick) {
                Icon(
                    imageVector = Icons.Filled.ShoppingBag,
                    contentDescription = stringResource(Res.string.sales_label)
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = stringResource(Res.string.sales_label)
                )
            }
        }
    ) {

    }
}