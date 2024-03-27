package model

data class Sale(
    val id: Long,
    val customerId: Long,
    val productName: String,
    val customerName: String,
    val purchasePrice: Float,
    val salePrice: Float,
    val quantity: Int,
    val deliveryPrice: Float,
    val amazonCode: String,
    val sku: String,
    val requestNumber: Long,
    val tax: Int,
    val amazonProfit: Float,
    val resaleProfit : Float,
    val totalProfit: Float,
    val dateOfSale: Long,
    val dateOfPayment: Long,
    val dateOfShipping: Long,
    val dateOfDelivery: Long,
    val isPaid: Boolean,
    val delivered: Boolean,
    val canceled: Boolean
)
