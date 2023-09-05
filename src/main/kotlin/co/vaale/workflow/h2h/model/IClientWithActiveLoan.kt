package co.vaale.workflow.h2h.model

import co.com.groupware.common.domain.Crud
import co.com.groupware.lenpli.model.Activity
import co.com.groupware.lenpli.model.ClientWithActiveLoan
import co.com.groupware.lenpli.model.LoanPayment
import lombok.EqualsAndHashCode
import lombok.Value
import java.sql.SQLException

interface IClientWithActiveLoan {

    @Throws(SQLException::class)
    fun getClientWithActiveLoan(filter: Filter): List<Map<String, Any>>

    @Value
    @EqualsAndHashCode(callSuper = false)
    data class Filter(
        val clientId : Long?,
        val clientName : String?,
        val clientDocumentNumber : String?,
        val clientCellPhone : String?,
        val entryDateTime : String?,
        val entryDate : String?,
        val entryTime : String?,
        val referrerName : String?,
        val inPastDue : String?,
        val amountOverdueWithFee : Long?,
        val amountOverdueWithoutFee : Long?,
        val daysPastDue : String?,
        val daysSincePurchaseToStartPayment : String?,
        val dateLastPurchase : String?,
        val commerceNameLastPurchase : String?,
        val dateToPay : String?,
        val commerceNeighborhood : String?,
        val commerceCity : String?,
        val amountToPay : Long?,
        val amountPurchase : Long?,
        val isClientPaymentAgreement : String?

    )
}