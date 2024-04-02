package screens.sales

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AreaChart
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.components.BottomSheet
import screens.components.ClickField
import screens.components.FloatInputField
import screens.components.IntegerInputField
import screens.components.MoreOptionsMenu
import screens.components.TextInputField
import screens.currentDate
import screens.dateFormat
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

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun SaleScreen(
    id: Long,
    title: String,
    onDoneButtonClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: SaleViewModel
) {

    LaunchedEffect(key1 = Unit) { viewModel.getAllCustomers() }

    LaunchedEffect(key1 = Unit) {
        if (id != 0L) {
            viewModel.getSale(id = id)
        } else {
            viewModel.updateDateOfSale(currentDate)
            viewModel.updateDateOfPayment(currentDate)
            viewModel.updateDateOfShipping(currentDate)
            viewModel.updateDateOfDelivery(currentDate)
        }
    }

    val totalProfit by viewModel.totalProfit.collectAsState()
    val amazonProfit by viewModel.amazonProfit.collectAsState()

    val focusManager = LocalFocusManager.current

    var  openCustomerSheet by remember { mutableStateOf(false) }
    var dateOfSalePickerState = rememberDatePickerState()
    var showDateOfSalePickerState by remember { mutableStateOf(false) }
    var dateOfPaymentPickerState = rememberDatePickerState()
    var showDateOfPaymentPickerState by remember { mutableStateOf(false) }
    var dateOfShippingPickerState = rememberDatePickerState()
    var showDateOfShippingPickerState by remember { mutableStateOf(false) }
    var dateOfDeliveryPickerState = rememberDatePickerState()
    var showDateOfDeliveryPickerState by remember { mutableStateOf(false) }

    if (showDateOfSalePickerState) {
        DatePickerDialog(
            onDismissRequest = {
                showDateOfSalePickerState = false
                focusManager.clearFocus(force = true)
            },
            confirmButton = {
                Button(
                    onClick = {
                        dateOfSalePickerState.selectedDateMillis?.let {
                            viewModel.updateDateOfSale(date = it)
                        }
                        focusManager.clearFocus(force = true)
                        showDateOfSalePickerState = false
                    }
                ) {
                    Text(text = stringResource(Res.string.date_of_sale_label))
                }
            }
        ) {
            dateOfSalePickerState = rememberDatePickerState(
                initialSelectedDateMillis = viewModel.dateOfSale,
                initialDisplayMode = DisplayMode.Picker
            )
            DatePicker(
                state = dateOfSalePickerState,
                showModeToggle = true
            )
        }
    }

    if (showDateOfPaymentPickerState) {
        DatePickerDialog(
            onDismissRequest = {
                showDateOfPaymentPickerState = false
                focusManager.clearFocus(force = true)
            },
            confirmButton = {
                Button(
                    onClick = {
                        dateOfPaymentPickerState.selectedDateMillis?.let {
                            viewModel.updateDateOfPayment(date = it)
                        }
                        focusManager.clearFocus(force = true)
                        showDateOfPaymentPickerState = false
                    }
                ) {
                    Text(text = stringResource(Res.string.date_of_payment_label))
                }
            }
        ) {
            dateOfPaymentPickerState = rememberDatePickerState(
                initialSelectedDateMillis = viewModel.dateOfPayment,
                initialDisplayMode = DisplayMode.Picker
            )
            DatePicker(
                state = dateOfPaymentPickerState,
                showModeToggle = true
            )
        }
    }

    if (showDateOfShippingPickerState) {
        DatePickerDialog(
            onDismissRequest = {
                showDateOfShippingPickerState = false
                focusManager.clearFocus(force = true)
            },
            confirmButton = {
                Button(
                    onClick = {
                        dateOfShippingPickerState.selectedDateMillis?.let {
                            viewModel.updateDateOfShipping(date = it)
                        }
                        focusManager.clearFocus(force = true)
                        showDateOfShippingPickerState = false
                    }
                ) {
                    Text(text = stringResource(Res.string.date_of_shipping_label))
                }
            }
        ) {
            dateOfShippingPickerState = rememberDatePickerState(
                initialSelectedDateMillis = viewModel.dateOfShipping,
                initialDisplayMode = DisplayMode.Picker
            )
            DatePicker(
                state = dateOfShippingPickerState,
                showModeToggle = true
            )
        }
    }

    if (showDateOfDeliveryPickerState) {
        DatePickerDialog(
            onDismissRequest = {
                showDateOfDeliveryPickerState = false
                focusManager.clearFocus(force = true)
            },
            confirmButton = {
                Button(
                    onClick = {
                        dateOfDeliveryPickerState.selectedDateMillis?.let {
                            viewModel.updateDateOfDelivery(date = it)
                        }
                        focusManager.clearFocus(force = true)
                        showDateOfDeliveryPickerState = false
                    }
                ) {
                    Text(text = stringResource(Res.string.date_of_delivery_label))
                }
            }
        ) {
            dateOfDeliveryPickerState = rememberDatePickerState(
                initialSelectedDateMillis = viewModel.dateOfDelivery,
                initialDisplayMode = DisplayMode.Picker
            )
            DatePicker(
                state = dateOfDeliveryPickerState,
                showModeToggle = true
            )
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
                },
                actions = {
                    var expanded by remember { mutableStateOf(false) }

                    if (id != 0L) {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = null
                            )

                            val items = arrayOf(
                                stringResource(Res.string.save_label),
                                stringResource(Res.string.cancel_label)
                            )
                            MoreOptionsMenu(
                                items = items,
                                expanded = expanded,
                                onDismissRequest = { expanded = it },
                                onClick = { index ->
                                    when (index) {
                                        SaleOptionsMenu.SAVE -> {
                                            onBackClick()
                                            viewModel.save(id = id)
                                        }

                                        SaleOptionsMenu.CANCEL -> {
                                            onBackClick()
                                            viewModel.cancelSale(id = id)
                                        }
                                    }
                                }
                            )
                        }
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
        Column(
            modifier = Modifier
                .padding(it).padding(vertical = 4.dp)
                .verticalScroll(state = rememberScrollState(), enabled = true)
        ) {
            AnimatedVisibility(visible = openCustomerSheet) {
                BottomSheet(
                    onDismissBottomSheet = {
                        openCustomerSheet = false
                        focusManager.clearFocus(force = true)
                    }
                ) {
                    val initialCustomer = viewModel.allCustomers
                        .filter { customer -> customer.isChecked }
                        .findLast { customer -> customer.isChecked }?.name
                    val (selected, onOptionSelected) = rememberSaveable {
                        mutableStateOf(initialCustomer)
                    }

                    Column(modifier = Modifier.padding(bottom = 32.dp)) {
                        viewModel.allCustomers.forEach { customerCheck ->
                            ListItem(
                                headlineContent = { Text(text = customerCheck.name) },
                                leadingContent = {
                                    RadioButton(
                                        selected = customerCheck.name == selected,
                                        onClick = {
                                            onOptionSelected(customerCheck.name)
                                            viewModel.updateCustomerName(customerCheck.name)
                                        }
                                    )
                                }
                            )
                        }
                    }
                }
            }
            TextInputField(
                value = viewModel.productName,
                onValueChange = viewModel::updateProductName,
                icon = Icons.Filled.Title,
                label = stringResource(Res.string.product_name_label),
                placeholder = stringResource(Res.string.enter_product_name_label)
            )
            ClickField(
                value = viewModel.customerName,
                onClick = { openCustomerSheet = true },
                icon = Icons.Filled.Person,
                label = stringResource(Res.string.customer_name_label),
                placeholder = ""
            )
            FloatInputField(
                value = viewModel.purchasePrice,
                onValueChange = viewModel::updatePurchasePrice,
                icon = Icons.Filled.Paid,
                label = stringResource(Res.string.purchase_price_label),
                placeholder = stringResource(Res.string.enter_purchase_price_label)
            )
            FloatInputField(
                value = viewModel.salePrice,
                onValueChange = viewModel::updateSalePrice,
                icon = Icons.Filled.Paid,
                label = stringResource(Res.string.sale_price_label),
                placeholder = stringResource(Res.string.enter_sale_price_label)
            )
            IntegerInputField(
                value = viewModel.quantity,
                onValueChange = viewModel::updateQuantity,
                icon = Icons.Filled.ShoppingBag,
                label = stringResource(Res.string.quantity_label),
                placeholder = stringResource(Res.string.enter_quantity_label)
            )
            FloatInputField(
                value = viewModel.deliveryPrice,
                onValueChange = viewModel::updateDeliveryPrice,
                icon = Icons.Filled.Paid,
                label = stringResource(Res.string.delivery_price_label),
                placeholder = stringResource(Res.string.enter_delivery_price_label)
            )
            TextInputField(
                value = viewModel.amazonCode,
                onValueChange = viewModel::updateAmazonCode,
                icon = Icons.Filled.Code,
                label = stringResource(Res.string.amazon_code_label),
                placeholder = stringResource(Res.string.enter_amazon_code_label)
            )
            TextInputField(
                value = viewModel.sku,
                onValueChange = viewModel::updateSku,
                icon = Icons.Filled.QrCodeScanner,
                label = stringResource(Res.string.sku_label),
                placeholder = stringResource(Res.string.enter_sku_label)
            )
            IntegerInputField(
                value = viewModel.requestNumber,
                onValueChange = viewModel::updateRequestNumber,
                icon = Icons.Filled.Numbers,
                label = stringResource(Res.string.request_number_label),
                placeholder = stringResource(Res.string.enter_request_number_label)
            )
            IntegerInputField(
                value = viewModel.tax,
                onValueChange = viewModel::updateTax,
                icon = Icons.Filled.Percent,
                label = stringResource(Res.string.tax_label),
                placeholder = stringResource(Res.string.enter_tax_label)
            )
            FloatInputField(
                value = amazonProfit,
                onValueChange = {},
                icon = Icons.Filled.BarChart,
                label = stringResource(Res.string.amazon_profit_label),
                placeholder = ""
            )
            FloatInputField(
                value = viewModel.resaleProfit,
                onValueChange = viewModel::updateResaleProfit,
                icon = Icons.Filled.AreaChart,
                label = stringResource(Res.string.resale_profit_label),
                placeholder = stringResource(Res.string.enter_resale_profit_label)
            )
            FloatInputField(
                value = totalProfit,
                onValueChange = {},
                icon = Icons.Filled.PieChart,
                label = stringResource(Res.string.total_profit_label),
                placeholder = ""
            )
            ClickField(
                value = dateFormat.format(viewModel.dateOfSale),
                onClick = { showDateOfSalePickerState = true },
                icon = Icons.Filled.CalendarToday,
                label = stringResource(Res.string.date_of_sale_label),
                placeholder = ""
            )
            ClickField(
                value = dateFormat.format(viewModel.dateOfPayment),
                onClick = { showDateOfPaymentPickerState = true },
                icon = Icons.Filled.CalendarToday,
                label = stringResource(Res.string.date_of_payment_label),
                placeholder = ""
            )
            ClickField(
                value = dateFormat.format(viewModel.dateOfShipping),
                onClick = { showDateOfShippingPickerState = true },
                icon = Icons.Filled.CalendarToday,
                label = stringResource(Res.string.date_of_shipping_label),
                placeholder = ""
            )
            ClickField(
                value = dateFormat.format(viewModel.dateOfDelivery),
                onClick = { showDateOfDeliveryPickerState = true },
                icon = Icons.Filled.CalendarToday,
                label = stringResource(Res.string.date_of_delivery_label),
                placeholder = ""
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = viewModel.isPaid,
                    onCheckedChange = viewModel::updateIsPaid
                )
                Text(text = stringResource(Res.string.is_paid_label))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = viewModel.delivered,
                    onCheckedChange = viewModel::updateDelivered
                )
                Text(text = stringResource(Res.string.delivered_label))
            }
        }
    }
}

private object SaleOptionsMenu {
    const val SAVE = 0
    const val CANCEL = 1
}