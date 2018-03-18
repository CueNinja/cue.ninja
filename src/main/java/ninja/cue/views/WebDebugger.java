package ninja.cue.views;

import com.vladsch.javafx.webview.debugger.DevToolsDebuggerServer;
import javafx.scene.web.WebEngine;
import com.sun.javafx.scene.web.Debugger;

import java.lang.reflect.Field;

public class WebDebugger {
    static void startDebugger(WebEngine engine) {
        Class webEngineClazz = WebEngine.class;

        Field debuggerField = null;
        try {
            debuggerField = webEngineClazz.getDeclaredField("debugger");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if(debuggerField == null) {
            return;
        }
        debuggerField.setAccessible(true);

        Debugger debugger = null;
        try {
            debugger = (Debugger) debuggerField.get(engine);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if(debugger = null) {
            return;
        }
        DevToolsDebuggerServer server = DevToolsDebuggerServer.startDebugServer(debugger, 51742, 1);
    }
}
