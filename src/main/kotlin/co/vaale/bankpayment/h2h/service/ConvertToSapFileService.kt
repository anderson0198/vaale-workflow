package co.vaale.bankpayment.h2h.service

import co.vaale.bankpayment.h2h.model.H2hTransaction
import co.vaale.bankpayment.h2h.port.`in`.GetSapH2hPaymentTextUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ConvertToSapFileService : GetSapH2hPaymentTextUseCase {

    private val log: Logger = LoggerFactory.getLogger(ConvertToSapFileService::class.java)

    override fun invoke(transaction: H2hTransaction?): String {
        log.info("--invoke")
        var text = StringBuilder()

        try {
            val payer = transaction?.payer
            text.append(payer?.documentType)
            text.append(String.format("%010d", payer?.documentNumber?.toLong()))
            val payerName = try {if (payer?.name!!.length > 16) payer?.name?.substring(0, 16) else String.format("%-16s", payer?.name)} catch(e: Exception) {String.format("%-16s", "")}
            text.append(payerName)
            text.append(String.format("%03d", transaction?.paymentType))
            val description = try {if (transaction?.description!!.length > 10) transaction?.description?.substring(0, 10) else String.format("%-10s", transaction?.description)} catch(e: Exception) {String.format("%-10s", "")}
            text.append(description)
            text.append(transaction?.creationDate?.replace("/", ""))
            text.append(transaction?.sequence)
            text.append(transaction?.applicationdate?.replace("/", ""))

            text.append(String.format("%010d", 10000))
            text.append(String.format("%010d", 0))

            val amount = try {transaction?.recipientItems?.sumOf { it?.amount!! }} catch(e: Exception) {0.0}
            text.append(String.format("%010d", amount?.toInt()))
            text.append(String.format("%011d", payer?.accountNumber?.toLong()))
            text.append(payer?.accountType)

            transaction?.recipientItems?.forEach {
                text.append("\n")
                text.append(it?.documentType)
                text.append(String.format("%015d", it?.documentNumber?.toLong()))
                val name = try {if (it?.name!!.length > 18) payer?.name?.substring(0, 18) else String.format("%-18s", it?.name)} catch(e: Exception) {String.format("%-18s", "")}
                text.append(name)
                text.append(String.format("%09d", it?.bank))
                text.append(String.format("%017d", it?.accountNumber?.toLong()))
                text.append(it?.accountType)
                text.append(it?.transactionType)
                text.append(String.format("%010d", it?.amount?.toInt()))
                val concept = try {if (it?.concept!!.length > 9) it?.concept?.substring(0, 9) else String.format("%-9s", it?.concept)} catch(e: Exception) {String.format("%-9s", "")}
                text.append(concept)

                text.append(String.format("%-12s", ""))
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return text.toString()
    }
}