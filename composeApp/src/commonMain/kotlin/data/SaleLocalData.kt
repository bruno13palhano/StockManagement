package data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.bruno13palhano.database.SaleQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import model.Sale

class SaleLocalData(private val saleQueries: SaleQueries) : Data<Sale> {
    override suspend fun insert(model: Sale) {
        saleQueries.insert(
            productName = model.productName,
            productPrice = model.productPrice.toDouble()
        )
    }

    override suspend fun update(model: Sale) {
        saleQueries.update(
            productName = model.productName,
            productPrice = model.productPrice.toDouble(),
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
        productName: String,
        productPrice: Double
    ) = Sale(
        id = id,
        productName = productName,
        productPrice = productPrice.toFloat()
    )
}