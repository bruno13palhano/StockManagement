import data.SaleLocalData
import data.SaleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.Sale

class AndroidViewModel(private val saleRepository: SaleRepository) : SMViewModel {
    override val sales = MutableStateFlow<List<Sale>>(emptyList())
    override fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            saleRepository.getAll().collect {
                sales.value = it
            }
        }
    }
}

actual fun getViewModel(): SMViewModel = AndroidViewModel(SaleRepository(SaleLocalData()))