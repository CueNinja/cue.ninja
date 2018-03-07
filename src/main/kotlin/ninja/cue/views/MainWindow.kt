package ninja.cue.views

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.MenuBar
import javafx.scene.web.WebView

class MainWindow {
    @FXML private var monaco = Monaco()
    @FXML private var mainMenu = MenuBar()

    fun initialize() {
        mainMenu.useSystemMenuBarProperty().set(true)
    }

    fun reset(event: ActionEvent) {
        monaco.setContent("-- Type code here!")
    }

}
