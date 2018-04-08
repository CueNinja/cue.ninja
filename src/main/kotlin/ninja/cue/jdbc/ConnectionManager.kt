package ninja.cue.jdbc

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class ConnectionManager() {
    private object Holder {
        val localInstance = ConnectionManager()
    }

    companion object {
        val instance: ConnectionManager by lazy { Holder.localInstance }
    }

    private fun connection(): Connection {
        val url = "jdbc:postgresql://localhost/rock?user=usr1"
        return DriverManager.getConnection(url)
    }

    fun connect(block: (Connection) -> ResultSet): ResultSet {
        val connection = connection()
        val result = block(connection)
        connection.close()
        return result
    }

}
