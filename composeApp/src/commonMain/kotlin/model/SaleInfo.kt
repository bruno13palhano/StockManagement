package model

data class SaleInfo(
    val id: Long,
    val productName: String,
    val customerName: String,
    val quantity: Int,
    val totalPrice: Float
)