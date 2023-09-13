package co.vaale.workflow.h2h.component

import co.vaale.workflow.h2h.port.out.ICreateSmsText
import org.springframework.stereotype.Component

@Component
class CreateSmsTextComponent : ICreateSmsText {


    override fun invoke(processId: Int, client: Map<String, Any?>, activity: Map<String, Any?>) : String? {

        var returnText: String? = null

        if (activity != null) {

                var text = activity["sms_template_text"] as String?
                //println("act: $activity")
                val attributes = activity["sms_template_attributes"] as String?


                if (!attributes.isNullOrBlank()) {
                    val attributeList = attributes.split(",").map { it.trim() }
                    val attributeMap = mutableMapOf<String, String>()
                    for (attribute in attributeList) {
                        // Asignar los valores al mapa usando el atributo como clave
                        if (client.containsKey(attribute)) {
                            val value = client[attribute]
                            attributeMap[attribute] = value.toString()

                            // igualar a la lista de cliente que estoy iterando
                            text = text?.replace("@@$attribute",attributeMap[attribute].toString())
                            //println(text)
                        }
                    }

                    println("Envio SMS con estos atributos: $attributeMap")
                }
            returnText = text




        }
        return returnText
    }


}