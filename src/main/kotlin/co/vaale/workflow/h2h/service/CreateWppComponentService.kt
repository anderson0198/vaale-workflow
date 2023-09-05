package co.vaale.workflow.h2h.service

import co.com.groupware.lenpli.model.ClientWithActiveLoan
import co.vaale.workflow.Application
import co.vaale.workflow.h2h.port.out.IActivityRepository
import co.vaale.workflow.h2h.port.out.IClientWithActiveLoanRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service
import java.util.jar.Attributes

@Service
class CreateWppComponentService {

    @Autowired
    private lateinit var activityPort: IActivityRepository
    /*@Autowired
    private lateinit var clientWithActiveLoanPort: IClientWithActiveLoanRepository
*/


     fun invoke(processId: Int, map: Map<String, Any?>) : String? {

        var activityList = activityPort.getActivityByProcessId(processId)
        var returnComponent: String? = null

        if (activityList != null) {

                for (activity in activityList) {
                    var component = activity["whatsappTemplateComponent"] as String?
                    val attributes = activity["whatsappTemplateAttributes"] as String?

                    if (!attributes.isNullOrBlank()) {
                        val attributeList = attributes.split(",").map { it.trim() }
                        val attributeMap = mutableMapOf<String, String>()
                        for (attribute in attributeList) {
                            // Asignar los valores al mapa usando el atributo como clave
                            if (map.containsKey(attribute)) {
                                val value = map[attribute]
                                attributeMap[attribute] = value.toString()

                                // igualar a la lista de cliente que estoy iterando
                                component = component?.replace("@@$attribute",attributeMap[attribute].toString())
                            }
                        }
                        println("Mapa de atributos: $attributeMap")
                    }


                    if (component != null && component != "") {
                        returnComponent = component
                        break
                    }

                }

        }
        return returnComponent
    }

    fun getTemplateName(processId: Int) : String? {
        var activityList = activityPort.getActivityByProcessId(processId)
        var result: String? = null

        if (activityList != null) {
            for (activity in activityList) {
                val templateName = activity["whatsappTemplateName"] as String?

                if (templateName != null && templateName != "") {
                    result = templateName
                    break
                }

            }
        }
        return result
    }

    fun getChannelId(processId: Int) : String? {
        var activityList = activityPort.getActivityByProcessId(processId)
        var result: String? = null

        if (activityList != null) {
            for (activity in activityList) {
                val channelId = activity["whatsappTemplateChannelId"] as String?

                if (channelId != null && channelId != "") {
                    result = channelId
                    break
                }

            }
        }
        return result
    }

}


