package ninja.cue.jdbc

class Query(query: String) {
    private val query = query
    private val connectionManager = ConnectionManager.instance

    fun execute(): Result {
        val results = connectionManager.connect {
            val stmt = it.createStatement()
            stmt.executeQuery(query)
        }

        return Result(results)
    }
}