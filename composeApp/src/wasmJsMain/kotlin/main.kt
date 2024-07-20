import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    // 方式一：传入Element
    ComposeViewport(document.body!!) {
        App()
    }

    // 方式二：使用id。对应index.html中<div id="composeApplication" />元素
//    ComposeViewport(viewportContainerId = "composeApplication") {
//        App()
//    }
}