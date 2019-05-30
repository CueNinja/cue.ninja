package ninja.cue.views

import javafx.scene.paint.Color

import com.github.thomasnield.rxkotlinfx.toObservable
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView

import ninja.cue.persistance.DataManager

class FAIcon : FontAwesomeIconView() {
    private val dataManager = DataManager.instance
    init {
        dataManager.theme.toObservable().subscribe(this::updateColor)
        updateColor(dataManager.theme.value)
    }

    private fun updateColor(theme: String?) {
        fill = when(theme) {
            "dark" -> Color.WHITE
            "light" -> Color.BLACK
            else -> Color.BLACK
        }
    }
}