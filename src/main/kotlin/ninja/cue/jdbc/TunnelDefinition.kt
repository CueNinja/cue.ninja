package ninja.cue.jdbc

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue

@JsonInclude
class TunnelDefinition(
        @JsonProperty var host: String,
        @JsonProperty var username: String,
        @JsonProperty var password: String? = null,
        @JsonProperty var port: Int = 22,
        @JsonProperty var keyFile: String? = null) {

    fun toJson(): JsonNode {
        return jacksonObjectMapper().valueToTree(this)
    }

    override fun toString(): String {
        return "$username@$host:$port"
    }

    companion object {
        @JvmStatic
        fun fromJson(obj: JsonNode): TunnelDefinition {
            return jacksonObjectMapper().treeToValue(obj)
        }
    }
}
