package screens.sales

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.CustomerRepository
import data.SaleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import model.Sale

class SaleViewModel(
    private val saleRepository: SaleRepository,
    private val customerRepository: CustomerRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private var customerId = 0L
    private var canceled = false

    var productName by mutableStateOf("")
        private set
    var customerName by mutableStateOf("")
        private set
    var purchasePrice by mutableStateOf("")
        private set
    var salePrice by mutableStateOf("")
        private set
    var deliveryPrice by mutableStateOf("")
        private set
    var amazonCode by mutableStateOf("")
        private set
    var sku by mutableStateOf("")
        private set
    var requestNumber by mutableStateOf("")
        private set
    var tax by mutableStateOf("")
        private set
    var amazonProfit by mutableStateOf("")
        private set
    var resaleProfit by mutableStateOf("")
        private set
    var totalProfit by mutableStateOf("")
        private set
    var dateOfSale by mutableLongStateOf(0L)
        private set
    var dateOfPayment by mutableLongStateOf(0L)
        private set
    var dateOfShipping by mutableLongStateOf(0L)
        private set
    var dateOfDelivery by mutableLongStateOf(0L)
        private set
    var isPaid by mutableStateOf(false)
        private set
    var delivered by mutableStateOf(false)
        private set
    var allCustomers by mutableStateOf(listOf<CustomerCheck>())
        private set

    fun updateProductName(productName: String) { this.productName = productName }

    fun updatePurchasePrice(purchasePrice: String) { this.purchasePrice = purchasePrice }

    fun updateSalePrice(salePrice: String) { this.salePrice = salePrice }

    fun updateDeliveryPrice(deliveryPrice: String) { this.deliveryPrice = deliveryPrice }

    fun updateAmazonCode(amazonCode: String) { this.amazonCode = amazonCode }

    fun updateSku(sku: String) { this.sku = sku }

    fun updateRequestNumber(requestNumber: String) { this.requestNumber = requestNumber }

    fun updateTax(tax: String) { this.tax = tax }

    fun updateAmazonProfit(amazonProfit: String) { this.amazonProfit = amazonProfit }

    fun updateResaleProfit(resaleProfit: String) { this.resaleProfit = resaleProfit }

    fun updateTotalProfit(totalProfit: String) { this.totalProfit = totalProfit }

    fun updateDateOfSale(date: Long) { dateOfSale = date }

    fun updateDateOfPayment(date: Long) { dateOfPayment = date }

    fun updateDateOfShipping(date: Long) { dateOfShipping = date }

    fun updateDateOfDelivery(date: Long) { dateOfDelivery = date }

    fun updateIsPaid(isPaid: Boolean) { this.isPaid = isPaid }

    fun updateDelivered(delivered: Boolean) { this.delivered = delivered }

    fun updateCustomerName(customerName: String) {
        this.customerName = customerName
        allCustomers.map { customer -> customer.isChecked = false }
        for (customer in allCustomers) {
            if (customer.name == customerName) {
                customerId = customer.id
                customer.isChecked = true
                break
            }
        }
    }

    fun getSale(id: Long) {
        CoroutineScope(SupervisorJob() + dispatcher).launch {
            saleRepository.getById(id = id).collect {
                customerId = it.customerId
                productName = it.productName
                customerName = it.customerName
                purchasePrice = it.purchasePrice.toString()
                salePrice = it.salePrice.toString()
                deliveryPrice = it.deliveryPrice.toString()
                amazonCode = it.amazonCode
                sku = it.sku
                requestNumber = it.requestNumber.toString()
                tax = it.tax.toString()
                amazonProfit = it.amazonProfit.toString()
                resaleProfit = it.amazonProfit.toString()
                totalProfit = it.totalProfit.toString()
                updateDateOfSale(it.dateOfSale)
                updateDateOfPayment(it.dateOfPayment)
                updateDateOfShipping(it.dateOfShipping)
                updateDateOfDelivery(it.dateOfDelivery)
                isPaid = it.isPaid
                delivered = it.delivered
                canceled = it.canceled
                setCustomerChecked(customerId = it.customerId)
            }
        }
    }

    private fun setCustomerChecked(customerId: Long) {
        for (customer in allCustomers) {
            if (customer.id == customerId) {
                customer.isChecked = true
                break
            }
        }
    }

    fun getAllCustomers() {
        CoroutineScope(SupervisorJob() + dispatcher).launch {
            customerRepository.getAll().collect {
                allCustomers = it.map { customer ->
                    CustomerCheck(
                        id = customer.id,
                        name = customer.name,
                        isChecked = false
                    )
                }
            }
        }
    }

    fun cancelSale(id: Long) {
        canceled = true
        save(id = id)
    }

    fun save(id: Long) {
        CoroutineScope(SupervisorJob() + dispatcher).launch {
            if (id == 0L) {
                saleRepository.insert(model = buildSale())
            } else {
                saleRepository.update(model = buildSale(id = id))
            }
        }
    }

    private fun buildSale(id: Long = 0L): Sale {
        return Sale(
            id = id,
            customerId = customerId,
            productName = productName,
            customerName = customerName,
            purchasePrice = purchasePrice.toFloat(),
            salePrice = salePrice.toFloat(),
            deliveryPrice = deliveryPrice.toFloat(),
            amazonCode = amazonCode,
            sku = sku,
            requestNumber = requestNumber.toLong(),
            tax = tax.toInt(),
            amazonProfit = amazonProfit.toFloat(),
            resaleProfit = resaleProfit.toFloat(),
            totalProfit = totalProfit.toFloat(),
            dateOfSale = dateOfSale,
            dateOfPayment = dateOfPayment,
            dateOfShipping = dateOfShipping,
            dateOfDelivery = dateOfDelivery,
            isPaid = isPaid,
            delivered = delivered,
            canceled = canceled
        )
    }
}

data class CustomerCheck(
    var id: Long,
    var name: String,
    var isChecked: Boolean
)