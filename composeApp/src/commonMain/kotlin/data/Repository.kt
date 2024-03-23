package data

import kotlinx.coroutines.flow.Flow

interface Repository<T> {
    suspend fun insert(model: T)
    suspend fun update(model: T)
    suspend fun delete(id: Long)
    fun getById(id: Long): Flow<T>
    fun getAll(): Flow<List<T>>
}