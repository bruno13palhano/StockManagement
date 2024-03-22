import data.SaleRepository

interface AppContainer {
    val saleRepository: SaleRepository
}

expect val appContainer: AppContainer