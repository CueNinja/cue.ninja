package ninja.cue.jdbc

import javax.json.Json
import javax.json.JsonObject
import javax.json.JsonValue

class ConnectionDefinition(name: String, uri: String, tunnelDefinition: TunnelDefinition? = null) {
    var name = name
    var uri = uri
    var tunnelDefinition = tunnelDefinition

    fun toJson(): JsonObject {
        val obj = Json.createObjectBuilder()
        obj.add("name", name)
        obj.add("uri", uri)
        obj.add("tunnel", if(tunnelDefinition == null) JsonValue.NULL else tunnelDefinition?.toJson() )
        return obj.build()
    }

    override fun toString(): String {
        return name
    }

    companion object {
        @JvmStatic
        fun fromJson(obj: JsonObject): ConnectionDefinition {
            return ConnectionDefinition(
                    obj.getString("name"),
                    obj.getString("uri"),
                    if(!obj.isNull("tunnel")) TunnelDefinition.fromJson(obj.getJsonObject("tunnel")) else null
            )
        }

        fun fromJson(obj: JsonValue): ConnectionDefinition {
            return fromJson(obj.asJsonObject())
        }
    }
}
