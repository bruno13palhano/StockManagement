package screens

import ViewModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    title: String,
    itemClick: (route: String) -> Unit
) {
    val viewModel = ViewModel().getVM()
    val sales by viewModel.sales.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchData()
    }

    LazyColumn {
        items(items = sales.toList(), key = { sale -> sale.id }) { sale ->
            ListItem(modifier = Modifier
                .clickable {
                    itemClick(sale.productName)
                }
            ) {
                Text(text = sale.productName)
            }
        }
    }
}