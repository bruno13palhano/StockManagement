package data

import kotlinx.coroutines.flow.Flow
import model.Sale

interface Data {
    suspend fun insert(sale: Sale)
    suspend fun update(sale: Sale)
    suspend fun delete(id: Long)
    fun getById(id: Long): Flow<Sale>
    fun getAll(): Flow<List<Sale>>
}