import data.customer.CustomerRepository
import data.sale.SaleRepository

interface AppContainer {
    val saleRepository: SaleRepository
    val customerRepository: CustomerRepository
}

expect val appContainer: AppContainer