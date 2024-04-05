package data.sale

import data.Repository
import kotlinx.coroutines.flow.Flow
import model.Sale

interface SaleRepository : Repository<Sale> {
    fun getSales(offset: Int, limit: Int): Flow<List<Sale>>
    fun getByCustomerId(id: Long): Flow<List<Sale>>
}