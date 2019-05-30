package ninja.cue.views

import com.github.thomasnield.rxkotlinfx.toObservable
import javafx.beans.property.SimpleStringProperty
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.util.Callback
import ninja.cue.jdbc.Query
import ninja.cue.monaco.Monaco
import ninja.cue.persistance.DataManager
import org.controlsfx.control.MasterDetailPane

class MainQueryEditor {
    @FXML private var monaco = Monaco()
    @FXML private var table = TableView<Array<String>>()
    @FXML private var masterDetail = MasterDetailPane()

    fun initialize() {
        updateTheme(DataManager.instance.theme.value)
        DataManager.instance.theme.toObservable().subscribe(this::updateTheme)
    }

    fun updateTheme(value: String?) {
        val theme = when(value) {
            "dark" -> "vs-dark"
            else -> "vs"
        }
        monaco.setTheme(theme)
    }

    fun executeQuery() {
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
}