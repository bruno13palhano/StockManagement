package screens.customer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.customer.CustomerRepository
import kotlinx.coroutines.launch
import model.Customer

class CustomerViewModel(private val customerRepository: CustomerRepository) : ViewModel() {
    var name by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var address by mutableStateOf("")
        private set
    var city by mutableStateOf("")
        private set
    var phoneNumber by mutableStateOf("")
        private set
    var gender by mutableStateOf("")
        private set
    var age by mutableStateOf("")
        private set

    fun updateName(name: String) { this.name = name }

    fun updateEmail(email: String) { this.email = email }

    fun updateAddress(address: String) { this.address = address }

    fun updateCity(city: String) { this.city = city }

    fun updatePhoneNumber(phoneNumber: String) { this.phoneNumber = phoneNumber }

    fun updateGender(gender: String) { this.gender = gender }

    fun updateAge(age: String) { this.age = age }

    fun getCustomer(id: Long) {
        viewModelScope.launch {
            customerRepository.getById(id = id).collect {
                name = it.name
                email = it.email
                address = it.address
                city = it.city
                phoneNumber = it.phoneNumber
                gender = it.gender
                age = it.age.toString()
            }
        }
    }

    fun save(id: Long = 0L) {
        viewModelScope.launch {
            if (id == 0L) {
                customerRepository.insert(model = buildCustomer())
            } else {
                customerRepository.update(model = buildCustomer(id = id))
            }
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            customerRepository.delete(id = id)
        }
    }

    private fun buildCustomer(
        id: Long = 0L
    ) = Customer(
        id = id,
        name = name,
        email = email,
        address = address,
        city = city,
        phoneNumber = phoneNumber,
        gender = gender,
        age = age.toInt()
    )
}