package co.vaale.workflow.h2h.port.out

import java.sql.SQLException

interface ISendSms {
    @Throws(SQLException::class)
    fun sendSMS(to: String, text: String)
}