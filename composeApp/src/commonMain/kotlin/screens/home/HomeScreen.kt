package screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import stockmanagement.composeapp.generated.resources.*

@Composable
fun HomeRoute(
    onIconMenuClick: () -> Unit,
    onAddButtonClick: () -> Unit,
    viewModel: HomeViewModel
) {
    // TODO: passar sales para home novamente.
    LaunchedEffect(key1 = Unit) { viewModel.getData() }

    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        amount = uiState.amount,
        profit = uiState.profit,
        biggestSale = uiState.biggestSale,
        smallestSale = uiState.smallestSale,
        onIconMenuClick = onIconMenuClick,
        onAddButtonClick = onAddButtonClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    amount: Float,
    profit: Float,
    biggestSale: Float,
    smallestSale: Float,
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
        Column(modifier = Modifier.padding(it)) {
            ElevatedCard(modifier = Modifier.padding(8.dp)) {
                Row {
                    Text(
                        modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                        text = stringResource(Res.string.amount_label),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, end = 8.dp),
                        text = amount.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row {
                    Text(
                        modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                        text = stringResource(Res.string.profit_tag),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, end = 8.dp),
                        text = profit.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row {
                    Text(
                        modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                        text = stringResource(Res.string.biggest_sale_label),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, end = 8.dp),
                        text = biggestSale.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row {
                    Text(
                        modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                        text = stringResource(Res.string.smallest_sale_label),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, end = 8.dp, bottom = 8.dp),
                        text = smallestSale.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}