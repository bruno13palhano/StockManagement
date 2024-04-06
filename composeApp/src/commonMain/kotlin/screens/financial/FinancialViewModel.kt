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
}