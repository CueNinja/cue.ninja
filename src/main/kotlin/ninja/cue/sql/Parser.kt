package ninja.cue.sql

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.IntStream
import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.misc.Interval

import ninja.cue.sql.antlr.ANSISqlLexer

private class CaseInsensitiveStream(
        val stream: CharStream
) : CharStream {
    override fun getSourceName(): String { return stream.sourceName }
    override fun index(): Int { return stream.index() }
    override fun size(): Int { return stream.size() }
    override fun release(marker: Int) { stream.release(marker) }
    override fun consume() { stream.consume() }
    override fun seek(index: Int) { stream.seek(index) }
    override fun getText(interval: Interval?): String { return stream.getText(interval) }
    override fun mark(): Int { return stream.mark() }
    override fun LA(i: Int): Int {
        val result = stream.LA(i)
        return when (result) {
            0, IntStream.EOF -> result
            else -> Character.toUpperCase(result)
        }
    }
}

fun getTokens(sql: String): List<Token> {
    val lexer = ANSISqlLexer(CaseInsensitiveStream(CharStreams.fromString(sql)))
    val tokens = mutableListOf<Token>()
    while (!lexer._hitEOF) { tokens.add(lexer.nextToken()) }
    return tokens
}

fun getTokenName(index: Int): String {
    return ANSISqlLexer.tokenNames[index]
}

fun getTokenNames(sql: String): List<String> {
    return getTokens(sql).map { getTokenName(it.type) }
}
