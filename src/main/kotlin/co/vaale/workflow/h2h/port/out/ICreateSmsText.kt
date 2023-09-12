package co.vaale.workflow.h2h.port.out

import java.sql.SQLException

interface ICreateSmsText {
    @Throws(SQLException::class)
    fun invoke(processId: Int, client: Map<String, Any?>, activity: Map<String, Any?>) : String?
}