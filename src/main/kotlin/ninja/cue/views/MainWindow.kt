package ninja.cue.views

import javafx.beans.property.SimpleStringProperty
import javafx.application.Platform;
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.util.Callback

import ninja.cue.monaco.Monaco
import org.controlsfx.control.MasterDetailPane

import ninja.cue.jdbc.Query


class MainWindow {
    @FXML private var mainBorderPane = BorderPane()
    @FXML private var mainMenu = MenuBar()
    @FXML private var vbox = VBox()
    @FXML private var preferencesSeparator = SeparatorMenuItem()
    @FXML private var preferencesMenuItem = MenuItem()
    @FXML private var tabs = TabPane()
    @FXML private var controllers = ArrayList<MainQueryEditor>()
    @FXML private var nextTabMenu = MenuItem()
    @FXML private var previousTabMenu = MenuItem()

    fun initialize() {
        newTab(ActionEvent())
    }

    @FXML fun executeQuery(event: ActionEvent) {
        val tabIndex = tabs.selectionModel.selectedIndex
        controllers[tabIndex].executeQuery()
    }

    @FXML fun newTab(event: ActionEvent) {
        tabs.tabs.add(buildTab())
        tabs.selectionModel.select(tabs.tabs.size - 1)
        updateMenus()
    }

    @FXML fun closeTab(event: ActionEvent) {
        val index = tabs.selectionModel.selectedIndex
        tabs.tabs.removeAt(index)
        controllers.removeAt(index)
        updateMenus()
    }

    @FXML fun showPreferences(events: ActionEvent) {
        val dialog = Dialog<ButtonType>()
        dialog.title = "Preferences"
        dialog.initOwner(mainBorderPane.scene.window)
        dialog.dialogPane.content = FXMLLoader.load(javaClass.getResource("PreferencesWindow.fxml"))
        dialog.dialogPane.buttonTypes.add(ButtonType.OK)
        dialog.dialogPane.buttonTypes.add(ButtonType.CANCEL)
        dialog.show()
    }

    @FXML fun quitApplication(events: ActionEvent) {
        Platform.exit()
    }

    @FXML fun previousTab(event: ActionEvent) {
    }

    @FXML fun nextTab(event: ActionEvent) {
    }

    private fun buildTab(): Tab {
        val tab = Tab()
        tab.text = "Query Localhost"
        val loader = FXMLLoader(javaClass.getResource("MainQueryEditor.fxml"))
        tab.content = loader.load()
        controllers.add(loader.getController())
        return tab
    }

    fun updateMenus() {
        nextTabMenu.disableProperty().set(tabs.tabs.size < 2)
        previousTabMenu.disableProperty().set(tabs.tabs.size < 2)
    }

    fun getMainMenu(): MenuBar {
        vbox.children.remove(mainMenu)
        return mainMenu
    }

    fun preferencesMenuItem(): MenuItem {
        mainMenu.menus[0].items.removeAll(preferencesSeparator, preferencesMenuItem)
        return preferencesMenuItem
    }
}
