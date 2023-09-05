package co.vaale.workflow.h2h.service

import co.vaale.workflow.h2h.port.out.IActivityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CreateTextSmsService {
    @Autowired
    private lateinit var activityPort: IActivityRepository


    fun invoke(processId: Int, map: Map<String, Any?>) : String? {

        var activityList = activityPort.getActivityByProcessId(processId)
        var returnText: String? = null

        if (activityList != null) {

            for (activity in activityList) {
                var text = activity["smsTemplateText"] as String?
                println("act: $activity")
                val attributes = activity["smsTemplateAttributes"] as String?


                if (!attributes.isNullOrBlank()) {
                    val attributeList = attributes.split(",").map { it.trim() }
                    val attributeMap = mutableMapOf<String, String>()
                    for (attribute in attributeList) {
                        // Asignar los valores al mapa usando el atributo como clave
                        if (map.containsKey(attribute)) {
                            val value = map[attribute]
                            attributeMap[attribute] = value.toString()

                            // igualar a la lista de cliente que estoy iterando
                            text = text?.replace("@@$attribute",attributeMap[attribute].toString())
                            //println(text)
                        }
                    }
                    //println("Mapa de atributos: $attributeMap")
                }

                if (text != null && text != "") {
                    returnText = text
                    break
                }


            }

        }
        return returnText
    }


}