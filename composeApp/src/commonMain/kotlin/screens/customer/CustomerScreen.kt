package screens.customer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import screens.components.IntegerInputField
import screens.components.MoreOptionsMenu
import screens.components.TextInputField
import stockmanagement.composeapp.generated.resources.*

@Composable
fun NewCustomerRoute(
    onBackClick: () -> Unit,
    viewModel: CustomerViewModel
) {
    CustomerScreen(
        title = stringResource(Res.string.new_customer_label),
        id = 0L,
        onBackClick = onBackClick,
        viewModel = viewModel
    )
}

@Composable
fun EditCustomerRoute(
    id: Long,
    onBackClick: () -> Unit,
    viewModel: CustomerViewModel
) {
    LaunchedEffect(key1 = Unit) { viewModel.getCustomer(id = id) }

    CustomerScreen(
        title = stringResource(Res.string.edit_customer_label),
        id = id,
        onBackClick = onBackClick,
        viewModel = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomerScreen(
    title: String,
    id: Long,
    onBackClick: () -> Unit,
    viewModel: CustomerViewModel
) {
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

                    if (!isNewCustomer(id = id)) {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = stringResource(
                                    Res.string.more_options_menu_label
                                )
                            )

                            val items = arrayOf(
                                stringResource(Res.string.save_label),
                                stringResource(Res.string.delete_label)
                            )

                            MoreOptionsMenu(
                                items = items,
                                expanded = expanded,
                                onDismissRequest = { expanded = it },
                                onClick = { index ->
                                    when (index) {
                                        CustomerOptionsMenu.SAVE -> {
                                            viewModel.save(id = id)
                                            onBackClick()
                                        }

                                        CustomerOptionsMenu.DELETE -> {
                                            viewModel.delete(id = id)
                                            onBackClick()
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
                    onBackClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = stringResource(Res.string.done_button_label)
                )
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            TextInputField(
                value = viewModel.name,
                onValueChange = viewModel::updateName,
                icon = Icons.Filled.Title,
                label = stringResource(Res.string.customer_name_label),
                placeholder = stringResource(Res.string.enter_customer_name_label)
            )
            TextInputField(
                value = viewModel.email,
                onValueChange = viewModel::updateEmail,
                icon = Icons.Filled.Email,
                label = stringResource(Res.string.email_label),
                placeholder = stringResource(Res.string.enter_email_label)
            )
            TextInputField(
                value = viewModel.address,
                onValueChange = viewModel::updateAddress,
                icon = Icons.Filled.LocationCity,
                label = stringResource(Res.string.address_label),
                placeholder = stringResource(Res.string.enter_address_label)
            )
            TextInputField(
                value = viewModel.city,
                onValueChange = viewModel::updateCity,
                icon = Icons.Filled.Place,
                label = stringResource(Res.string.city_label),
                placeholder = stringResource(Res.string.enter_city_label)
            )
            IntegerInputField(
                value = viewModel.phoneNumber,
                onValueChange = viewModel::updatePhoneNumber,
                icon = Icons.Filled.Phone,
                label = stringResource(Res.string.phone_number_label),
                placeholder = stringResource(Res.string.enter_phone_number_label)
            )
            TextInputField(
                value = viewModel.gender,
                onValueChange = viewModel::updateGender,
                icon = Icons.Filled.Person,
                label = stringResource(Res.string.gender_label),
                placeholder = stringResource(Res.string.enter_gender_label)
            )
            IntegerInputField(
                value = viewModel.age,
                onValueChange = viewModel::updateAge,
                icon = Icons.Filled.Cake,
                label = stringResource(Res.string.age_label),
                placeholder = stringResource(Res.string.enter_age_label)
            )
        }
    }
}

private fun isNewCustomer(id: Long) = id == 0L

private object CustomerOptionsMenu {
    const val SAVE = 0
    const val DELETE = 1
}