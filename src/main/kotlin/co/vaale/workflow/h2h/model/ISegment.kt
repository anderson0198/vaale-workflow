package co.vaale.workflow.h2h.model

import co.com.groupware.common.domain.Crud
import lombok.EqualsAndHashCode
import lombok.Value
import java.sql.SQLException
import javax.swing.text.Segment

interface ISegment:
    Crud<Long, Segment, ISegment.Filter> {

    @Throws(SQLException::class)
    fun getActivityByProcessId(data: String): List<Map<String, Any>>

    @Value
    @EqualsAndHashCode(callSuper = false)
    data class Filter (
        val name: String?
    )
}