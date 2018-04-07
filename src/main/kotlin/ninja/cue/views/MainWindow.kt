package ninja.cue.views

import javafx.beans.property.SimpleStringProperty
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.layout.VBox
import javafx.util.Callback

import de.jensd.shichimifx.utils.OS
import javafx.scene.control.*
import ninja.cue.monaco.Monaco

import ninja.cue.JdbcQuery

class MainWindow {
    @FXML private var monaco = Monaco()
    @FXML private var mainMenu = MenuBar()
    @FXML private var table = TableView<Array<String>>()
    @FXML private var vbox = VBox()
    @FXML private var preferencesSeparator = SeparatorMenuItem()
    @FXML private var preferencesMenuItem = MenuItem()

    @FXML fun executeQuery(event: ActionEvent) {
        val queryText = monaco.getContent()
        val query = JdbcQuery(queryText)
        val results = query.execute()

        table.columns.setAll(results.columns.map {
            val cell = TableColumn<Array<String>, String>(it)
            val index = results.columns.indexOf(it)
            cell.cellValueFactory = Callback { SimpleStringProperty(it.value[index]) }
            cell
        })

        table.items.clear()
        table.items.addAll(results.rows)
    }

    @FXML fun newTab(event: ActionEvent) {

    }

    @FXML fun closeTab(event: ActionEvent) {

    }

    @FXML fun showPreferences(events: ActionEvent) {

    }

    fun getMainMenu(): MenuBar {
        vbox.children.remove(mainMenu)
        return mainMenu
    }

    fun preferencesMenuItem(): MenuItem {
        mainMenu.menus[0].items.removeAll(preferencesSeparator, preferencesMenuItem)
        return preferencesMenuItem
    }

}
