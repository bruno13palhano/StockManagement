package data

import kotlinx.coroutines.flow.Flow
import model.Sale

class SaleRepository(private val data: Data) : Repository {
    override suspend fun insert(sale: Sale) {
        data.insert(sale = sale)
    }

    override suspend fun update(sale: Sale) {
        data.update(sale = sale)
    }

    override suspend fun delete(id: Long) {
        data.delete(id = id)
    }

    override fun getById(id: Long): Flow<Sale> {
        return data.getById(id = id)
    }

    override fun getAll(): Flow<List<Sale>> {
        return data.getAll()
    }

}