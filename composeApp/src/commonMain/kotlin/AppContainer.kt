import data.CustomerRepository
import data.SaleRepository

interface AppContainer {
    val saleRepository: SaleRepository
    val customerRepository: CustomerRepository
}

expect val appContainer: AppContainer