package screens.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.customer.CustomerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import model.Customer

class CustomersViewModel(private val customerRepository: CustomerRepository) : ViewModel() {
    private var _customers = MutableStateFlow(listOf<Customer>())
    val customers = _customers
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun getCustomers() {
        viewModelScope.launch {
            customerRepository.getAll().collect {
                _customers.value = it
            }
        }
    }
}