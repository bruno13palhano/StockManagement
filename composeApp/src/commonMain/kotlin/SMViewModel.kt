import kotlinx.coroutines.flow.MutableStateFlow
import model.Sale

interface SMViewModel {
    val sales: MutableStateFlow<List<Sale>>
    fun fetchData()
}

expect fun getViewModel(): SMViewModel