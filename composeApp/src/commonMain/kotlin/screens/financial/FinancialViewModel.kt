package screens.financial

import data.sale.SaleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import model.Sale

class FinancialViewModel(
    private val saleRepository: SaleRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private var _uiState = MutableStateFlow(FinancialUiState())
    val uiState = _uiState
        .stateIn(
            scope = CoroutineScope(SupervisorJob() + dispatcher),
            started = WhileSubscribed(5_000),
            initialValue = FinancialUiState()
        )

    private var _lastSales = MutableStateFlow(listOf<Sale>())

    val lastSales = _lastSales
        .stateIn(
            scope = CoroutineScope(SupervisorJob() + dispatcher),
            started = WhileSubscribed(),
            initialValue = listOf()
        )

    fun getLastSales() {
        CoroutineScope(SupervisorJob() + dispatcher).launch {
            saleRepository.getSales(offset = 0, limit = 3).collect {
                _lastSales.value = it
            }
        }
    }

    fun getData() {
        CoroutineScope(SupervisorJob() + dispatcher).launch {
            saleRepository.getAll().collect {
                var amount = 0.0
                var resaleProfit = 0.0
                var amazonProfit = 0.0
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
                }

                _uiState.value = FinancialUiState(
                    amount = amount,
                    profit = amazonProfit + resaleProfit,
                    resaleProfit = resaleProfit,
                    amazonProfit = amazonProfit,
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
        val biggestSale: Double = 0.0,
        val smallestSale: Double = 0.0
    )
}