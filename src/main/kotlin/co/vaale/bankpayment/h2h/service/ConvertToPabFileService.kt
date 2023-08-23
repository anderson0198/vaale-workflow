package co.vaale.bankpayment.h2h.service

import co.vaale.bankpayment.h2h.model.H2hTransaction
import co.vaale.bankpayment.h2h.port.`in`.GetPabH2hPaymentTextUseCase
import co.vaale.bankpayment.h2h.port.`in`.GetSapH2hPaymentTextUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.math.pow

@Service
class ConvertToPabFileService : GetPabH2hPaymentTextUseCase {

    private val log: Logger = LoggerFactory.getLogger(ConvertToPabFileService::class.java)

    override fun invoke(transaction: H2hTransaction?): String {
        log.info("--invoke")
        var text = StringBuilder()

        try {
            val payer = transaction?.payer
            text.append(payer?.documentType)
            text.append(String.format("%015d", payer?.documentNumber?.toLong()))
            text.append(transaction?.application)
            text.append("".padEnd(15)) // Espacio de 15
            //val payerName = try {if (payer?.name!!.length > 15) payer?.name?.substring(0, 15) else String.format("%-15s", payer?.name)} catch(e: Exception) {String.format("%-15s", "")}
            //text.append(payerName)
            text.append(String.format("%03d", transaction?.paymentType))

            val description = try {if (transaction?.description!!.length > 10) transaction?.description?.substring(0, 10) else String.format("%-10s", transaction?.description)} catch(e: Exception) {String.format("%-10s", "")}
            text.append(description)
            text.append(transaction?.creationDate?.replace("/", ""))
            text.append(if (transaction?.sequence?.length == 1) "0${transaction?.sequence}" else transaction?.sequence?.take(2))
            text.append(transaction?.applicationdate?.replace("/", ""))

            text.append(String.format("%010d", 20000))
            text.append(String.format("%010d", 0))

            val amount = try {transaction?.recipientItems?.sumOf { it?.amount!! }} catch(e: Exception) {0.0}
            text.append(String.format("%018d", amount?.toInt()))
            if (amount != null) {
                text.append(String.format("%02d", (amount * (10.0.pow(2))).toInt() % 100))
            }
            text.append(String.format("%011d", payer?.accountNumber?.toLong()))
            text.append(payer?.accountType)
            text.append("".padEnd(149))
            text.append("\n")

            transaction?.recipientItems?.forEach {
                text.append(6)
                text.append(it?.documentNumber?.padEnd(15))
                val name = try {if (it?.name!!.length > 30) it?.name?.substring(0, 30) else String.format("%-30s", it?.name)} catch(e: Exception) {String.format("%-30s", "")}
                text.append(name)
                text.append(String.format("%09d", it?.bank))
                text.append(it?.accountNumber?.padEnd(17))
                text.append(it?.accountType)
                text.append(it?.transactionType)
                text.append(String.format("%015d", it?.amount?.toInt()))
                if (it?.amount != null) {
                    text.append(String.format("%02d", (it?.amount * (10.0.pow(2))).toInt() % 100))
                }
                text.append(transaction?.applicationdate?.replace("/", ""))
                val concept = try {if (it?.concept!!.length > 21) it?.concept?.substring(0, 21) else String.format("%-21s", it?.concept)} catch(e: Exception) {String.format("%-21s", "")}
                text.append(concept)
                text.append(it?.documentType)
                text.append(String.format("%05d", 0))
                text.append("".padEnd(137))
                text.append("\n")
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return text.toString()
    }
}