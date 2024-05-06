package com.gp.socialapp.presentation.assignment.submissionreview.components

import CURRENT_WINDOW
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import com.gp.socialapp.data.assignment.model.AssignmentAttachment
import com.sun.javafx.application.PlatformImpl
import javafx.embed.swing.JFXPanel
import netscape.javascript.JSObject
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import dev.zt64.compose.pdf.component.PdfColumn
import dev.zt64.compose.pdf.rememberLocalPdfState
import javafx.application.Platform
import javafx.concurrent.Worker
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.web.WebView
import java.awt.BorderLayout
import java.awt.Container
import java.net.URL
import javax.swing.JPanel
import javafx.scene.paint.Color as JFXColor
import javafx.scene.text.Font as JFXFont
import javafx.scene.text.Text as JFXText


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