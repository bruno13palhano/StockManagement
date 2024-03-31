package com.bruno13palhano

import AppContainer
import data.customer.CustomerLocalData
import data.customer.CustomerRepository
import data.customer.CustomerRepositoryDefault
import data.sale.SaleLocalData
import data.sale.SaleRepository
import data.sale.SaleRepositoryDefault

class DesktopAppContainer : AppContainer {
    private val database = createDatabase(driverFactory = DriverFactory())

    override val saleRepository: SaleRepository =
        SaleRepositoryDefault(saleData = SaleLocalData(saleQueries = database.saleQueries))

    override val customerRepository: CustomerRepository =
        CustomerRepositoryDefault(
            customerData = CustomerLocalData(customerQueries = database.customerQueries)
        )
}