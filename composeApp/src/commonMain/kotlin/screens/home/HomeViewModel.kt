package screens.home

import data.SaleLocalData
import data.SaleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.Sale

class HomeViewModel(
    private val saleRepository: SaleRepository = SaleRepository(data = SaleLocalData())
) {
    var sales = MutableStateFlow<List<Sale>>(emptyList())

    fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            saleRepository.getAll().collect {
                sales.value = it
            }
        }
    }
}