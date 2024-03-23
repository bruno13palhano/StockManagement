package data

import kotlinx.coroutines.flow.Flow
import model.Sale

class SaleRepository(private val data: Data<Sale>) : Repository<Sale> {
    override suspend fun insert(model: Sale) {
        data.insert(model = model)
    }

    override suspend fun update(model: Sale) {
        data.update(model = model)
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