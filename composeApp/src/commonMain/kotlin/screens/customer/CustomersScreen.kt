package screens.customer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import model.Customer
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import stockmanagement.composeapp.generated.resources.Res

@Composable
fun CustomersRoute(
    onItemClick: (id: Long) -> Unit,
    onIconMenuClick: () -> Unit,
    onAddButtonClick: () -> Unit,
    viewModel: CustomersViewModel
) {
    val customers by viewModel.customers.collectAsState()

    LaunchedEffect(key1 = Unit) { viewModel.getCustomers() }

    CustomersScreen(
        customers = customers,
        onItemClick = onItemClick,
        onIconMenuClick = onIconMenuClick,
        onAddButtonClick = onAddButtonClick
    )
}

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterialApi::class)
@Composable
private fun CustomersScreen(
    customers: List<Customer>,
    onItemClick: (id: Long) -> Unit,
    onIconMenuClick: () -> Unit,
    onAddButtonClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(Res.string.customers_label)) },
                navigationIcon = {
                    IconButton(onClick = onIconMenuClick) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = stringResource(Res.string.drawer_menu_label)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddButtonClick) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(Res.string.add_button_label)
                )
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(items = customers, key = { customer -> customer.id }) { customer ->
                ListItem(
                    modifier = Modifier.clickable { onItemClick(customer.id) }
                ) {
                    Text(text = customer.name)
                }
            }
        }
    }
}