package data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import model.Sale

class SaleLocalData : Data {
    private val sales = mutableListOf<Sale>(
        Sale(id = 1L, productName = "Homem", productPrice = 123.45F),
        Sale(id = 2L, productName = "Essencial", productPrice = 88.99F),
        Sale(id = 3L, productName = "Kaiak", productPrice = 133.75F)
    )

    override suspend fun insert(sale: Sale) {
        sales.add(sale)
    }

    override suspend fun update(sale: Sale) {
        for (i in 0 until sales.size) {
            if (sales[i].id == sale.id ) {
                sales[i] = sale
                break
            }
        }
    }

    override suspend fun delete(id: Long) {
        for (i in 0 until sales.size) {
            if (sales[i].id == id ) {
                sales.remove(sales[i])
                break
            }
        }
    }

    override fun getById(id: Long): Flow<Sale> {
        var currentSale = Sale(0L, "", 0F)

        for (i in 0 until sales.size) {
            if (sales[i].id == id ) {
                currentSale = sales[i]
                break
            }
        }

        return flowOf(currentSale)
    }

    override fun getAll(): Flow<List<Sale>> {
        return flowOf(sales)
    }
}