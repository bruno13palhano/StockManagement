package screens.customer

import androidx.lifecycle.ViewModel
import data.customer.CustomerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import model.Customer

class CustomersViewModel(
    private val customerRepository: CustomerRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {
    private var _customers = MutableStateFlow(listOf<Customer>())
    val customers = _customers
        .stateIn(
            scope = CoroutineScope(SupervisorJob() + dispatcher),
            started = WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun getCustomers() {
        CoroutineScope(SupervisorJob() + dispatcher).launch {
            customerRepository.getAll().collect {
                _customers.value = it
            }
        }
    }
}