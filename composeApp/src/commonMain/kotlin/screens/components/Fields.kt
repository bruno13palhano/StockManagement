package screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.DecimalFormat
import java.util.Locale

@Composable
fun TextInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (value: String) -> Unit,
    icon: ImageVector,
    label: String,
    placeholder: String,
    singleLine: Boolean = true,
    readOnly: Boolean = false
) {
    OutlinedTextField(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        leadingIcon = { Icon(imageVector = icon, contentDescription = label) },
        label = { Text(text = label, fontStyle = FontStyle.Italic) },
        placeholder = { Text(text = placeholder, fontStyle = FontStyle.Italic) },
        singleLine = singleLine,
        readOnly = readOnly,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { defaultKeyboardAction(ImeAction.Done) })
    )
}

@Composable
fun FloatInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (value: String) -> Unit,
    icon: ImageVector,
    label: String,
    placeholder: String,
    singleLine: Boolean = true,
    readOnly: Boolean = false
) {
    val decimalFormat = DecimalFormat.getInstance(Locale.getDefault()) as DecimalFormat
    val decimalSeparator = decimalFormat.decimalFormatSymbols.decimalSeparator
    val pattern = remember { Regex("^\\d*\\$decimalSeparator?\\d*\$") }

    OutlinedTextField(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth(),
        value = value,
        onValueChange =  { newValue: String ->
            if (newValue.isBlank() || newValue.matches(pattern)) {
                onValueChange(newValue)
            }
        },
        leadingIcon = { Icon(imageVector = icon, contentDescription = label) },
        label = { Text(text = label, fontStyle = FontStyle.Italic) },
        placeholder = { Text(text = placeholder, fontStyle = FontStyle.Italic) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Decimal
        ),
        keyboardActions = KeyboardActions(onDone = {
            defaultKeyboardAction(ImeAction.Done)
        }),
        singleLine = singleLine,
        readOnly = readOnly
    )
}

@Composable
fun IntegerInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (value: String) -> Unit,
    icon: ImageVector,
    label: String,
    placeholder: String,
    singleLine: Boolean = true,
    readOnly: Boolean = false
) {
    val patternInt = remember { Regex("^\\d*") }

    OutlinedTextField(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 2.dp)
            .fillMaxWidth(),
        value = value,
        onValueChange = { newValue ->
            if (newValue.isEmpty() || newValue.matches(patternInt)) {
                onValueChange(newValue)
            }
        },
        leadingIcon = { Icon(imageVector = icon, contentDescription = label) },
        label = { Text(text = label, fontStyle = FontStyle.Italic) },
        placeholder = { Text(text = placeholder, fontStyle = FontStyle.Italic) },
        singleLine = singleLine,
        readOnly = readOnly,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(onDone = {
            defaultKeyboardAction(ImeAction.Done)
        })
    )
}