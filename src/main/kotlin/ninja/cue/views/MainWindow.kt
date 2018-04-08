package ninja.cue.views

import javafx.beans.property.SimpleStringProperty
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.util.Callback

import ninja.cue.monaco.Monaco
import org.controlsfx.control.MasterDetailPane

import ninja.cue.jdbc.Query


class MainWindow {
    @FXML private var mainBorderPane = BorderPane()
    @FXML private var monaco = Monaco()
    @FXML private var mainMenu = MenuBar()
    @FXML private var table = TableView<Array<String>>()
    @FXML private var vbox = VBox()
    @FXML private var preferencesSeparator = SeparatorMenuItem()
    @FXML private var preferencesMenuItem = MenuItem()
    @FXML private var masterDetail = MasterDetailPane()

    @FXML fun executeQuery(event: ActionEvent) {
        val queryText = monaco.getContent()
        val query = Query(queryText)
        val results = query.execute()

        table.columns.setAll(results.columns.map {
            val cell = TableColumn<Array<String>, String>(it)
            val index = results.columns.indexOf(it)
            cell.cellValueFactory = Callback { SimpleStringProperty(it.value[index]) }
            cell
        })

        table.items.clear()
        table.items.addAll(results.rows)
        masterDetail.isShowDetailNode = true
    }

    @FXML fun newTab(event: ActionEvent) {

    }

    @FXML fun closeTab(event: ActionEvent) {

    }

    @FXML fun showPreferences(events: ActionEvent) {
        val dialog = Dialog<ButtonType>()
        dialog.title = "Preferences"
        dialog.initOwner(mainBorderPane.scene.window)
        dialog.dialogPane.content = FXMLLoader.load(javaClass.getResource("PreferencesWindow.fxml"))
        dialog.dialogPane.buttonTypes.add(ButtonType.OK)
        dialog.dialogPane.buttonTypes.add(ButtonType.CANCEL)
        dialog.show()
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
