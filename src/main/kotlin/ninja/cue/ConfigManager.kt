package ninja.cue

import java.io.File
import javax.json.Json

class ConfigManager {
    private object Holder {
        val localInstance = ConfigManager()
    }

    companion object {
        val instance: ConfigManager by lazy { Holder.localInstance }
    }

//    val array = File("").bufferedReader().use {
//        Json.createReader(it).use {
//            it.readArray()
//        }
//    }
}
