package co.vaale.workflow.h2h.infrastructure.mysql.bigquery

import co.vaale.workflow.h2h.port.out.IBigQyeryRepository
import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.bigquery.BigQueryOptions
import com.google.cloud.bigquery.JobId
import com.google.cloud.bigquery.JobInfo
import com.google.cloud.bigquery.QueryJobConfiguration
import org.springframework.stereotype.Repository
import java.text.NumberFormat
import java.util.*

@Repository
class BigQyeryRepository : IBigQyeryRepository {



    override fun getClientWithActiveLoan(map: List<Map<String, Any>>): List<Map<String, Any>> {

        val resultList = mutableListOf<Map<String, Any>>()
        val projectId = "vaale-347417"
        val credentialsPath = Thread.currentThread().contextClassLoader.getResourceAsStream("credentials_bigquery.json")
        var credentials = ServiceAccountCredentials.fromStream(credentialsPath)


        // Instantiate a client.
        val bigquery = BigQueryOptions.newBuilder()
            .setCredentials(credentials)
            .setProjectId(projectId)
            .build()
            .service


        val conditionList = mutableListOf<String>()

        for (segment in map){
            val filterOperation = segment["filterOperation"]
            val filterAttributeName = segment["filterAttributeName"]
            val filterValue = segment["filterValue"]
            if (filterOperation != null && filterOperation != "") {
                val condition = "$filterAttributeName $filterOperation '$filterValue'"
                conditionList.add(condition)
            }
        }

        var querySQL = """
    SELECT 
        *
    FROM `vaale-347417.vaaleds.client_with_active_loan`
"""

        if (conditionList.isNotEmpty()) {
            val conditionWithFilterSQL = conditionList.joinToString(" AND ", " WHERE ", "")
            querySQL += conditionWithFilterSQL
        }

        println("El query de este proceso es: $querySQL")
        try {

            val queryConfig = QueryJobConfiguration.newBuilder(
                querySQL
            ) .setUseLegacySql(false)
                .build()

            // Create a job ID so that we can safely retry.
            val jobId = JobId.of(UUID.randomUUID().toString())
            var queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build())


            val result = queryJob.getQueryResults()


            for (row in result.iterateAll()) {
                val clientName = row["client_name"].stringValue
                val clientPhone = row["client_cell_phone"].stringValue
                val clientId = row["client_id"].stringValue
                val clientDocumentNumber = row["client_document_number"].stringValue
                val entryDateTime = row["entry_date_time"].stringValue
                val entryDate = row["entry_date"].stringValue
                val entryTime = row["entry_time"].stringValue
                val referrerName = row["referrer_name"].stringValue
                val inPastDue = row["in_past_due"].stringValue
                val amountOverdueWithFee = row["amount_overdue_with_fee"].stringValue
                val amountOverdueWithoutFee = row["amount_overdue_without_fee"].stringValue
                val daysPastDue = row["days_past_due"].stringValue
                val daysSincePurchaseToStartPayment = row["days_since_purchase_to_start_payment"].stringValue
                val dateLastPurchase = row["date_last_purchase"].stringValue
                val commerceNameLastPurchase = row["commerce_name_last_purchase"].stringValue
                val dateToPay = row["date_to_pay"].stringValue
                val commerceCity = row["commerce_city"].stringValue
                val amountToPay = row["amount_to_pay"].stringValue
                val amountPurchase = row["amount_purchase"].stringValue
                val isClientPaymentAgreement = row["is_client_payment_agreement"].stringValue

                val rowMap = mapOf(

                    "client_name" to clientName.split(" ")[0].substring(0, 1).toUpperCase() + clientName.split(" ")[0].substring(1).toLowerCase(),
                    "client_cell_phone" to clientPhone,
                    "client_id" to clientId,
                    "client_document_number" to clientDocumentNumber,
                    "entry_date_time" to entryDateTime,
                    "entry_date" to entryDate,
                    "entry_time" to entryTime,
                    "referrer_name" to referrerName,
                    "in_past_due" to inPastDue,
                    "amount_overdue_with_fee" to amountOverdueWithFee,
                    "amount_overdue_without_fee" to amountOverdueWithoutFee,
                    "days_past_due" to daysPastDue,
                    "days_since_purchase_to_start_payment" to daysSincePurchaseToStartPayment,
                    "date_last_purchase" to dateLastPurchase,
                    "commerce_name_last_purchase" to commerceNameLastPurchase,
                    "date_to_pay" to dateToPay,
                    "commerce_city" to commerceCity,
                    "amount_to_pay" to "/$${addThousandsSeparators(amountToPay)}",
                    "amount_purchase" to amountPurchase,
                    "is_client_payment_agreement" to isClientPaymentAgreement
                )

                resultList.add(rowMap)
            }
            // Wait for the query to complete.
            queryJob = queryJob.waitFor()

        }catch (e: Exception){
            println(e)
        }
        return  resultList

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
