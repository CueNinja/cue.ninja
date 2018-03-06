package ninja.cue

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

import ninja.cue.views.MainWindow

class Main : Application() {
    private val root: Parent = FXMLLoader.load(MainWindow::class.java.getResource("MainWindow.fxml"))
    private val scene = Scene(root, 900.0, 500.0)
    private val css = javaClass.getResource("style.css").toExternalForm()
    override fun start(primaryStage: Stage?) {
        primaryStage?.title = "Cue.Ninja"
        scene.stylesheets.add(css)
        primaryStage?.scene = scene
        primaryStage?.show()
    }

    companion object {
        @JvmStatic
        fun main(vararg args: String) {
            launch(Main::class.java, *args)
        }
    }
}
