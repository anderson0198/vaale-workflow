package co.vaale.bankpayment.h2h.port.`in`

import io.swagger.client.models.PaymentMembership

interface GetPaymentMembershipUseCase {
    fun invoke(listCommercePlaceId: String?): List<PaymentMembership?>?
}