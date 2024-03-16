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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.Sale

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    title: String,
    itemClick: (route: String) -> Unit
) {
    val viewModel = HomeViewModel()
    val sales = remember { mutableStateListOf<Sale>() }

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchData()
    }

    val coroutineScope = rememberCoroutineScope()

    coroutineScope.launch {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.sales.collect {
                sales.apply { addAll(it) }
            }
        }
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