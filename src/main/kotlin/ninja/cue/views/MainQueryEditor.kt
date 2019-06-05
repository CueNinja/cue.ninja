package ninja.cue.views

import com.github.thomasnield.rxkotlinfx.toObservable
import javafx.beans.property.SimpleStringProperty
import javafx.fxml.FXML
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.paint.Color
import javafx.util.Callback

import javafx.geometry.Insets

import org.fxmisc.richtext.CodeArea
import org.controlsfx.control.MasterDetailPane

import ninja.cue.jdbc.Query
import ninja.cue.persistance.DataManager

class MainQueryEditor {
    @FXML private var editor = CodeArea()
    @FXML private var table = TableView<Array<String>>()
    @FXML private var masterDetail = MasterDetailPane()

    fun initialize() {
        //TODO Set theme on the CodeArea
        updateTheme(DataManager.instance.theme.value)
        DataManager.instance.theme.toObservable().subscribe(this::updateTheme)

        // Nodes.addInputMap(area, consume(keyPressed(S, CONTROL_DOWN), event -&gt; save()));
        editor.padding = Insets(5.0)
    }

    private fun updateTheme(value: String?) {
        if(value == "light" ) {
            editor.background = Background(BackgroundFill(Color.WHITE, null, null))
        } else {
            editor.background = Background(BackgroundFill(Color.web("454545"), null, null))
        }
    }

    fun formatSql() {
//        val statements = editor.text.split(";").map {
//            val parser = SqlParser(SqlParserOptions)
//            val stmt = parser.createStatement(it, ParsingOptions())
//            SqlFormatter.formatSql(stmt, null)
//        }
//        editor.setText();
    }

    fun executeQuery() {
        val queryText = editor.text
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