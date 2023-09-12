package co.vaale.workflow.h2h.port.out

import java.io.File
import java.sql.SQLException

interface ISendMail {
    @Throws(SQLException::class)
    fun sendMail(to: String, reply: String, byteArray: ByteArray)

    @Throws(SQLException::class)
    fun getTo(activityList: List<Map<String, Any?>>): String?
    @Throws(SQLException::class)
    fun invoke(clientList: List<Map<String, Any?>>, to: String)
}