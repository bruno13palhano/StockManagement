package screens.home

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

class HomeViewModel(
    private val saleRepository: SaleRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {
    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState
        .stateIn(
            scope = CoroutineScope(SupervisorJob() + dispatcher),
            started = WhileSubscribed(5_000),
            initialValue = HomeUiState()
        )

    fun getData() {
        CoroutineScope(SupervisorJob() + dispatcher).launch {
            saleRepository.getAll().collect {
                var currentAmount = 0F
                var currentProfit = 0F
                var currentBiggest = Float.MIN_VALUE
                var currentSmallest = Float.MAX_VALUE

                it.forEach { sale ->
                    currentAmount += (sale.salePrice * sale.quantity)
                    currentProfit += ((sale.salePrice * sale.quantity) - sale.deliveryPrice)
                    if (sale.salePrice >= currentBiggest)
                        currentBiggest = sale.salePrice
                    if (sale.salePrice <= currentSmallest)
                        currentSmallest = sale.salePrice
                }

                _uiState.value = HomeUiState(
                    amount = currentAmount,
                    profit = currentAmount,
                    biggestSale = currentBiggest,
                    smallestSale = currentSmallest
                )
            }
        }
    }

    data class HomeUiState(
        val amount: Float = 0F,
        val profit: Float = 0F,
        val biggestSale: Float = 0F,
        val smallestSale: Float = 0F
    )
}