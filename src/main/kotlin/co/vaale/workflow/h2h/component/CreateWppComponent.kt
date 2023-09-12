package co.vaale.workflow.h2h.component

import co.vaale.workflow.h2h.port.out.ICreateWppComponent
import org.springframework.stereotype.Component

@Component
class CreateWppComponent : ICreateWppComponent {

     override fun invoke(processId: Int, client: Map<String, Any?>, activity: Map<String, Any?>) : String? {

        var returnComponent: String? = null

        if (activity != null) {

                    var component = activity["whatsapp_template_component"] as String?
                    val attributes = activity["whatsapp_template_attributes"] as String?

                    if (!attributes.isNullOrBlank()) {
                        val attributeList = attributes.split(",").map { it.trim() }
                        val attributeMap = mutableMapOf<String, String>()
                        for (attribute in attributeList) {
                            // Asignar los valores al mapa usando el atributo como clave
                            if (client.containsKey(attribute)) {
                                val value = client[attribute]
                                attributeMap[attribute] = value.toString()

                                // igualar a la lista de cliente que estoy iterando
                                component = component?.replace("@@$attribute",attributeMap[attribute].toString())
                            }
                        }

                        println("Enviar wpp: $attributeMap")
                    }
            returnComponent = component
        }
        return returnComponent
    }

    override fun getTemplateName(processId: Int, activity: Map<String, Any?>) : String? {
        var result: String? = null

        if (activity != null) {
                val templateName = activity["whatsapp_template_name"] as String?
            result = templateName
        }
        return result
    }

    override fun getChannelId(processId: Int, activity: Map<String, Any?>) : String? {
        var result: String? = null

        if (activity != null) {
                val channelId = activity["whatsapp_template_channel_id"] as String?
            result = channelId
        }
        return result
    }

}


