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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.components.IntegerInputField
import screens.components.TextInputField
import stockmanagement.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
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

@OptIn(ExperimentalResourceApi::class)
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

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
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
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (id == 0L) viewModel.save() else viewModel.save(id = id)
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