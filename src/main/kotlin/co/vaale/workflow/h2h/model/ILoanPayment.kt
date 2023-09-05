package co.vaale.workflow.h2h.model

import co.com.groupware.common.domain.Crud
import co.com.groupware.lenpli.model.LoanPayment
import lombok.EqualsAndHashCode
import lombok.Value
import java.sql.SQLException

interface ILoanPayment :
        Crud<Long, LoanPayment, ILoanPayment.Filter> {

    @Throws(SQLException::class)
    fun getClientWithActivePurchases(): List<Map<String, Any>>

    @Value
    @EqualsAndHashCode(callSuper = false)
    data class Filter (
            val clientId: Long?,
            val loanId: Long?
    )
}