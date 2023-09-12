package co.vaale.workflow.h2h.service

import co.vaale.workflow.Application
import co.vaale.workflow.h2h.port.`in`.CollectUseCase
import co.vaale.workflow.h2h.port.out.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service
import java.sql.Time
import java.time.LocalTime

@Service
class CollectServices : CollectUseCase {

    @Autowired
    private lateinit var segmentPort: ISegmentRepository
    @Autowired
    private lateinit var bigQyeryPort: IBigQyeryRepository
    @Autowired
    private lateinit var activityPort: IActivityRepository
    @Autowired
    private lateinit var runActivitiesPort: IRunActivities
    @Autowired
    private lateinit var sendMailPort:ISendMail


//el data es la fecha de sistema
override fun invoke() : String? {
    val dataList = listOf(
        mapOf(
            "client_id" to 1L,
            "client_name" to "Juan Pérez",
            "client_document_number" to "123456789A",
            "client_cell_phone" to "3205431049",
            "entry_date_time" to "2023-09-01 10:30:00",
            "entry_date" to "2023-09-01",
            "entry_time" to "10:30:00",
            "referrer_name" to "Maria González",
            "in_past_due" to "No",
            "amount_overdue_with_fee" to 0L,
            "amount_overdue_without_fee" to 0L,
            "days_past_due" to "0",
            "days_since_purchase_to_start_payment" to "15",
            "date_last_purchase" to "2023-08-15",
            "commerce_name_last_purchase" to "Tienda ABC",
            "date_to_pay" to "2023-09-15",
            "commerce_neighborhood" to "Barrio X",
            "commerce_city" to "Ciudad A",
            "amount_to_pay" to "$${addThousandsSeparators("104000")}",
            "amount_purchase" to 950L,
            "is_client_payment_agreement" to "Sí"
        ),
        mapOf(
            "client_id" to 2L,
            "client_name" to "Brahian hincapie",
            "client_document_number" to "987654321B",
            "client_cell_phone" to "3205431049",
            "entry_date_time" to "2023-09-02 14:45:00",
            "entry_date" to "2023-09-02",
            "entry_time" to "14:45:00",
            "referrer_name" to "Pedro Sánchez",
            "in_past_due" to "Sí",
            "amount_overdue_with_fee" to 50L,
            "amount_overdue_without_fee" to 0L,
            "days_past_due" to "7",
            "days_since_purchase_to_start_payment" to "30",
            "date_last_purchase" to "2023-08-25",
            "commerce_name_last_purchase" to "Tienda XYZ",
            "date_to_pay" to "2023-09-10",
            "commerce_neighborhood" to "Barrio Y",
            "commerce_city" to "Ciudad B",
            "amount_to_pay" to "$${addThousandsSeparators("67000")}",
            "amount_purchase" to 750L,
            "is_client_payment_agreement" to "No"
        ),
        mapOf(
            "client_id" to 3L,
            "client_name" to "Alexandra",
            "client_document_number" to "555555555C",
            "client_cell_phone" to "3205431049",
            "entry_date_time" to "2023-09-03 09:15:00",
            "entry_date" to "2023-09-03",
            "entry_time" to "09:15:00",
            "referrer_name" to "Carlos Ramírez",
            "in_past_due" to "No",
            "amount_overdue_with_fee" to 0L,
            "amount_overdue_without_fee" to 0L,
            "days_past_due" to "0",
            "days_since_purchase_to_start_payment" to "10",
            "date_last_purchase" to "2023-08-20",
            "commerce_name_last_purchase" to "Tienda MNO",
            "date_to_pay" to "2023-09-10",
            "commerce_neighborhood" to "Barrio Z",
            "commerce_city" to "Ciudad C",
            "amount_to_pay" to "$${addThousandsSeparators("103000")}",
            "amount_purchase" to 550L,
            "isClient_payment_agreement" to "Sí"
        )
    )

        //listar procesos
        var time = LocalTime.now()
        var segmentList = segmentPort.getProcessByTime(Time.valueOf(time))
        var returnProcess: Long? = null



        if (segmentList != null) {
            //Agrupar procesos por id
            val agroupedBySegmentId = segmentList.groupBy { it["segmentId"] }

            //println("gb $agroupedBySegmentId")
            //iterar procesos
            for ((id, segmentList) in agroupedBySegmentId) {
                for (segment in segmentList) {

                }
            //lista de actividades
            val idStr = id.toString()
            val segmentIdInt = idStr.toIntOrNull() ?: 0
            val activityList = activityPort.getActivityByProcessId(segmentIdInt)
            //println(activityList)
            //llamar bigquery adentro se manejan los filtros
            val clients = bigQyeryPort.getClientWithActiveLoan(segmentList)
            //println("clientessss $clients")
                //iterar clientes
                //cambiar por clients
                //println(clients.size)
                val to = sendMailPort.getTo(activityList)
                    for (client in dataList){
                        //itero actividad
                        for (activity in activityList) {
                            //ejecuto actividad
                            runActivitiesPort.invoke(segmentIdInt, client, activity)
                        }
                    }
                //enviar correo
                if (to != null) {
                    sendMailPort.invoke(dataList, to)
                }

            }

        }
        //println(returnProcess)
        return returnProcess.toString()
    }

    fun addThousandsSeparators(number: String): String {
        val parts = number.split(".")
        val wholePart = parts[0]
        val decimalPart = if (parts.size > 1) "." + parts[1] else ""

        val regex = "(\\d)(?=(\\d{3})+\$)".toRegex()
        val formatedWholePart = wholePart.replace(regex, "$1,")

        return formatedWholePart + decimalPart
    }

}

