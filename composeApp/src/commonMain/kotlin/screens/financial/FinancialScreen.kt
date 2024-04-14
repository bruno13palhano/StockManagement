package screens.financial

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import model.Sale
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import stockmanagement.composeapp.generated.resources.Res

@Composable
fun FinancialRoute(
    onIconMenuClick: () -> Unit,
    viewModel: FinancialViewModel
) {
    LaunchedEffect(key1 = Unit) { viewModel.getData(); viewModel.getLastSales() }

    val lastSales by viewModel.lastSales.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    FinancialScreen(
        lastSales = lastSales,
        amount = uiState.amount,
        profit = uiState.profit,
        biggestSale = uiState.biggestSale,
        smallestSale = uiState.smallestSale,
        onIconMenuClick = onIconMenuClick
    )
}

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun FinancialScreen(
    lastSales: List<Sale>,
    amount: Float,
    profit:Float,
    biggestSale:Float,
    smallestSale: Float,
    onIconMenuClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(Res.string.financial_label)) },
                navigationIcon = {
                    IconButton(onClick = onIconMenuClick) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = stringResource(Res.string.drawer_menu_label)
                        )
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            ElevatedCard(modifier = Modifier.padding(8.dp)) {
                Row {
                    Text(
                        modifier = Modifier.padding(start = 24.dp, top = 8.dp),
                        text = stringResource(Res.string.amount_label),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, end = 24.dp),
                        text = amount.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row {
                    Text(
                        modifier = Modifier.padding(start = 24.dp, top = 8.dp),
                        text = stringResource(Res.string.profit_label),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, end = 24.dp),
                        text = profit.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row {
                    Text(
                        modifier = Modifier.padding(start = 24.dp, top = 8.dp),
                        text = stringResource(Res.string.biggest_sale_label),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, end = 24.dp),
                        text = biggestSale.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row {
                    Text(
                        modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 8.dp),
                        text = stringResource(Res.string.smallest_sale_label),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, end = 24.dp, bottom = 8.dp),
                        text = smallestSale.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            LazyColumn (
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
            ) {
                items(items = lastSales, key = {sale -> sale.id} ) { sale ->
                    ElevatedCard(
                        shape = RoundedCornerShape(5),
                        onClick = {}
                    ) {
                        ListItem(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            headlineContent = {
                                Text(
                                    text = sale.productName,
                                    style = MaterialTheme.typography.titleMedium,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                                Text(
                                    text = sale.salePrice.toString(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 1
                                )
                                Text(
                                    text = sale.customerName,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontStyle = FontStyle.Italic,
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
}
