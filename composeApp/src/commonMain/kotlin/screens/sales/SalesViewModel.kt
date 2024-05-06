package screens.sales

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
import model.SaleInfo

class SalesViewModel(
    private val saleRepository: SaleRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {
    private var _sales = MutableStateFlow(listOf<SaleInfo>())
    val sales = _sales
        .stateIn(
            scope = CoroutineScope(SupervisorJob() + dispatcher),
            started = WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun getSales() {
        CoroutineScope(SupervisorJob() + dispatcher).launch {
            saleRepository.getAll().collect {
                _sales.value = it.map {  sale ->
                    SaleInfo(
                        id = sale.id,
                        productName = sale.productName,
                        customerName = sale.customerName,
                        quantity = sale.quantity,
                        totalPrice = (sale.salePrice * sale.quantity)
                    )
                }
            }
        }
    }
}