package data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.bruno13palhano.database.SaleQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import model.Sale

class SaleLocalData(private val saleQueries: SaleQueries) : Data {
    override suspend fun insert(sale: Sale) {
        saleQueries.insert(
            productName = sale.productName,
            productPrice = sale.productPrice.toDouble()
        )
    }

    override suspend fun update(sale: Sale) {
        saleQueries.update(
            productName = sale.productName,
            productPrice = sale.productPrice.toDouble(),
            id = sale.id
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
        productName: String,
        productPrice: Double
    ) = Sale(
        id = id,
        productName = productName,
        productPrice = productPrice.toFloat()
    )
}