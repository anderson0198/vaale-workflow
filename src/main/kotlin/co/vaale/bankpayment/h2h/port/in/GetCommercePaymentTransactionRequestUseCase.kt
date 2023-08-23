package co.vaale.bankpayment.h2h.port.`in`

import co.vaale.bankpayment.h2h.model.H2hTransaction

interface GetCommercePaymentTransactionRequestUseCase {
    fun invoke(listCommercePlaceId: String?, listPlanId: String?): H2hTransaction
}