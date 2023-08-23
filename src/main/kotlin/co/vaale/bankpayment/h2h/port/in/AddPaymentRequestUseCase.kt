package co.vaale.bankpayment.h2h.port.`in`

interface AddPaymentRequestUseCase {
    fun invoke(listCommercePlaceId: String?, initDate: String?, endDate: String?)
}