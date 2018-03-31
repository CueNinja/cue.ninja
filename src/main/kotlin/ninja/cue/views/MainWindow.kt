package ninja.cue.views

import javafx.beans.property.SimpleStringProperty
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.MenuBar
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.util.Callback
import ninja.cue.JdbcQuery
import java.sql.Connection
import java.sql.DriverManager

import ninja.cue.monaco.Monaco
import java.sql.ResultSet

class MainWindow {
    @FXML private var monaco = Monaco()
    @FXML private var mainMenu = MenuBar()
    @FXML private var table = TableView<Array<String>>()

    private var connection: Connection? = null

    fun initialize() {
        mainMenu.useSystemMenuBarProperty().set(true)
    }

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

}
