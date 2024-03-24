package com.bruno13palhano

import AppContainer
import data.CustomerLocalData
import data.CustomerRepository
import data.SaleLocalData
import data.SaleRepository

class DesktopAppContainer : AppContainer {
    private val database = createDatabase(driverFactory = DriverFactory())

    override val saleRepository: SaleRepository =
        SaleRepository(data = SaleLocalData(saleQueries = database.saleQueries))

    override val customerRepository: CustomerRepository =
        CustomerRepository(data = CustomerLocalData(customerQueries = database.customerQueries))
}