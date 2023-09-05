package co.vaale.workflow.h2h.model

import co.com.groupware.common.domain.Crud
import co.com.groupware.lenpli.model.Activity
import lombok.EqualsAndHashCode
import lombok.Value
import java.sql.SQLException

interface IActivity:
    Crud<Long, Activity, IActivity.Filter> {

    @Throws(SQLException::class)
    fun getActivityByProcessId(processId: Int): List<Map<String, Any>>

    @Value
    @EqualsAndHashCode(callSuper = false)
    data class Filter (
        val name: String?
    )
}