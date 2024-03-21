package screens.sales

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.SaleLocalData
import data.SaleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SaleViewModel(
    private val saleRepository: SaleRepository = SaleRepository(data = SaleLocalData())
) {
    var productName by mutableStateOf("")
        private set
    var productPrice by mutableStateOf("")
        private set

    fun updateProductName(productName: String) {
        this.productName = productName
    }

    fun updateProductPrice(productPrice: String) {
        this.productPrice = productPrice
    }

    fun getSale(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            saleRepository.getById(id = id).collect {
                productName = it.productName
                productPrice = it.productPrice.toString()
            }
        }
    }
}