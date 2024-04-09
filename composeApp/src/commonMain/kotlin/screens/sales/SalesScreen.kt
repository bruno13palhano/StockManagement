package screens.sales

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import model.Sale
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import stockmanagement.composeapp.generated.resources.Res

@Composable
fun SalesRoute(
    onItemCLick: (id: Long) -> Unit,
    onAddButtonClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: SalesViewModel
) {
    val sales by viewModel.sales.collectAsState()

    LaunchedEffect(key1 = Unit) { viewModel.getSales() }

    SalesScreen(
        sales = sales,
        onItemCLick = onItemCLick,
        onAddButtonClick = onAddButtonClick,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
private fun SalesScreen(
    sales: List<Sale>,
    onItemCLick: (id: Long) -> Unit,
    onAddButtonClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(Res.string.sales_label)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
            items(items = sales, key = { sale -> sale.id }) { sale ->
                ListItem(
                    modifier = Modifier.clickable { onItemCLick(sale.id) },
                    headlineContent = { Text(text = sale.productName) }
                )
            }
        }
    }
}