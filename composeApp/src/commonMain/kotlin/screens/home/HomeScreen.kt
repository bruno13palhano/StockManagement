package screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import data.SaleLocalData
import data.SaleRepository
import model.Sale
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import stockmanagement.composeapp.generated.resources.Res

@Composable
fun HomeRoute(
    onItemClick: (id: Long) -> Unit,
    onIconMenuClick: () -> Unit,
    onAddButtonClick: () -> Unit,
    viewModel: HomeViewModel = HomeViewModel(
        saleRepository = SaleRepository(data = SaleLocalData())
    )
) {
    val sales by viewModel.sales.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchData()
    }

    HomeScreen(
        sales = sales,
        onItemClick = onItemClick,
        onIconMenuClick = onIconMenuClick,
        onAddButtonClick = onAddButtonClick
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalResourceApi::class)
@Composable
private fun HomeScreen(
    sales: List<Sale>,
    onItemClick: (id: Long) -> Unit,
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
                            contentDescription = stringResource(Res.string.navigate_back_label)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddButtonClick) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(Res.string.add_button_label)
                )
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(items = sales.toList(), key = { sale -> sale.id }) { sale ->
                ListItem(modifier = Modifier
                    .clickable {
                        onItemClick(sale.id)
                    }
                ) {
                    Text(text = sale.productName)
                }
            }
        }
    }
}