package com.bruno13palhano

import AppContainer
import android.app.Application
import data.customer.CustomerLocalData
import data.customer.CustomerRepository
import data.sale.SaleLocalData
import data.sale.SaleRepository
import data.sale.SaleRepositoryDefault

class AndroidAppContainer : Application(), AppContainer {
    private val database: Database = createDatabase(DriverFactory().apply { setContext(this@AndroidAppContainer) })

    override val saleRepository: SaleRepository =
        SaleRepositoryDefault(saleData = SaleLocalData(saleQueries = database.saleQueries))

    override val customerRepository: CustomerRepository =
        CustomerRepository(data = CustomerLocalData(customerQueries = database.customerQueries))
}
