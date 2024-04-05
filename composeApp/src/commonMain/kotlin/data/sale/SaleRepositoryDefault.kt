package data.sale

import kotlinx.coroutines.flow.Flow
import model.Sale

class SaleRepositoryDefault(private val saleData: SaleData) : SaleRepository {
    override suspend fun insert(model: Sale) {
        saleData.insert(model = model)
    }

    override suspend fun update(model: Sale) {
        saleData.update(model = model)
    }

    override suspend fun delete(id: Long) {
        saleData.delete(id = id)
    }

    override fun getById(id: Long): Flow<Sale> {
        return saleData.getById(id = id)
    }

    override fun getAll(): Flow<List<Sale>> {
        return saleData.getAll()
    }

    override fun getSales(offset: Int, limit: Int): Flow<List<Sale>> {
        TODO("Not yet implemented")
    }

    override fun getByCustomerId(id: Long): Flow<List<Sale>> {
        return saleData.getCustomerById(id = id)
    }
}