package co.vaale.bankpayment.h2h.service

import co.com.groupware.lenpli.model.Setting
import co.vaale.bankpayment.h2h.infrastructure.mysql.SettingRepository
import co.vaale.bankpayment.h2h.model.H2hPayer
import co.vaale.bankpayment.h2h.model.H2hRecipient
import co.vaale.bankpayment.h2h.model.H2hTransaction
import co.vaale.bankpayment.h2h.model.ICommercePlacePaymentRequest
import co.vaale.bankpayment.h2h.port.`in`.GetCommercePaymentTransactionRequestUseCase
import co.vaale.bankpayment.h2h.port.out.ICommercePlacePaymentRequestRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class CommercePaymentTransactionRequestService : GetCommercePaymentTransactionRequestUseCase {

    private val log: Logger = LoggerFactory.getLogger(CommercePaymentTransactionRequestService::class.java)

    @Autowired
    private lateinit var commercePlacePaymentRequestPort: ICommercePlacePaymentRequestRepository

    @Autowired
    private lateinit var settingPort: SettingRepository


    override fun invoke(listCommercePlaceId: String?, listPlanId: String?): H2hTransaction {

        val nameSetting: Setting = settingPort.getById("VAALE_NAME", null)
        val identificationNumberSetting: Setting = settingPort.getById("VAALE_IDENTIFICATION_NUMBER", null)
        val identificationTypeSetting: Setting = settingPort.getById("VAALE_IDENTIFICATION_TYPE", null)
        val bankAccountNumberSetting: Setting = settingPort.getById("VAALE_H2H_BANK_ACCOUNT_NUMBER", null)
        val bankAccountTypeSetting: Setting = settingPort.getById("VAALE_H2H_BANK_ACCOUNT_TYPE", null)
        val paymentTypeSetting: Setting = settingPort.getById("VAALE_H2H_PAYMENT_TYPE", null)

        val list = commercePlacePaymentRequestPort.get(
            ICommercePlacePaymentRequest.Filter(
                isDone = false,
                listPlanId = listPlanId,
                listCommercePlaceId = listCommercePlaceId
            ), null
        )
        val recipientItems = list.map {
            val name = scapeSpecialCharacters(it.commercePlace?.name)
            val documentNumber = it.commercePlacePaymentMethod?.accountHolderDocumentKey?.split("-")?.get(0)
            val accountNumber = it.commercePlacePaymentMethod?.accountNumber?.split("-")?.get(0)
            log.info("-- $name")
            log.info("-- $documentNumber")
            log.info("-- $accountNumber")
            H2hRecipient(name = name
            , documentNumber = documentNumber
            , documentType = it.commercePlacePaymentMethod?.accountHolderDocumentType?.h2hCode?.toInt()
            , accountNumber = accountNumber
            , accountType = it.commercePlacePaymentMethod?.bankAccountType?.h2hCode
            , bank = it.commercePlacePaymentMethod?.bank?.h2hCode?.toInt()
            , amount = (it.amount - it.paymentInterest)
            , transactionType = getTransactionType(it.commercePlacePaymentMethod?.bankAccountType?.id)
            , concept = "Transferencia"
            , applicationdate = SimpleDateFormat("yyyy/MM/dd").format(Date()))
        }

        return H2hTransaction(
            payer = H2hPayer(
                name = nameSetting.value,
                documentNumber = identificationNumberSetting.value,
                documentType = identificationTypeSetting.value.toInt(),
                accountNumber = bankAccountNumberSetting.value,
                accountType = bankAccountTypeSetting.value
            )
            , recipientItems = recipientItems
            , paymentType = paymentTypeSetting.value.toInt()
            , creationDate = SimpleDateFormat("yyyy/MM/dd").format(Date())
            , applicationdate = SimpleDateFormat("yyyy/MM/dd").format(Date())
            , sequence = "1"
            , description = "Pago a comercios"
            , application = "I"
        )
    }

    private fun getTransactionType(accountType: Long?) : Int {
        when(accountType) {
            1L -> return 37
            2L -> return 27
            3L -> return 52
            else -> return 37
        }
    }

    private fun scapeSpecialCharacters(input: String?): String? {
        val pattern = Regex("[^a-zA-Z0-9\\s]")
        return input?.replace(pattern, "")
    }

}