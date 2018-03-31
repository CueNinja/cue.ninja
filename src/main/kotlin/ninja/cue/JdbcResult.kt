package ninja.cue

import java.sql.ResultSet

class JdbcResult(results: ResultSet) {
    val columns = Array<String>(results.metaData.columnCount) {
        results.metaData.getColumnName(it + 1)
    }
    var rows = ArrayList<Array<String>>()
    init {
        while(results.next()) {
            rows.add(Array(columns.size) {
                results.getString(it + 1)
            })
        }

    }
}
