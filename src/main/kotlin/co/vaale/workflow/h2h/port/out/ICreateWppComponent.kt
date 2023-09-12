package co.vaale.workflow.h2h.port.out

import java.sql.SQLException

interface ICreateWppComponent {
    @Throws(SQLException::class)
    fun invoke(processId: Int, client: Map<String, Any?>, activity: Map<String, Any?>) : String?
    @Throws(SQLException::class)
    fun getTemplateName(processId: Int, activity: Map<String, Any?>) : String?
    @Throws(SQLException::class)
    fun getChannelId(processId: Int, activity: Map<String, Any?>) : String?
}