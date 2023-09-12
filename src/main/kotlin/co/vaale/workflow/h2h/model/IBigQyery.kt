package co.vaale.workflow.h2h.model

import java.sql.SQLException

interface IBigQyery {
    @Throws(SQLException::class)
    fun getClientWithActiveLoan(map: List<Map<String, Any>>): List<Map<String, Any>>
}