package com.bruno13palhano

import AppContainer
import data.SaleLocalData
import data.SaleRepository

class DesktopAppContainer : AppContainer {
    private val database = createDatabase(driverFactory = DriverFactory())
    override val saleRepository: SaleRepository =
        SaleRepository(data = SaleLocalData(saleQueries = database.saleQueries))
}