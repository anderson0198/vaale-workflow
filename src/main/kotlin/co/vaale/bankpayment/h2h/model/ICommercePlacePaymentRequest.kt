package co.vaale.bankpayment.h2h.model

import co.com.groupware.common.domain.Crud
import co.com.groupware.common.domain.Query
import co.com.groupware.lenpli.model.CommercePlacePaymentRequest
import lombok.EqualsAndHashCode
import lombok.Value

interface ICommercePlacePaymentRequest :
    Crud<Long, CommercePlacePaymentRequest, ICommercePlacePaymentRequest.Filter> {

    @Value
    @EqualsAndHashCode(callSuper = false)
    data class Filter (
        var listCommercePlaceId: String? = null
        , var listPlanId: String? = null
        , var isDone: Boolean? = null
    )

}