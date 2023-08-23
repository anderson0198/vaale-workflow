package co.vaale.bankpayment.h2h.port.`in`

import co.vaale.bankpayment.h2h.model.H2hTransaction

interface GetSapH2hPaymentTextUseCase {
    fun invoke(transaction: H2hTransaction?):String
}