package co.vaale.bankpayment.h2h.model

import co.com.groupware.common.domain.Query
import co.com.groupware.lenpli.model.Setting
import lombok.EqualsAndHashCode
import lombok.Value

interface ISetting : Query<String, Setting, ISetting.Filter> {

    @Value
    @EqualsAndHashCode(callSuper = false)
    data class Filter (
        var id: String? = null
    )
}