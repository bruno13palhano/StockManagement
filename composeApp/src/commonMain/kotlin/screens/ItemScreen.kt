package screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ItemScreen(
    title: String,
    navigateUp: () -> Unit
) {
    Column {
        Button(onClick = navigateUp) {
            Text(text = title)
        }
    }
}