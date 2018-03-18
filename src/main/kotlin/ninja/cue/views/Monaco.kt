package ninja.cue.views

import com.vladsch.javafx.webview.debugger.DevToolsDebuggerJsBridge
import javafx.scene.layout.BorderPane
import javafx.scene.web.WebView
import netscape.javascript.JSObject

class Monaco : BorderPane() {
    private val webView = WebView()
    private val jsBridge = DevToolsDebuggerJsBridge(webView, 1, null)
    private var window: JSObject? = null
    private var _content = ""
    private var _language = ""
    init {
        center = webView
        webView.engine.load(javaClass.getResource("monaco/index.html").toExternalForm())
        window = webView.engine.executeScript("window") as JSObject

        window?.setMember("javafx", this)
    }

    fun startDebugger() {
        //WebDebugger.startDebugger(webView.engine)
        jsBridge.startDebugServer(51742, onStartFail, onStartSuccess)
    }

    fun setContent(value: String) {
        System.out.println("EXTERNAL UPDATE: ")
        System.out.println(value)
        window?.call("updateContent", value)
        _content = value
    }

    fun getContent(): String {
        return _content
    }

    fun setLanguage(value: String) {
        _language = value
    }

    fun getLanguage(): String {
        return _language
    }

    fun contentChanged(value: String) {
        System.out.println("JS UPDATE: ")
        System.out.println(value)
        _content = value
    }

    fun log(value: String) {
        System.out.println("[JS][LOG] $value")
    }
}