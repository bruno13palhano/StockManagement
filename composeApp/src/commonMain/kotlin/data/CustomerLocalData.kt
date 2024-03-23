package data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.bruno13palhano.database.CustomerQueries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import model.Customer

class CustomerLocalData(
    private val customerQueries: CustomerQueries,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : Data<Customer> {
    override suspend fun insert(model: Customer) =
        customerQueries.insert(
            name = model.name,
            email = model.email,
            address = model.address,
            city = model.city,
            phoneNumber = model.phoneNumber,
            gender = model.gender,
            age = model.age.toLong()
        )

    override suspend fun update(model: Customer) =
        customerQueries.update(
            name = model.name,
            email = model.email,
            address = model.address,
            city = model.city,
            phoneNumber = model.phoneNumber,
            gender = model.gender,
            age = model.age.toLong(),
            id = model.id
        )

    override suspend fun delete(id: Long) = customerQueries.delete(id = id)

    override fun getById(id: Long): Flow<Customer> =
        customerQueries.getById(id = id, mapper = ::mapToCustomer)
            .asFlow()
            .mapToOne(context = dispatcher)
            .catch { it.printStackTrace() }

    override fun getAll(): Flow<List<Customer>> =
        customerQueries.getAll(mapper = ::mapToCustomer).asFlow().mapToList(context = dispatcher)


    private fun mapToCustomer(
        id: Long,
        name: String,
        email: String,
        address: String,
        city: String,
        phoneNumber: String,
        gender: String,
        age: Long
    ) = Customer(
        id = id,
        name = name,
        email = email,
        address = address,
        city = city,
        phoneNumber = phoneNumber,
        gender = gender,
        age = age.toInt()
    )
}