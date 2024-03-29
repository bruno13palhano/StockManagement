package data.customer

import data.Data
import data.Repository
import kotlinx.coroutines.flow.Flow
import model.Customer

class CustomerRepository(private val data: Data<Customer>) : Repository<Customer> {
    override suspend fun insert(model: Customer) = data.insert(model = model)

    override suspend fun update(model: Customer) = data.update(model = model)

    override suspend fun delete(id: Long) = data.delete(id = id)

    override fun getById(id: Long): Flow<Customer> = data.getById(id = id)

    override fun getAll(): Flow<List<Customer>> = data.getAll()
}