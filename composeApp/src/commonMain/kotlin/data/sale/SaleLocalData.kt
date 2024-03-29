package data.sale

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.bruno13palhano.database.SaleQueries
import data.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import model.Sale

class SaleLocalData(private val saleQueries: SaleQueries) : Data<Sale> {
    override suspend fun insert(model: Sale) {
        saleQueries.insert(
            customerId = model.customerId,
            productName = model.productName,
            purchasePrice = model.purchasePrice.toDouble(),
            salePrice = model.salePrice.toDouble(),
            quantity = model.quantity.toLong(),
            deliveryPrice = model.deliveryPrice.toDouble(),
            amazonCode = model.amazonCode,
            sku = model.sku,
            requestNumber = model.requestNumber,
            tax = model.tax.toLong(),
            amazonProfit = model.amazonProfit.toDouble(),
            resaleProfit = model.resaleProfit.toDouble(),
            totalProfit = model.totalProfit.toDouble(),
            dateOfSale = model.dateOfSale,
            dateOfPayment = model.dateOfPayment,
            dateOfShipping = model.dateOfShipping,
            dateOfDelivery = model.dateOfDelivery,
            isPaid = model.isPaid,
            delivered = model.delivered,
            canceled = model.canceled
        )
    }

    override suspend fun update(model: Sale) {
        saleQueries.update(
            customerId = model.customerId,
            productName = model.productName,
            purchasePrice = model.purchasePrice.toDouble(),
            salePrice = model.salePrice.toDouble(),
            quantity = model.quantity.toLong(),
            deliveryPrice = model.deliveryPrice.toDouble(),
            amazonCode = model.amazonCode,
            sku = model.sku,
            requestNumber = model.requestNumber,
            tax = model.tax.toLong(),
            amazonProfit = model.amazonProfit.toDouble(),
            resaleProfit = model.resaleProfit.toDouble(),
            totalProfit = model.totalProfit.toDouble(),
            dateOfSale = model.dateOfSale,
            dateOfPayment = model.dateOfPayment,
            dateOfShipping = model.dateOfShipping,
            dateOfDelivery = model.dateOfDelivery,
            isPaid = model.isPaid,
            delivered = model.delivered,
            canceled = model.canceled,
            id = model.id
        )
    }

    override suspend fun delete(id: Long) {
        saleQueries.delete(id = id)
    }

    override fun getById(id: Long): Flow<Sale> {
        return saleQueries.getById(id = id, mapper = ::mapToSale).asFlow().mapToOne(Dispatchers.IO)
    }

    override fun getAll(): Flow<List<Sale>> {
        return saleQueries.getAll(mapper = ::mapToSale).asFlow().mapToList(Dispatchers.IO)
    }

    private fun mapToSale(
        id: Long,
        customerId: Long,
        productName: String,
        customerName: String,
        purchasePrice: Double,
        salePrice: Double,
        quantity: Long,
        deliveryPrice: Double,
        amazonCode: String,
        sku: String,
        requestNumber: Long,
        tax: Long,
        amazonProfit: Double,
        resaleProfit: Double,
        totalProfit: Double,
        dateOfSale: Long,
        dateOfPayment: Long,
        dateOfShipping: Long,
        dateOfDelivery: Long,
        isPaid: Boolean,
        delivered: Boolean,
        canceled: Boolean
    ) = Sale(
        id = id,
        customerId = customerId,
        productName = productName,
        customerName = customerName,
        purchasePrice = purchasePrice.toFloat(),
        salePrice = salePrice.toFloat(),
        quantity = quantity.toInt(),
        deliveryPrice = deliveryPrice.toFloat(),
        amazonCode = amazonCode,
        sku = sku,
        requestNumber = requestNumber,
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