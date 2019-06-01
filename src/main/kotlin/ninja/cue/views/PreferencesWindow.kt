package ninja.cue.views

import javafx.beans.value.ObservableValue
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.stage.FileChooser
import javafx.util.Callback
import java.io.File

import org.controlsfx.control.ToggleSwitch

import ninja.cue.jdbc.ConnectionDefinition
import ninja.cue.jdbc.TunnelDefinition
import ninja.cue.persistance.DataManager
import java.util.*
import javax.xml.crypto.Data

@Suppress("UNUSED_PARAMETER")
class PreferencesWindow {
    private val connections = DataManager.instance.connections
    @FXML private var rootBorderPane = BorderPane()
    @FXML private var sshTunnelPane = TitledPane()
    @FXML private var sshTunnelToggle = ToggleSwitch()
    @FXML private var connectionName = TextField()
    @FXML private var connectionUri = TextField()
    @FXML private var tunnelHost = TextField()
    @FXML private var tunnelPort = TextField()
    @FXML private var tunnelUsername = TextField()
    @FXML private var tunnelPassword = TextField()
    @FXML private var tunnelAuthType = ChoiceBox<String>()
    @FXML private var tunnelKeyPath = TextField()

    @FXML private var connectionList = ListView<ConnectionDefinition>()

    @FXML private var tunnelKeyPathLabel = Label()
    @FXML private var tunnelKeyPathButton = Button()

    @FXML private var theme = ChoiceBox<String>()

    fun initialize() {
        connectionList.items = connections

        connectionList.cellFactory = Callback { ConnectionDefinitionListCell() }

        connectionList.selectionModel.selectionMode = SelectionMode.SINGLE
        connectionList.selectionModel.selectedItemProperty().addListener(this::connectionChange)
        connectionList.selectionModel.select(0)

        val themeStr = DataManager.instance.theme.value
        theme.value = "${themeStr[0].toUpperCase()}${themeStr.substring(1)}"
    }

    private class ConnectionDefinitionListCell : ListCell<ConnectionDefinition>() {
        override fun updateItem(item: ConnectionDefinition?, empty: Boolean) {
            super.updateItem(item, empty)
            text = null
            if(!empty) {
                text = item?.name
            }
        }
    }

    private fun connectionChange(observe: ObservableValue<out ConnectionDefinition>?, old: ConnectionDefinition?, new: ConnectionDefinition?) {
        if(new != null) {
            tunnelKeyPath.text = null
            connectionName.text = new.name
            connectionUri.text = new.name
            val tunnelDefinition: TunnelDefinition? = new.tunnelDefinition
            if (tunnelDefinition != null) {
                sshTunnelToggle.isSelected = true
                tunnelHost.text = tunnelDefinition.host
                tunnelPort.text = tunnelDefinition.port.toString()
                tunnelUsername.text = tunnelDefinition.username
                tunnelPassword.text = tunnelDefinition.password
                if(tunnelDefinition.keyFile != null && tunnelDefinition.keyFile != "") {
                    tunnelAuthType.value = "Private Key"
                    tunnelKeyPath.text = tunnelDefinition.keyFile
                } else {
                    tunnelAuthType.value = "Password"
                }
            } else {
                sshTunnelToggle.isSelected = false
            }
            tunnelAuthTypeChanged(ActionEvent())
            toggleSshTunnel()
        }
    }

    @FXML fun toggleSshTunnel() {
        sshTunnelPane.expandedProperty().set(sshTunnelToggle.isSelected)
        sshTunnelPane.disableProperty().set(!sshTunnelToggle.isSelected)
    }

    @FXML fun tunnelAuthTypeChanged(event: ActionEvent) {
        tunnelKeyPathLabel.disableProperty().set(tunnelAuthType.value == "Password")
        tunnelKeyPathButton.disableProperty().set(tunnelAuthType.value == "Password")
        tunnelKeyPath.disableProperty().set(tunnelAuthType.value == "Password")
    }

    @FXML fun tunnelFileOpen(event: ActionEvent) {
        val chooser = FileChooser()
        chooser.title = "Open Key File"
        val file: File? = chooser.showOpenDialog(rootBorderPane.scene.window)
        if(file != null) {
            tunnelKeyPath.text = file.toURI().toString().substring(5)
        }
    }

    @FXML fun saveItems(event: ActionEvent) {
        if(connectionList.selectionModel.selectedItem == null) {
            val connection = ConnectionDefinition(
                    connectionName.text,
                    connectionUri.text
            )
            if (sshTunnelToggle.isSelected) {
                connection.tunnelDefinition = TunnelDefinition(
                        tunnelHost.text,
                        tunnelUsername.text,
                        tunnelPassword.text,
                        tunnelPort.text.toInt(),
                        tunnelKeyPath.text
                )
            }
            connections.add(connection)
            connectionList.selectionModel.select(connection)
        } else {
            val connection = connectionList.selectionModel.selectedItem
            connection.name = connectionName.text
            connection.uri = connectionUri.text
            if(sshTunnelToggle.isSelected) {
                if(connection.tunnelDefinition == null) {
                    connection.tunnelDefinition = TunnelDefinition(
                            tunnelHost.text,
                            tunnelUsername.text,
                            tunnelPassword.text,
                            tunnelPort.text.toInt(),
                            tunnelKeyPath.text
                    )
                } else {
                    connection.tunnelDefinition?.host = tunnelHost.text
                    connection.tunnelDefinition?.username = tunnelUsername.text
                    connection.tunnelDefinition?.password = tunnelPassword.text
                    connection.tunnelDefinition?.port = tunnelPort.text.toInt()
                    connection.tunnelDefinition?.keyFile = tunnelKeyPath.text
                }
            } else {
                connection.tunnelDefinition = null
                tunnelHost.text = null
                tunnelUsername.text = null
                tunnelPassword.text = null
                tunnelPort.text = null
                tunnelKeyPath.text = null
            }
            connections.set(connectionList.selectionModel.selectedIndex, connection)
        }
    }

    @FXML fun testItem(event: ActionEvent) {

    }

    @FXML fun addItem(event: ActionEvent) {
        tunnelKeyPath.text = null
        connectionName.text = null
        connectionUri.text = null
        sshTunnelToggle.isSelected = false
        tunnelAuthTypeChanged(ActionEvent())
        toggleSshTunnel()
        connectionList.selectionModel.clearSelection()
    }

    @FXML fun removeItem(event: ActionEvent) {
        connections.remove(connectionList.selectionModel.selectedItem)
        if(connections.size == 0) {
            addItem(ActionEvent())
        }
    }

    @FXML fun themeChange(event: ActionEvent) {
        DataManager.instance.theme.set(theme.value.toLowerCase())
    }

}