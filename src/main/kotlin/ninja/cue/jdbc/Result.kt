package ninja.cue.jdbc

import java.sql.ResultSet

class Result(results: ResultSet) {
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
