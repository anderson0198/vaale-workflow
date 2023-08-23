package co.vaale.bankpayment.h2h.model

import co.com.groupware.common.domain.Query
import co.com.groupware.lenpli.model.CommercePlacePaymentMethod
import lombok.EqualsAndHashCode
import lombok.Value

interface ICommercePlacePaymentMethod : Query<Long, CommercePlacePaymentMethod, ICommercePlacePaymentMethod.Filter> {

    @Value
    @EqualsAndHashCode(callSuper = false)
    data class Filter (
        var commercePlaceId: Long? = null,
        var isActive: Boolean? = null
    )
}