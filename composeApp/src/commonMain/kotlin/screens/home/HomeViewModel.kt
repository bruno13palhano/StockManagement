package screens.home

import data.SaleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.Sale

class HomeViewModel(
    private val saleRepository: SaleRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    var sales = MutableStateFlow<List<Sale>>(emptyList())

    fun fetchData() {
        CoroutineScope(dispatcher).launch {
            saleRepository.getAll().collect {
                sales.value = it
            }
        }
    }
}