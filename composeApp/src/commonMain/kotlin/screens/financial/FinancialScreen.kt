package screens.financial

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aay.compose.donutChart.DonutChart
import com.aay.compose.donutChart.model.PieChartData
import org.jetbrains.compose.resources.stringResource
import stockmanagement.composeapp.generated.resources.*

@Composable
fun FinancialRoute(
    onIconMenuClick: () -> Unit,
    viewModel: FinancialViewModel
) {
    LaunchedEffect(key1 = Unit) { viewModel.getData() }

    val uiState by viewModel.uiState.collectAsState()
    val showProfitChart by viewModel.showProfitChart.collectAsState()
    val showSalesChart by viewModel.showSalesChart.collectAsState()
    val showCanceledChart by viewModel.showCanceledChart.collectAsState()

    FinancialScreen(
        amount = uiState.amount,
        profit = uiState.profit,
        resaleProfit = uiState.resaleProfit,
        amazonProfit = uiState.amazonProfit,
        paidSales = uiState.paidSales,
        notPaidSales = uiState.notPaidSales,
        doneSales = uiState.doneSales,
        canceledSales = uiState.canceledSales,
        showProfitChart = showProfitChart,
        showSalesChart = showSalesChart,
        showCanceledChart = showCanceledChart,
        biggestSale = uiState.biggestSale,
        smallestSale = uiState.smallestSale,
        onIconMenuClick = onIconMenuClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FinancialScreen(
    amount: Double,
    profit: Double,
    resaleProfit: Double,
    amazonProfit: Double,
    paidSales: Int,
    notPaidSales: Int,
    doneSales: Int,
    canceledSales: Int,
    showProfitChart: Boolean,
    showSalesChart: Boolean,
    showCanceledChart: Boolean,
    biggestSale: Double,
    smallestSale: Double,
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
        Column(modifier = Modifier.padding(it).verticalScroll(rememberScrollState())) {
            ElevatedCard(modifier = Modifier.padding(8.dp)) {
                Row {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                        text = stringResource(Res.string.amount_label),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp, end = 16.dp),
                        text = amount.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                        text = stringResource(Res.string.profit_tag),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp, end = 16.dp),
                        text = profit.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                        text = stringResource(Res.string.biggest_sale_label),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, end = 16.dp),
                        text = biggestSale.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 16.dp),
                        text = stringResource(Res.string.smallest_sale_label),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, end = 16.dp, bottom = 16.dp),
                        text = smallestSale.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            if (showSalesChart) {
                HorizontalDivider(modifier = Modifier.padding(8.dp).fillMaxWidth())

                Text(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    text = stringResource(Res.string.sales_label),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.titleMedium,
                    fontStyle = FontStyle.Italic
                )

                DonutChart(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, end = 8.dp, bottom = 16.dp)
                        .fillMaxWidth()
                        .sizeIn(maxHeight = 400.dp),
                    pieChartData = listOf(
                        PieChartData(
                            partName = stringResource(Res.string.paid_label),
                            data = paidSales.toDouble(),
                            color = MaterialTheme.colorScheme.secondary
                        ),
                        PieChartData(
                            partName = stringResource(Res.string.not_paid_label),
                            data = notPaidSales.toDouble(),
                            color = MaterialTheme.colorScheme.error
                        ),
                    ),
                    centerTitle = stringResource(Res.string.sales_label)
                )
            }

            if (showProfitChart) {
                HorizontalDivider(modifier = Modifier.padding(8.dp).fillMaxWidth())

                Text(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    text = stringResource(Res.string.profit_label),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.titleMedium,
                    fontStyle = FontStyle.Italic
                )

                DonutChart(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, end = 8.dp, bottom = 16.dp)
                        .fillMaxWidth()
                        .sizeIn(maxHeight = 400.dp),
                    pieChartData = listOf(
                        PieChartData(
                            partName = stringResource(Res.string.resale_label),
                            data = resaleProfit,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        PieChartData(
                            partName = stringResource(Res.string.amazon_label),
                            data = amazonProfit,
                            color = MaterialTheme.colorScheme.tertiary
                        ),
                    ),
                    centerTitle = stringResource(Res.string.profit_label)
                )
            }

            if (showCanceledChart) {
                HorizontalDivider(modifier = Modifier.padding(8.dp).fillMaxWidth())

                Text(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    text = stringResource(Res.string.done_vs_canceled_label),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.titleMedium,
                    fontStyle = FontStyle.Italic
                )

                DonutChart(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, end = 8.dp, bottom = 16.dp)
                        .fillMaxWidth()
                        .sizeIn(maxHeight = 400.dp),
                    pieChartData = listOf(
                        PieChartData(
                            partName = stringResource(Res.string.done_label),
                            data = doneSales.toDouble(),
                            color = MaterialTheme.colorScheme.inversePrimary
                        ),
                        PieChartData(
                            partName = stringResource(Res.string.canceled_label),
                            data = canceledSales.toDouble(),
                            color = MaterialTheme.colorScheme.inverseSurface
                        ),
                    ),
                    centerTitle = stringResource(Res.string.sales_label)
                )
            }
        }
    }
}
