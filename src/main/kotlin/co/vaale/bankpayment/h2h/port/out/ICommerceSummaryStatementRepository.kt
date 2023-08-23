package co.vaale.bankpayment.h2h.port.out

interface ICommerceSummaryStatementRepository {
    fun getStatementSubscription(initDate: String, endDate: String, placeId: Long): List<Map<String, Any>?>?
}