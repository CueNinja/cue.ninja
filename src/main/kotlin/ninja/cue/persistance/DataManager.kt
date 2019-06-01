package ninja.cue.persistance

import io.reactivex.Observable

import java.nio.file.Files
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import ninja.cue.jdbc.ConnectionDefinition
import java.io.File
import java.nio.file.Paths

import javax.json.Json
import com.github.thomasnield.rxkotlinfx.changes
import com.github.thomasnield.rxkotlinfx.toObservable
import javax.json.JsonObject

import ninja.cue.util.*

class DataManager private constructor() {
    private val configDirPath = configPath()
    private val jsonFilePath = mainConfigFile()
    val connections = FXCollections.observableArrayList<ConnectionDefinition>()
    private var connectionsObserver = connections.changes().subscribe {
        save()
    }
    val theme = SimpleStringProperty("light", "theme")
    private var themeObserver = theme.toObservable().subscribe {
        save()
    }


    private object Holder {
        val localInstance = DataManager()
    }

    companion object {
        val instance: DataManager by lazy { Holder.localInstance }
    }

    private fun defaultConfig(): JsonObject {
        val defaultJsonPath = javaClass.getResource("default.json")
        return File(defaultJsonPath.toURI()).bufferedReader().use {
            Json.createReader(it).readObject()
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

    fun loadConfig() {
        if(Files.notExists(Paths.get(configDirPath))) {
            File(configDirPath).mkdir()
        }
        val configFile = File(jsonFilePath)
        if(!configFile.exists()) {
            configFile.createNewFile()
            configFile.bufferedWriter().use {
                Json.createWriter(it).writeObject(defaultConfig())
            }
        }
        disableObservers()
        val obj = File(jsonFilePath).bufferedReader().use {
            Json.createReader(it).use {
                it.readObject()
            }
        }
        // TODO the format of the obj file should be verified
        theme.value = obj.getString("theme")
        for(connection in obj.getJsonArray("connections")) {
            connections.add(ConnectionDefinition.fromJson(connection))
        }
        turnOnObservers()
    }

    fun save() {
        val obj = Json.createObjectBuilder()
        obj.add("theme", theme.value)
        val connectionsArray = Json.createArrayBuilder()
        for(connection in connections) {
            connectionsArray.add(connection.toJson())
        }
        obj.add("connections", connectionsArray)
        File(jsonFilePath).bufferedWriter().use {
            Json.createWriter(it).writeObject(obj.build())
        }
    }
}
