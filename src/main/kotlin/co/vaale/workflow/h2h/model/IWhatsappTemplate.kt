package co.vaale.workflow.h2h.model

import co.com.groupware.common.domain.Crud
import co.com.groupware.lenpli.model.WhatsappTemplate
import lombok.EqualsAndHashCode
import lombok.Value

interface IWhatsappTemplate:
Crud<Long, WhatsappTemplate, IWhatsappTemplate.Filter> {

    @Value
    @EqualsAndHashCode(callSuper = false)
    data class Filter (
        val name: String?
    )
}