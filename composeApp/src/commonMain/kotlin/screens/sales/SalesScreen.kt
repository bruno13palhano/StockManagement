package screens.sales

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import model.SaleInfo
import org.jetbrains.compose.resources.stringResource
import stockmanagement.composeapp.generated.resources.*

@Composable
fun SalesRoute(
    onItemClick: (id: Long) -> Unit,
    onAddButtonClick: () -> Unit,
    onIconMenuClick: () -> Unit,
    viewModel: SalesViewModel
) {
    val sales by viewModel.sales.collectAsState()

    LaunchedEffect(key1 = Unit) { viewModel.getSales() }

    SalesScreen(
        sales = sales,
        onItemClick = onItemClick,
        onAddButtonClick = onAddButtonClick,
        onIconMenuClick = onIconMenuClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SalesScreen(
    sales: List<SaleInfo>,
    onItemClick: (id: Long) -> Unit,
    onAddButtonClick: () -> Unit,
    onIconMenuClick: () -> Unit
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
            FloatingActionButton(onClick = onAddButtonClick) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(Res.string.add_button_label)
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            items(items = sales, key = { sale -> sale.id }) { sale ->
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    shape = RoundedCornerShape(5),
                    onClick = { onItemClick(sale.id) }
                ) {
                    val salePriceAndQuantity =
                        (
                            if (sale.quantity == 1) {
                                stringResource(
                                    Res.string.item_label,
                                    sale.quantity,
                                    sale.totalPrice
                                )
                            } else {
                                stringResource(
                                    Res.string.items_label,
                                    sale.quantity,
                                    sale.totalPrice
                                )
                            }
                        ).toString()

                    ListItem(
                        headlineContent = {
                            Text(
                                text = sale.productName,
                                style = MaterialTheme.typography.titleMedium,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                            Text(
                                text = salePriceAndQuantity,
                                style = MaterialTheme.typography.bodyMedium,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                            Text(
                                text = sale.customerName,
                                style = MaterialTheme.typography.bodyMedium,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }
                    )
                }
            }
        }
    }
}