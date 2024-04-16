package screens.sales

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import data.customer.CustomerRepository
import data.sale.SaleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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
    var quantity by mutableStateOf("")
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
    var resaleProfit by mutableStateOf("")
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

    private val _amazonProfit = snapshotFlow { calcAmazonProfit() }
    val amazonProfit = _amazonProfit
        .stateIn(
            scope = CoroutineScope(SupervisorJob() + dispatcher),
            started = WhileSubscribed(5_000),
            initialValue = ""
        )

    private val _totalProfit = combine(
        amazonProfit,
        snapshotFlow { resaleProfit }
    ) { aProfit, rProfit ->
        (stringToFloat(aProfit) + stringToFloat(rProfit)).toString()
    }

    val totalProfit = _totalProfit
        .stateIn(
            scope = CoroutineScope(SupervisorJob() + dispatcher),
            started = WhileSubscribed(5_000),
            initialValue = ""
        )

    fun updateProductName(productName: String) { this.productName = productName }

    fun updatePurchasePrice(purchasePrice: String) { this.purchasePrice = purchasePrice }

    fun updateSalePrice(salePrice: String) { this.salePrice = salePrice }

    fun updateQuantity(quantity: String) { this.quantity = quantity }

    fun updateDeliveryPrice(deliveryPrice: String) { this.deliveryPrice = deliveryPrice }

    fun updateAmazonCode(amazonCode: String) { this.amazonCode = amazonCode }

    fun updateSku(sku: String) { this.sku = sku }

    fun updateRequestNumber(requestNumber: String) { this.requestNumber = requestNumber }

    fun updateTax(tax: String) { this.tax = tax }

    fun updateResaleProfit(resaleProfit: String) { this.resaleProfit = resaleProfit }

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
                quantity = it.quantity.toString()
                deliveryPrice = it.deliveryPrice.toString()
                amazonCode = it.amazonCode
                sku = it.sku
                requestNumber = it.requestNumber.toString()
                tax = it.tax.toString()
                resaleProfit = it.resaleProfit.toString()
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
            purchasePrice = stringToFloat(purchasePrice),
            salePrice = stringToFloat(salePrice),
            quantity = stringToInt(quantity),
            deliveryPrice = stringToFloat(deliveryPrice),
            amazonCode = amazonCode,
            sku = sku,
            requestNumber = stringToLong(requestNumber),
            tax = stringToInt(tax),
            amazonProfit = stringToFloat(amazonProfit.value),
            resaleProfit = stringToFloat(resaleProfit),
            totalProfit = stringToFloat(totalProfit.value),
            dateOfSale = dateOfSale,
            dateOfPayment = dateOfPayment,
            dateOfShipping = dateOfShipping,
            dateOfDelivery = dateOfDelivery,
            isPaid = isPaid,
            delivered = delivered,
            canceled = canceled
        )
    }

    private fun calcAmazonProfit(): String {
        return (if (salePrice.isNotEmpty()) {
            val totalAmazonPrice = (stringToFloat(salePrice) * stringToFloat(quantity))
            (totalAmazonPrice - ((stringToFloat(tax) * totalAmazonPrice) / 100)) -
                    stringToFloat(purchasePrice)
        } else 0F).toString()
    }
}

private fun stringToInt(value: String) = try { value.toInt() } catch(e: Exception) { 0 }
private fun stringToLong(value: String) = try { value.toLong() } catch (e: Exception) { 0L }
private fun stringToFloat(value: String) = try {  value.toFloat() } catch (e: Exception) { 0F }

data class CustomerCheck(
    var id: Long,
    var name: String,
    var isChecked: Boolean
)