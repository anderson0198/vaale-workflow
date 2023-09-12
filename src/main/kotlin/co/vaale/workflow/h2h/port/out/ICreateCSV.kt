package co.vaale.workflow.h2h.port.out

import java.sql.SQLException

interface ICreateCSV {
    @Throws(SQLException::class)
    fun createCsvFile(clientList: List<Map<String, Any?>>):ByteArray
}