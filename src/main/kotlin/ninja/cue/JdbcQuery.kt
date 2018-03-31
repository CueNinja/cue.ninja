package ninja.cue

import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TableColumn
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class JdbcQuery(query: String) {
    private val query = query
    private val connectionManager = ConnectionManager.instance

    fun execute(): JdbcResult {
        val results = connectionManager.connect {
            val stmt = it.createStatement()
            stmt.executeQuery(query)
        }

        return JdbcResult(results)
    }
}