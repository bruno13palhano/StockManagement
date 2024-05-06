package screens.sales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.sale.SaleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import model.SaleInfo

class SalesViewModel(private val saleRepository: SaleRepository) : ViewModel() {
    private var _sales = MutableStateFlow(listOf<SaleInfo>())
    val sales = _sales
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun getSales() {
        viewModelScope.launch {
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