package co.vaale.bankpayment.h2h.port.`in`

import co.vaale.bankpayment.h2h.model.H2hTransaction

interface GetPabH2hPaymentTextUseCase {
    fun invoke(transaction: H2hTransaction?):String
}