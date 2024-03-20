package screens.home

import ViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    title: String,
    onItemClick: (id: Long) -> Unit,
    onIconMenuClick: () -> Unit
) {
    val viewModel = ViewModel().getVM()
    val sales by viewModel.sales.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = onIconMenuClick) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn {
            items(items = sales.toList(), key = { sale -> sale.id }) { sale ->
                ListItem(modifier = Modifier
                    .clickable {
                        onItemClick(sale.id)
                    }
                ) {
                    Text(text = "${sale.productName}, $title")
                }
            }
        }
    }
}