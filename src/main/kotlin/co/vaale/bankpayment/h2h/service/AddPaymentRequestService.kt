package co.vaale.bankpayment.h2h.service

import co.com.groupware.lenpli.model.CommercePlace
import co.com.groupware.lenpli.model.CommercePlacePaymentRequest
import co.vaale.bankpayment.h2h.model.ICommercePlacePaymentMethod
import co.vaale.bankpayment.h2h.port.`in`.AddPaymentRequestUseCase
import co.vaale.bankpayment.h2h.port.out.ICommercePlacePaymentMethodRepository
import co.vaale.bankpayment.h2h.port.out.ICommercePlacePaymentRequestRepository
import co.vaale.bankpayment.h2h.port.out.ICommerceSummaryStatementRepository
import co.vaale.bankpayment.h2h.port.out.IPaymentMembershipRepository
import io.swagger.client.models.PaymentMembership
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AddPaymentRequestService : AddPaymentRequestUseCase {

    private val log: Logger = LoggerFactory.getLogger(AddPaymentRequestService::class.java)

    @Autowired
    private lateinit var paymentMembershipPort: IPaymentMembershipRepository

    @Autowired
    private lateinit var commerceSummaryStatementPort: ICommerceSummaryStatementRepository

    @Autowired
    private lateinit var commercePlacePaymentMethodPort: ICommercePlacePaymentMethodRepository

    @Autowired
    private lateinit var commercePlacePaymentRequestPort: ICommercePlacePaymentRequestRepository



    override fun invoke(listCommercePlaceId: String?, initDate: String?, endDate: String?) {
        val listPaymentMembership = paymentMembershipPort.getPaymentMembership(listCommercePlaceId)
        listPaymentMembership?.forEach {
            addPaymentRequest(it, initDate, endDate)
        }

    }

    private fun addPaymentRequest(paymentMembership: PaymentMembership?, initDate: String?, endDate: String?) {
        if (paymentMembership?.totalSalesAmount!! <= 0.0) {
            return
        }
        val list = commerceSummaryStatementPort.getStatementSubscription(initDate!!, endDate!!, paymentMembership?.commercePlaceId!!)
        val totalBalance = list?.fold(list?.get(0)?.get("initialBalanceAmount") as Double) { acc, it ->
            acc + (it?.get("salesAmount") as Double) + (it?.get("paymentsToVaaleAmount") as Double) - (it?.get("collectionAmount") as Double) - (it?.get("paymentsToCommerceAmount") as Double) - (it?.get("subscriptionAmount") as Double) + (it?.get("cashbackAmount") as Double)
        }
        log.info("--totalBalance $totalBalance")
        if (totalBalance!! <= 0.0) {
            return
        }

        val listCommercePlacePaymentMethod = commercePlacePaymentMethodPort.get(ICommercePlacePaymentMethod.Filter(paymentMembership?.commercePlaceId!!, true), null)

        if (listCommercePlacePaymentMethod == null || listCommercePlacePaymentMethod.isEmpty()) {
            return
        }

        val commercePlacePaymentRequest = CommercePlacePaymentRequest()
            .commercePlace(CommercePlace().id(paymentMembership?.commercePlaceId!!))
            .commercePlacePaymentMethod(listCommercePlacePaymentMethod?.get(0))
            .amount(totalBalance)
            .paymentInterest(0.0)
            .isAdvanceRequest(false)
            .isDone(false)
            .description("Pago para comercio con cuenta Premium")
            .isActive(true)
        log.info("-- $commercePlacePaymentRequest")
        commercePlacePaymentRequestPort.save(commercePlacePaymentRequest, null)
    }
}