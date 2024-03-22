package screens.sales

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Title
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.components.FloatInputField
import screens.components.TextInputField
import stockmanagement.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NewSaleRoute(
    onDoneButtonClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: SaleViewModel
) {
    SaleScreen(
        id = 0L,
        title = stringResource(Res.string.new_sale_label),
        onDoneButtonClick = onDoneButtonClick,
        onBackClick = onBackClick,
        viewModel = viewModel
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun EditSaleRoute(
    id: Long,
    onDoneButtonClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: SaleViewModel
) {
    SaleScreen(
        id = id,
        title = stringResource(Res.string.edit_sale_label),
        onBackClick = onBackClick,
        onDoneButtonClick = onDoneButtonClick,
        viewModel = viewModel
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun SaleScreen(
    id: Long,
    title: String,
    onDoneButtonClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: SaleViewModel
) {
    if (id != 0L) {
        LaunchedEffect(key1 = Unit) {
            viewModel.getSale(id = id)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.navigate_back_label)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.save(id = id)
                    onDoneButtonClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = stringResource(Res.string.done_button_label)
                )
            }
        }
    ) {
        Column(modifier = Modifier.padding(it).padding(vertical = 4.dp)) {
            TextInputField(
                value = viewModel.productName,
                onValueChange = viewModel::updateProductName,
                icon = Icons.Filled.Title,
                label = stringResource(Res.string.product_name_label),
                placeholder = stringResource(Res.string.enter_product_name_label)
            )
            FloatInputField(
                value = viewModel.productPrice,
                onValueChange = viewModel::updateProductPrice,
                icon = Icons.Filled.Paid,
                label = stringResource(Res.string.product_price_label),
                placeholder = stringResource(Res.string.enter_product_price_label)
            )
        }
    }
}