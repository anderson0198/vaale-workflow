package co.vaale.workflow.h2h.component

import co.vaale.workflow.h2h.port.out.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RunActivitiesComponent : IRunActivities {
    @Autowired
    private lateinit var sendWppPort:ISendWpp
    @Autowired
    private lateinit var sendSmsPort:ISendSms
    @Autowired
    private lateinit var sendMailPort:ISendMail
    @Autowired
    private lateinit var createWppComponentPort:ICreateWppComponent
    @Autowired
    private lateinit var createSmsTextPort:ICreateSmsText


    override fun invoke(processId: Int, client: Map<String, Any?>, activity: Map<String, Any?>) : String? {
        try {
            val wppComponent = createWppComponentPort.invoke(processId,client,activity)
            val channel = createWppComponentPort.getChannelId(processId, activity)
            val templateName = createWppComponentPort.getTemplateName(processId, activity)
            if (wppComponent !=null && wppComponent != "" && channel !=null && channel != "" && templateName !=null && templateName != "") {
                sendWppPort.send(client["client_cell_phone"].toString(), templateName, wppComponent, channel)
            }
        }catch (e: Exception){
            println(e)
        }

        try {
            val smsText = createSmsTextPort.invoke(processId,client,activity)
            if (smsText !=null && smsText != "") {
                sendSmsPort.sendSMS(client["client_cell_phone"].toString(), smsText)
            }
        }catch (e: Exception){
            println(e)
        }

        return "Ok"
    }

}