package co.vaale.workflow.h2h.model

import co.com.groupware.common.domain.Crud
import lombok.EqualsAndHashCode
import lombok.Value
import java.sql.SQLException
import java.sql.Time
import javax.swing.text.Segment

interface ISegment:
    Crud<Long, Segment, ISegment.Filter> {

    @Throws(SQLException::class)
    fun getProcessByTime(data: Time): List<Map<String, Any>>

    @Value
    @EqualsAndHashCode(callSuper = false)
    data class Filter (
        val name: String?
    )
}