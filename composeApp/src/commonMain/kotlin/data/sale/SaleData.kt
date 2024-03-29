package data.sale

import data.Data
import kotlinx.coroutines.flow.Flow
import model.Sale

interface SaleData : Data<Sale> {
    fun getCustomerById(id: Long): Flow<List<Sale>>
}