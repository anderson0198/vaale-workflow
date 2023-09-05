package co.vaale.workflow.h2h.infrastructure.mysql.bigquery
/*
import co.vaale.workflow.h2h.model.IClientWithActiveLoan
import co.vaale.workflow.h2h.port.out.IClientWithActiveLoanRepository
import co.vaale.workflow.h2h.service.CreateWppComponentService
import com.google.cloud.bigquery.BigQuery
import com.google.cloud.bigquery.BigQueryOptions
import com.google.cloud.bigquery.QueryJobConfiguration
import org.springframework.stereotype.Repository

@Repository
class ClientWithActiveLoanRepository: IClientWithActiveLoanRepository{

    override fun getClientWithActiveLoan(filter: IClientWithActiveLoan.Filter): List<Map<String, Any>> {

        // definir instancia con credenciales
        val bigQuery: BigQuery? = BigQueryOptions.getDefaultInstance().service

        // Define  consulta SQL.
        val sqlQuery = """
        SELECT 
        *
         FROM `vaale-347417.vaaleds.client_with_active_loan` 
         where 
         days_past_due = '2'
         and is_client_payment_agreement = 'NO'
         and date_to_pay = '2023-09-01';
    """.trimIndent()

        // Configura la consulta.
        val queryConfig = QueryJobConfiguration.newBuilder(sqlQuery).build()

        // Ejecuta la consulta.
        val queryJob = bigQuery.create(queryConfig)
        val queryResults = queryJob.queryResults

        // Procesa los resultados de la consulta.
        while (queryResults.hasNext()) {
            val row = queryResults.next()
            // Procesa los datos de la fila aquí.
            val column1Value = row.get("column1").getStringValue()
            val column2Value = row.get("column2").getStringValue()
        }

        // Cierra recursos.
        queryResults.close()
    }

}*/