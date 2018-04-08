package ninja.cue.jdbc

import javax.json.Json
import javax.json.JsonObject
import javax.json.JsonValue

class TunnelDefinition(host: String, username: String, password: String? = null, port: Int = 22, keyFile: String? = null) {
    var host = host
    var port = port
    var username = username
    var password = password
    var keyFile = keyFile

    fun toJson(): JsonObject {
        val obj = Json.createObjectBuilder()
        obj.add("host", host)
        obj.add("port", port)
        obj.add("username", username)
        obj.add("password", password)
        if(keyFile == null) {
            obj.add("keyFile", JsonValue.NULL)
        } else {
            obj.add("keyFile", keyFile)
        }
        return obj.build()
    }

    companion object {
        @JvmStatic
        fun fromJson(obj: JsonObject): TunnelDefinition {
            return TunnelDefinition(
                    obj.getString("host"),
                    obj.getString("username"),
                    obj.getString("password"),
                    obj.getInt("port"),
                    if(!obj.isNull("keyFile")) obj.getString("keyFile") else null
            )
        }
    }
}
