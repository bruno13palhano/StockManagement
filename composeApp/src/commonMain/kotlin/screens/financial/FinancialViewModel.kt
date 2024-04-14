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
                var amount = 0F
                var profit = 0F
                var biggestSale = Float.MIN_VALUE
                var smallestSale = Float.MAX_VALUE

                it.forEach { sale ->
                    amount += (sale.salePrice * sale.quantity)
                    profit += ((sale.salePrice * sale.quantity) - sale.deliveryPrice)
                    if (sale.salePrice >= biggestSale)
                        biggestSale = sale.salePrice
                    if (sale.salePrice <= smallestSale)
                        smallestSale = sale.salePrice
                }

                _uiState.value = FinancialUiState(
                    amount = amount,
                    profit = profit,
                    biggestSale = biggestSale,
                    smallestSale = smallestSale
                )
            }
        }
    }

    data class FinancialUiState(
        val amount: Float = 0F,
        val profit: Float = 0F,
        val biggestSale: Float = 0F,
        val smallestSale: Float = 0F
    )
}