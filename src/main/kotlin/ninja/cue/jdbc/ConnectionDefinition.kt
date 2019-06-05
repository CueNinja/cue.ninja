package ninja.cue.jdbc

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue

@JsonInclude(JsonInclude.Include.NON_EMPTY)
class ConnectionDefinition(
        @JsonProperty var name: String,
        @JsonProperty var uri: String,
        @JsonProperty("tunnel") var tunnelDefinition: TunnelDefinition? = null) {

    fun toJson(): JsonNode {
        return jacksonObjectMapper().valueToTree(this)
    }

    override fun toString(): String {
        return name
    }

    companion object {
        @JvmStatic
        fun fromJson(obj: JsonNode): ConnectionDefinition {
            return jacksonObjectMapper().treeToValue(obj)
        }
    }
}
