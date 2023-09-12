package co.vaale.workflow.h2h.port.out

import java.sql.SQLException

interface ISendWpp {
    @Throws(SQLException::class)
    fun send(to: String, name: String, components: String, phoneNumberId: String)
}