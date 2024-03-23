package data

import kotlinx.coroutines.flow.Flow
import model.Customer

class CustomerRepository(
    private val customerData: Data<Customer>
) : Repository<Customer> {
    override suspend fun insert(model: Customer) = customerData.insert(model = model)

    override suspend fun update(model: Customer) = customerData.update(model = model)

    override suspend fun delete(id: Long) = customerData.delete(id = id)

    override fun getById(id: Long): Flow<Customer> = customerData.getById(id = id)

    override fun getAll(): Flow<List<Customer>> = customerData.getAll()
}