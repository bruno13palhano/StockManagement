package screens.home

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

class HomeViewModel(
    private val saleRepository: SaleRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private var _sales = MutableStateFlow<List<Sale>>(emptyList())
    val sales = _sales
        .stateIn(
            scope = CoroutineScope(SupervisorJob() + dispatcher),
            started = WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun fetchData() {
        CoroutineScope(SupervisorJob() + dispatcher).launch {
            saleRepository.getAll().collect {
                _sales.value = it
            }
        }
    }
}