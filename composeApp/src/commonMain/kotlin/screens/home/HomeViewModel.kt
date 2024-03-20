package screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.SaleLocalData
import data.SaleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import model.Sale

class HomeViewModel(
    private val saleRepository: SaleRepository = SaleRepository(data = SaleLocalData())
) {
    var sales = MutableStateFlow<List<Sale>>(emptyList())

    var currentSale by mutableStateOf("")
        private set

    fun updateCurrentSale(currentSale: String) {
        this.currentSale = currentSale
    }

    fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            saleRepository.getAll().collect {
                sales.value = it
            }
        }
    }
}