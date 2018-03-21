package ninja.cue.views

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.MenuBar
import ninja.cue.monaco.Monaco

class MainWindow {
    @FXML private var monaco = Monaco()
    @FXML private var mainMenu = MenuBar()

    fun initialize() {
        mainMenu.useSystemMenuBarProperty().set(true)
    }

    @FXML fun reset(event: ActionEvent) {
        monaco.setContent("-- Type code here!")
    }

    @FXML fun debugger(actionEvent: ActionEvent) {
        //monaco.startDebugger()
    }

}
