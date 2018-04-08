package ninja.cue

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.scene.control.SeparatorMenuItem

import de.jensd.shichimifx.utils.OS
import de.codecentric.centerdevice.MenuToolkit
import ninja.cue.persistance.DataManager

import ninja.cue.views.MainWindow

class Main : Application() {
    private val loader = FXMLLoader()
    private val root: Parent = loader.load(
            MainWindow::class.java.getResource("MainWindow.fxml").openStream())
    private val controller = loader.getController() as MainWindow
    private val scene = Scene(root, 900.0, 500.0)
    private val css = javaClass.getResource("style.css").toExternalForm()
    private val darkCss = javaClass.getResource("dark.css").toExternalForm()
    override fun start(primaryStage: Stage?) {
        if(OS.isMac()) {
            val mainMenu = controller.getMainMenu()
            val toolkit = MenuToolkit.toolkit()
            val appMenu = toolkit.createDefaultApplicationMenu("Cue.Ninja")

            appMenu.items.add(2, controller.preferencesMenuItem())
            appMenu.items.add(3, SeparatorMenuItem())

            mainMenu.menus.add(0, appMenu)

            toolkit.setGlobalMenuBar(mainMenu)
            toolkit.setApplicationMenu(appMenu)
        }

        DataManager.instance.loadConfig()

        primaryStage?.title = "Cue.Ninja"
        scene.stylesheets.add(css)
        if(DataManager.instance.theme.get() == "dark") {
            scene.stylesheets.add(darkCss)
        }
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
