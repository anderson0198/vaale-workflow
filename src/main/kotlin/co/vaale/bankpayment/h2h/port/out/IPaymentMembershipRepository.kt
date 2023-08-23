package co.vaale.bankpayment.h2h.port.out

import io.swagger.client.models.PaymentMembership

interface IPaymentMembershipRepository {

    fun getPaymentMembership(listCommercePlaceId: String?): List<PaymentMembership?>?

}