package ninja.cue

import com.github.thomasnield.rxkotlinfx.toObservable
import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.scene.control.SeparatorMenuItem

import de.codecentric.centerdevice.MenuToolkit
import ninja.cue.persistance.DataManager

import ninja.cue.util.isMac

import ninja.cue.views.MainWindow

class Main : Application() {
    private val loader = FXMLLoader()
    private val root: Parent = loader.load(
            MainWindow::class.java.getResourceAsStream("MainWindow.fxml"))
    private val controller = loader.getController() as MainWindow
    private val scene = Scene(root, 900.0, 500.0)
    private val commonCss = javaClass.getResource("style.css").toExternalForm()
    private val darkCss = javaClass.getResource("dark.css").toExternalForm()
    private val dataManager = DataManager.instance
    override fun start(primaryStage: Stage?) {
        if(isMac()) {
            val mainMenu = controller.getMainMenu()
            val toolkit = MenuToolkit.toolkit()
            val appMenu = toolkit.createDefaultApplicationMenu("Cue.Ninja")

            appMenu.items.add(2, controller.preferencesMenuItem())
            appMenu.items.add(3, SeparatorMenuItem())

            mainMenu.menus.add(0, appMenu)

            toolkit.setGlobalMenuBar(mainMenu)
            toolkit.setApplicationMenu(appMenu)
        }

        dataManager.loadConfig()

        primaryStage?.title = "Cue.Ninja"

        updateStyle(dataManager.theme.value)
        DataManager.instance.theme.toObservable().subscribe(this::updateStyle)

        primaryStage?.scene = scene
        primaryStage?.show()

        primaryStage?.setOnCloseRequest {
            Platform.exit()
        }
    }

    private fun updateStyle(theme: String?) {
        scene.stylesheets.removeAll()
        scene.stylesheets.add(commonCss)
        if(theme == "dark") {
            scene.stylesheets.add(darkCss)
        }
    }

    companion object {
        @JvmStatic
        fun main(vararg args: String) {
            launch(Main::class.java, *args)
        }
    }
}
