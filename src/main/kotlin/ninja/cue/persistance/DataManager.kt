package ninja.cue.persistance

import java.nio.file.Files
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import java.io.File
import java.nio.file.Paths

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import com.github.thomasnield.rxkotlinfx.changes
import com.github.thomasnield.rxkotlinfx.toObservable
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import javafx.collections.ObservableList
import com.networknt.schema.JsonSchemaFactory
import com.networknt.schema.JsonSchema

import ninja.cue.jdbc.ConnectionDefinition
import ninja.cue.util.*

class DataManager private constructor() {
    private val mapper = jacksonObjectMapper()
    private val configDirPath = configPath()
    private val jsonFilePath = mainConfigFile()
    val connections: ObservableList<ConnectionDefinition> =
            FXCollections.observableArrayList<ConnectionDefinition>()
    private var connectionsObserver = connections.changes().subscribe {
        save()
    }
    val theme = SimpleStringProperty("light", "theme")
    private var themeObserver = theme.toObservable().subscribe {
        save()
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private class Configuration(
            @JsonProperty val theme: String,
            @JsonProperty connections: List<ConnectionDefinition>
    )

    private object Holder {
        val localInstance = DataManager()
    }

    companion object {
        val instance: DataManager by lazy { Holder.localInstance }
    }

    private fun defaultConfig(): JsonNode {
        val defaultJsonPath = javaClass.getResource("default.json")
        return File(defaultJsonPath.toURI()).bufferedReader().use {
            mapper.readTree(it)
        }
    }

    private fun disableObservers() {
        themeObserver.dispose()
        connectionsObserver.dispose()
    }

    private fun turnOnObservers() {
        connectionsObserver = connections.changes().subscribe {
            save()
        }
        themeObserver = theme.toObservable().subscribe {
            save()
        }
    }

    private fun loadSchema(): JsonSchema {
        val factory = JsonSchemaFactory.getInstance()
        val it = File(javaClass.getResource("schema.json").toURI()).inputStream()
        return factory.getSchema(it)
    }

    private fun valid(node: JsonNode): Boolean {
        val schema = loadSchema()
        val errors = schema.validate(node)
        return errors.size == 0
    }

    fun loadConfig() {
        if(Files.notExists(Paths.get(configDirPath))) {
            File(configDirPath).mkdir()
        }
        val configFile = File(jsonFilePath)
        if(!configFile.exists()) {
            configFile.createNewFile()
            configFile.bufferedWriter().use {
                mapper.writeValue(it, defaultConfig())
            }
        }
        disableObservers()
        var obj = File(jsonFilePath).bufferedReader().use {
            mapper.readTree(it)
        }
        val valid = valid(obj)
        if(!valid) {
           obj = defaultConfig()
        }
        theme.value = obj.get("theme").asText()
        for(connection in obj.get("connections")) {
            connections.add(ConnectionDefinition.fromJson(connection))
        }
        if(!valid) {
            save()
        }
        turnOnObservers()
    }

    private fun save() {
        val json = mapper.writeValueAsString(Configuration(theme.value, connections))
        File(jsonFilePath).bufferedWriter().use {
            it.write(json)
        }
    }
}
