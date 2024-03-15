import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MainViewModel {
    var inputText by mutableStateOf("Default text")
        private set

    fun updateInputText(inputText: String) {
        this.inputText = inputText
    }
}