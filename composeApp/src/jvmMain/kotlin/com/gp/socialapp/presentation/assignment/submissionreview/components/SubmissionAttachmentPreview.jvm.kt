package com.gp.socialapp.presentation.assignment.submissionreview.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import dev.zt64.compose.pdf.component.PdfColumn
import dev.zt64.compose.pdf.rememberLocalPdfState
import java.net.URL


@Composable
actual fun PdfPreview(
    modifier: Modifier,
    attachment: AssignmentAttachment
) {
    val pdfState = rememberLocalPdfState(url = URL(attachment.url))
    PdfColumn(
        modifier = Modifier.fillMaxSize().then(modifier),
        state = pdfState,
    )
}
//@Composable
//actual fun PdfPreview(
//    modifier: Modifier,
//    attachment: AssignmentAttachment
//) {
//    val window = CURRENT_WINDOW
//    val finishListener = object : PlatformImpl.FinishListener {
//        override fun idle(implicitExit: Boolean) {}
//        override fun exitCalled() {}
//    }
//    PlatformImpl.addListener(finishListener)
//    val jfxPanel = remember { JFXPanel() }
//    var jsObject = remember<JSObject?> { null }
//    val counter = remember { mutableStateOf(0) }
//    val jfxtext = remember { JFXText() }
//    val inc: () -> Unit = {
//        counter.value++
//        // update JavaFX text component
//        Platform.runLater {
//            jfxtext.text = "Welcome JavaFX! ${counter.value}"
//        }
//    }
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Column(
//            modifier = Modifier.padding(top = 80.dp, bottom = 20.dp)
//        ) {
//            Spacer(modifier = Modifier.height(20.dp))
//            // The "Box" is strictly necessary to properly sizing and positioning the JFXPanel container.
//            Box(
//                modifier = Modifier.height(200.dp).fillMaxWidth()
//            ) {
//                JavaFXPanel(
//                    root = window,
//                    panel = jfxPanel,
//                    // function to initialize JFXPanel, Group, Scene
//                    onCreate = {
//                        Platform.runLater {
//                            val root = WebView()
//                            val engine = root.engine
//                            val scene = Scene(root)
//                            engine.loadWorker.stateProperty().addListener { _, _, newState ->
//                                if (newState === Worker.State.SUCCEEDED) {
//                                    jsObject = root.engine.executeScript("window") as JSObject
//                                    // execute other javascript / setup js callbacks fields etc..
//                                }
//                            }
//                            engine.loadWorker.exceptionProperty().addListener { _, _, newError ->
//                                println("page load error : $newError")
//                            }
//                            jfxPanel.scene = scene
//                            engine.load("https://www.google.com/") // can be a html document from resources ..
//                            engine.setOnError { error -> println("onError : $error") }
//                        }
//                    }
//                )
//            }
//            Spacer(modifier = Modifier.height(20.dp))
//        }
//    }
//}
//
//@Composable
//private fun JavaFXPanel(
//    root: Container,
//    panel: JFXPanel,
//    onCreate: () -> Unit
//) {
//    val container = remember { JPanel() }
//    val density = LocalDensity.current.density
//
//    Layout(
//        content = {},
//        modifier = Modifier.onGloballyPositioned { childCoordinates ->
//            val coordinates = childCoordinates.parentCoordinates!!
//            val location = coordinates.localToWindow(Offset.Zero).round()
//            val size = coordinates.size
//            container.setBounds(
//                (location.x / density).toInt(),
//                (location.y / density).toInt(),
//                (size.width / density).toInt(),
//                (size.height / density).toInt()
//            )
//            container.validate()
//            container.repaint()
//        },
//        measurePolicy = { _, _ ->
//            layout(0, 0) {}
//        }
//    )
//
//    DisposableEffect(Unit) {
//        container.apply {
//            layout = BorderLayout(0, 0)
//            add(panel)
//        }
//        root.add(container)
//        onCreate.invoke()
//        onDispose {
//            root.remove(container)
//        }
//    }
//}