package ninja.cue.views

import javafx.beans.property.SimpleStringProperty
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.MenuBar
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.util.Callback
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

    @FXML fun reset(event: ActionEvent) {
        monaco.setContent("-- Type code here!")
    }

    @FXML fun debugger(actionEvent: ActionEvent) {
        //monaco.startDebugger()
    }

    fun makeConnection(): Connection {
        if(connection == null) {
            val url = "jdbc:postgresql://localhost/rock?user=usr1"
            connection = DriverManager.getConnection(url)
        }
        return connection!!
    }

    fun executeQuery(event: ActionEvent) {
        val query = monaco.getContent()
        val stmt = makeConnection().createStatement()
        val results: ResultSet = stmt.executeQuery(query)

        val columns = Array<String>(results.metaData.columnCount) {
            results.metaData.getColumnName(it + 1)
        }

        table.columns.setAll(columns.map {
            val cell = TableColumn<Array<String>, String>(it)
            val index = columns.indexOf(it)
            cell.cellValueFactory = Callback { SimpleStringProperty(it.value[index]) }
            cell
        })

        table.items.clear()

        while(results.next()) {
            val row = Array<String>(columns.size) {
                results.getString(it + 1)
            }
            table.items.add(row)
        }
    }

}
