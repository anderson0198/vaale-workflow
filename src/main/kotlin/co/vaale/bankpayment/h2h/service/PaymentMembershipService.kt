package co.vaale.bankpayment.h2h.service

import co.com.groupware.lenpli.model.CommercePlace
import co.com.groupware.lenpli.model.CommercePlaceSubscriptionPlan
import co.com.groupware.lenpli.model.CommercePlaceSubscriptionPlanPayment
import co.vaale.bankpayment.h2h.port.`in`.AddPaymentMembershipUseCase
import co.vaale.bankpayment.h2h.port.`in`.GetPaymentMembershipUseCase
import co.vaale.bankpayment.h2h.port.out.ICommercePlaceSubscriptionPlanPaymentRepository
import co.vaale.bankpayment.h2h.port.out.IPaymentMembershipRepository
import io.swagger.client.models.PaymentMembership
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.math.roundToInt

@Service
class PaymentMembershipService : GetPaymentMembershipUseCase, AddPaymentMembershipUseCase {

    private val log: Logger = LoggerFactory.getLogger(PaymentMembershipService::class.java)

    @Autowired
    private lateinit var paymentMembershipPort: IPaymentMembershipRepository

    @Autowired
    private lateinit var commercePlaceSubscriptionPlanPaymentPort: ICommercePlaceSubscriptionPlanPaymentRepository

    override fun invoke(listCommercePlaceId: String?): List<PaymentMembership?>? {
        return paymentMembershipPort.getPaymentMembership(listCommercePlaceId)
    }

    override fun invoke(listCommercePlaceId: String?, entryDate: String?): String? {
        log.info("-- AddPaymentMembershipUseCase")
        val list = invoke(listCommercePlaceId)
        list?.forEach {
            addPaymentMembershipToCommerce(it, entryDate)
        }

        return "OK"
    }


    private fun addPaymentMembershipToCommerce(paymentMembership: PaymentMembership?, entryDate: String?) {
        if (paymentMembership?.totalSalesAmount!! < paymentMembership?.minPremiumMembershipCapValue!!) {
            return
        }

        if (paymentMembership?.totalSalesAmount!! <= paymentMembership?.minPremiumMembershipCapValue!! && paymentMembership?.countSubscriptionPlanPayment!! > 0) {
            return
        }

        val quotient = (paymentMembership?.totalSalesAmount!! / paymentMembership?.premiumMembershipCapValue!!).toInt()
        val residue = paymentMembership?.totalSalesAmount!! % paymentMembership?.premiumMembershipCapValue!!
        val count = quotient - paymentMembership?.countSubscriptionPlanPayment!! + (if (residue >= paymentMembership?.minPremiumMembershipCapValue!!) 1 else 0)

        for (i in 0 until count) {
            val commercePlaceSubscriptionPlanPayment = CommercePlaceSubscriptionPlanPayment()
                .commercePlace(CommercePlace().id(paymentMembership?.commercePlaceId))
                .amount(paymentMembership?.premiumMembershipValue)
                .description("Suscripción premium")
                .entryDate(entryDate)
            //log.info("-- $commercePlaceSubscriptionPlanPayment")
            log.info("-- commercePlaceId ${paymentMembership?.commercePlaceId}")

            commercePlaceSubscriptionPlanPaymentPort.save(commercePlaceSubscriptionPlanPayment
                , null)
        }
    }
}