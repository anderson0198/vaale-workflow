package co.vaale.bankpayment.h2h.model

import co.com.groupware.common.domain.Crud
import co.com.groupware.lenpli.model.CommercePlaceSubscriptionPlanPayment
import lombok.EqualsAndHashCode
import lombok.Value

interface ICommercePlaceSubscriptionPlanPayment :
    Crud<Long, CommercePlaceSubscriptionPlanPayment, ICommercePlaceSubscriptionPlanPayment.Filter> {

    @Value
    @EqualsAndHashCode(callSuper = false)
    data class Filter (
        var isActive: Boolean? = null
    )
}