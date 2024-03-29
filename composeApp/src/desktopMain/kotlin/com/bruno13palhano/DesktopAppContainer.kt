package com.bruno13palhano

import AppContainer
import data.customer.CustomerLocalData
import data.customer.CustomerRepository
import data.sale.SaleLocalData
import data.sale.SaleRepository

class DesktopAppContainer : AppContainer {
    private val database = createDatabase(driverFactory = DriverFactory())

    override val saleRepository: SaleRepository =
        SaleRepository(data = SaleLocalData(saleQueries = database.saleQueries))

    override val customerRepository: CustomerRepository =
        CustomerRepository(data = CustomerLocalData(customerQueries = database.customerQueries))
}