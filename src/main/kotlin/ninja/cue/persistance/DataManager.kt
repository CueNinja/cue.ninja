package ninja.cue.persistance

import java.nio.file.Files
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import ninja.cue.jdbc.ConnectionDefinition
import java.io.File
import java.nio.file.Paths

import javax.json.Json
import com.github.thomasnield.rxkotlinfx.changes
import com.github.thomasnield.rxkotlinfx.toObservable
import de.jensd.shichimifx.utils.OS
import javax.json.JsonObject

class DataManager private constructor() {
    private val pathSeperator = if(OS.isWindows()) "\\" else "/"
    private val configDirPath = "${OS.getSystemLocalAppDataPath()}$pathSeperator.cue.ninja"
    private val jsonFilePath = "$configDirPath${pathSeperator}config.json"
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
        val obj = Json.createObjectBuilder()
        obj.add("theme", "light")
        val connections = Json.createArrayBuilder()
        obj.add("connections", connections)
        return obj.build()
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
        if(!Files.exists(Paths.get(configDirPath))) {
            File(configDirPath).mkdir()
        }
        val config = File(jsonFilePath)
        if(!config.exists()) {
            config.createNewFile()
            config.bufferedWriter().use {
                Json.createWriter(it).use {
                    it.writeObject(defaultConfig())
                }
            }
        }
        disableObservers()
        val obj = File(jsonFilePath).bufferedReader().use {
            Json.createReader(it).use {
                it.readObject()
            }
        }
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
            Json.createWriter(it).use {
                it.writeObject(obj.build())
            }
        }
    }
}
