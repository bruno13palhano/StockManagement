package screens.financial

import androidx.lifecycle.ViewModel
import data.sale.SaleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FinancialViewModel(
    private val saleRepository: SaleRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {
    private var _uiState = MutableStateFlow(FinancialUiState())
    val uiState = _uiState
        .stateIn(
            scope = CoroutineScope(SupervisorJob() + dispatcher),
            started = WhileSubscribed(5_000),
            initialValue = FinancialUiState()
        )

    private var _showProfitChart = MutableStateFlow(false)
    val showProfitChart = _showProfitChart
        .stateIn(
            scope = CoroutineScope(SupervisorJob() + dispatcher),
            started = WhileSubscribed(5_000),
            initialValue = false
        )

    private var _showSalesChart = MutableStateFlow(false)
    val showSalesChart = _showSalesChart
        .stateIn(
            scope = CoroutineScope(SupervisorJob() + dispatcher),
            started = WhileSubscribed(5_000),
            initialValue = false
        )

    private var _showCanceledChart = MutableStateFlow(false)
    val showCanceledChart = _showCanceledChart
        .stateIn(
            scope = CoroutineScope(SupervisorJob() + dispatcher),
            started = WhileSubscribed(5_000),
            initialValue = false
        )

    fun getData() {
        CoroutineScope(SupervisorJob() + dispatcher).launch {
            saleRepository.getAll().collect {
                var amount = 0.0
                var resaleProfit = 0.0
                var amazonProfit = 0.0
                var paidSales = 0
                var notPaidSales = 0
                var doneSales = 0
                var canceledSales = 0
                var biggestSale = Double.MIN_VALUE
                var smallestSale = Double.MAX_VALUE

                it.forEach { sale ->
                    amount += (sale.salePrice * sale.quantity)
                    resaleProfit += sale.resaleProfit
                    amazonProfit += sale.amazonProfit

                    if (sale.salePrice >= biggestSale)
                        biggestSale = sale.salePrice.toDouble()

                    if (sale.salePrice <= smallestSale)
                        smallestSale = sale.salePrice.toDouble()

                    if (sale.isPaid) paidSales++ else notPaidSales++

                    if (sale.canceled) canceledSales++ else doneSales++
                }

                if (paidSales != 0 || notPaidSales != 0)
                    _showSalesChart.value = true

                if (resaleProfit != 0.0 || amazonProfit != 0.0)
                    _showProfitChart.value = true

                if (doneSales != 0 || canceledSales != 0)
                    _showCanceledChart.value = true

                _uiState.value = FinancialUiState(
                    amount = amount,
                    profit = amazonProfit + resaleProfit,
                    resaleProfit = resaleProfit,
                    amazonProfit = amazonProfit,
                    paidSales = paidSales,
                    notPaidSales = notPaidSales,
                    doneSales = doneSales,
                    canceledSales = canceledSales,
                    biggestSale = biggestSale,
                    smallestSale = smallestSale
                )
            }
        }
    }

    data class FinancialUiState(
        val amount: Double = 0.0,
        val profit: Double = 0.0,
        val resaleProfit: Double = 1.0,
        val amazonProfit: Double = 0.0,
        val paidSales: Int = 0,
        val notPaidSales: Int = 1,
        val doneSales: Int = 1,
        val canceledSales: Int = 0,
        val biggestSale: Double = 0.0,
        val smallestSale: Double = 0.0
    )
}