package co.vaale.workflow.h2h.service

import co.vaale.workflow.Application
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service

@Service
class CollectService {

    @Autowired
    private lateinit var createWppComponentService: CreateWppComponentService
    @Autowired
    private lateinit var sendWhatsappService: SendWhatsappService
    @Autowired
    private lateinit var sendSmsService: SendSmsService
    @Autowired
    private lateinit var createTextSmsService: CreateTextSmsService


    fun invoke() : String? {

        val dataList = listOf(
            mapOf(
                "clientId" to 1L,
                "clientName" to "Juan Pérez",
                "clientDocumentNumber" to "123456789A",
                "clientCellPhone" to "3205431049",
                "entryDateTime" to "2023-09-01 10:30:00",
                "entryDate" to "2023-09-01",
                "entryTime" to "10:30:00",
                "referrerName" to "Maria González",
                "inPastDue" to "No",
                "amountOverdueWithFee" to 0L,
                "amountOverdueWithoutFee" to 0L,
                "daysPastDue" to "0",
                "daysSincePurchaseToStartPayment" to "15",
                "dateLastPurchase" to "2023-08-15",
                "commerceNameLastPurchase" to "Tienda ABC",
                "dateToPay" to "2023-09-15",
                "commerceNeighborhood" to "Barrio X",
                "commerceCity" to "Ciudad A",
                "amountToPay" to 1000L,
                "amountPurchase" to 950L,
                "isClientPaymentAgreement" to "Sí"
            ),
            mapOf(
                "clientId" to 2L,
                "clientName" to "Ana Rodríguez",
                "clientDocumentNumber" to "987654321B",
                "clientCellPhone" to "3205431049",
                "entryDateTime" to "2023-09-02 14:45:00",
                "entryDate" to "2023-09-02",
                "entryTime" to "14:45:00",
                "referrerName" to "Pedro Sánchez",
                "inPastDue" to "Sí",
                "amountOverdueWithFee" to 50L,
                "amountOverdueWithoutFee" to 0L,
                "daysPastDue" to "7",
                "daysSincePurchaseToStartPayment" to "30",
                "dateLastPurchase" to "2023-08-25",
                "commerceNameLastPurchase" to "Tienda XYZ",
                "dateToPay" to "2023-09-10",
                "commerceNeighborhood" to "Barrio Y",
                "commerceCity" to "Ciudad B",
                "amountToPay" to 800L,
                "amountPurchase" to 750L,
                "isClientPaymentAgreement" to "No"
            ),
            mapOf(
                "clientId" to 3L,
                "clientName" to "María López",
                "clientDocumentNumber" to "555555555C",
                "clientCellPhone" to "3205431049",
                "entryDateTime" to "2023-09-03 09:15:00",
                "entryDate" to "2023-09-03",
                "entryTime" to "09:15:00",
                "referrerName" to "Carlos Ramírez",
                "inPastDue" to "No",
                "amountOverdueWithFee" to 0L,
                "amountOverdueWithoutFee" to 0L,
                "daysPastDue" to "0",
                "daysSincePurchaseToStartPayment" to "10",
                "dateLastPurchase" to "2023-08-20",
                "commerceNameLastPurchase" to "Tienda MNO",
                "dateToPay" to "2023-09-10",
                "commerceNeighborhood" to "Barrio Z",
                "commerceCity" to "Ciudad C",
                "amountToPay" to 600L,
                "amountPurchase" to 550L,
                "isClientPaymentAgreement" to "Sí"
            )
        )

        for (data in dataList) {
            val processId = 2
            val wppTemplate = createWppComponentService.invoke(processId, data)
            val templateName = createWppComponentService.getTemplateName(processId)
            val channelId = createWppComponentService.getChannelId(processId)
            val to = data["clientCellPhone"]
            println("Resultado: $wppTemplate")
            try {
               // sendWhatsappService.send("57$to",templateName!!,wppTemplate!!,channelId!!)
            }catch (e: Exception){

            }

            //sms
            val smsText = createTextSmsService.invoke(processId,data)
            println("text: $smsText")
            try {
                sendSmsService.sendSms(to.toString(),smsText!!)
            }catch (e: Exception){
                println(e)
            }

        }

        return null

    }



}

fun main(args: Array<String>) {
    val context = runApplication<Application>(*args)

    val collectService = context.getBean(CollectService::class.java)
    collectService.invoke()

    // Cerrar el contexto de Spring
    context.close()
}