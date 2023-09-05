package co.vaale.workflow.h2h.infrastructure.mysql

import co.com.groupware.common.api.model.ApiPagination
import co.com.groupware.lenpli.model.LoanPayment
import co.vaale.workflow.h2h.model.ILoanPayment
import co.vaale.workflow.h2h.port.out.ILoanPaymentRepository
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.CallableStatement
import java.sql.ResultSet
import java.sql.SQLException
@Repository
class LoanPaymentRepository : ILoanPaymentRepository {

    @Autowired
    private lateinit var dsl: DSLContext
    override fun getClientWithActivePurchases(): List<Map<String, Any>> {
        val rows: MutableList<Map<String, Any>> = ArrayList()
        val conn = dsl.configuration().connectionProvider().acquire()
        val query = "{ CALL lenpli.GetClientWithActivePurchases()}"

        var cStmt: CallableStatement? = null
        var rs: ResultSet? = null
        try {
            cStmt = conn.prepareCall(query)
            rs = cStmt.executeQuery()

            while (rs.next()) {
                val clientId = rs.getLong(1)
                val clientName = rs.getString(2)
                val clientDocumentKey = rs.getString(3)
                val clientCellPhone = rs.getString(4)
                val referrerName = rs.getString(5)
                val inPastDue = rs.getString(6)
                val amountOverdue = rs.getDouble(7)
                val daysPastDue = rs.getInt(8)
                val daysSincePurchaseToStartPayment = rs.getInt(9)
                val dateToPay = rs.getString(12)

                rows.add(
                        mapOf(
                                "clientId" to clientId,
                                "clientDocumentKey" to clientDocumentKey,
                                "clientName" to clientName,
                                "clientCellPhone" to clientCellPhone,
                                "referrerName" to referrerName,
                                "inPastDue" to inPastDue,
                                "amountOverdue" to amountOverdue,
                                "daysPastDue" to daysPastDue,
                                "daysSincePurchaseToStartPayment" to daysSincePurchaseToStartPayment,
                                "dateToPay" to dateToPay
                        )
                )
            }
        } catch (e: Exception) {
            throw SQLException(e.cause)
        } finally {
            cStmt?.close()
            rs?.close()
        }

        return rows
    }


    override fun getById(id: Long?, schema: String?): LoanPayment {
        TODO("Not yet implemented")
    }

    override fun get(filter: ILoanPayment.Filter?, schema: String?): MutableList<LoanPayment> {
        TODO("Not yet implemented")
    }

    override fun getWithPagination(apiPagination: ApiPagination?, filter: ILoanPayment.Filter?, schema: String?): MutableList<LoanPayment> {
        TODO("Not yet implemented")
    }

    override fun countByFilter(filter: ILoanPayment.Filter?, schema: String?): Int {
        TODO("Not yet implemented")
    }

    override fun save(t: LoanPayment?, schema: String?): Long {
        TODO("Not yet implemented")
    }

    override fun update(t: LoanPayment?, schema: String?): Long {
        TODO("Not yet implemented")
    }

    override fun active(id: Long?, active: Boolean?, schema: String?): Long {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long?, schema: String?): Long {
        TODO("Not yet implemented")
    }
}