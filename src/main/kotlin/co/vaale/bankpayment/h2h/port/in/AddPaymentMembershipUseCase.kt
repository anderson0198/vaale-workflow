package co.vaale.bankpayment.h2h.port.`in`

interface AddPaymentMembershipUseCase {

    fun invoke(listCommercePlaceId: String?, entryDate: String?): String?
}