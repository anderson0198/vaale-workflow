package co.vaale.workflow.h2h.component

import co.vaale.workflow.Application
import co.vaale.workflow.h2h.port.out.ICreateCSV
import co.vaale.workflow.h2h.service.CollectServices
import co.vaale.workflow.h2h.service.SendMailService
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.nio.charset.StandardCharsets

@Component
class CreateCSV : ICreateCSV{
    override fun createCsvFile(clientList: List<Map<String, Any?>>):ByteArray{
        var csvByteArray = byteArrayOf()
        try {

            val printer = StringWriter()
            val headers = listOf(
                "client_name", "client_cell_phone", "client_id", "client_document_number", "entry_date_time", "entry_date", "entry_time", "referrer_name",
                        "in_past_due", "amount_overdue_with_fee", "amount_overdue_without_fee", "days_past_due",
                        "days_since_purchase_to_start_payment", "date_last_purchase", "commerce_name_last_purchase",
                        "date_to_pay", "commerce_city", "amount_to_pay", "amount_purchase", "is_client_payment_agreement"
                 )
            val csvPrinter = CSVPrinter(printer, CSVFormat.EXCEL)
            csvPrinter.printRecord(headers)


            for (client in clientList) {
                val clientName = client["client_name"]?.toString() ?: ""
                val clientPhone = client["client_cell_phone"]?.toString() ?: ""
                val clientId = client["client_id"]?.toString() ?: ""
                val clientDocumentNumber = client["client_document_number"]?.toString() ?: ""
                val entryDateTime = client["entry_date_time"]?.toString() ?: ""
                val entryDate = client["entry_date"]?.toString() ?: ""
                val entryTime = client["entry_time"]?.toString() ?: ""
                val referrerName = client["referrer_name"]?.toString() ?: ""
                val inPastDue = client["in_past_due"]?.toString() ?: ""
                val amountOverdueWithFee = client["amount_overdue_with_fee"]?.toString() ?: ""
                val amountOverdueWithoutFee = client["amount_overdue_without_fee"]?.toString() ?: ""
                val daysPastDue = client["days_past_due"]?.toString() ?: ""
                val daysSincePurchaseToStartPayment = client["days_since_purchase_to_start_payment"]?.toString() ?: ""
                val dateLastPurchase = client["date_last_purchase"]?.toString() ?: ""
                val commerceNameLastPurchase = client["commerce_name_last_purchase"]?.toString() ?: ""
                val dateToPay = client["date_to_pay"]?.toString() ?: ""
                val commerceCity = client["commerce_city"]?.toString() ?: ""
                val amountToPay = client["amount_to_pay"]?.toString() ?: ""
                val amountPurchase = client["amount_purchase"]?.toString() ?: ""
                val isClientPaymentAgreement = client["is_client_payment_agreement"]?.toString() ?: ""
                val csvLine = "$clientName;$clientPhone;$clientId;$clientDocumentNumber;$entryDateTime;$entryDate;" +
                        "$entryTime;$referrerName;$inPastDue;$amountOverdueWithFee;$amountOverdueWithoutFee;" +
                        "$daysPastDue;$daysSincePurchaseToStartPayment;$dateLastPurchase;" +
                        "$commerceNameLastPurchase;$dateToPay;$commerceCity;$amountToPay;$amountPurchase;$isClientPaymentAgreement "
                val rowData = listOf(
                    clientName,clientPhone,clientId,clientDocumentNumber,entryDateTime,entryDate,
                            entryTime,referrerName,inPastDue,amountOverdueWithFee,amountOverdueWithoutFee,
                            daysPastDue,daysSincePurchaseToStartPayment,dateLastPurchase,
                            commerceNameLastPurchase,dateToPay,commerceCity,amountToPay,amountPurchase,isClientPaymentAgreement
                )
                csvPrinter.printRecord(rowData)
                //printer.println(csvLine)
                //csvPrinter.printRecord(csvLine)
                val csvContent = printer.toString()
                println(csvContent)
                csvByteArray = csvContent.toByteArray(StandardCharsets.UTF_8)
            }
            printer.flush()
        }catch (e:Exception){
            println(e)
        }

        return csvByteArray
    }
}

