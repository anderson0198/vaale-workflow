package co.vaale.bankpayment.h2h.infrastructure.mysql

import co.vaale.bankpayment.h2h.port.out.ICommerceSummaryStatementRepository
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.CallableStatement
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class CommerceSummaryStatementRepository : ICommerceSummaryStatementRepository {

    @Autowired
    private lateinit var dsl: DSLContext

    override fun getStatementSubscription(initDate: String, endDate: String, placeId: Long): List<Map<String, Any>?>? {
        val query = "call lenpli.GetWeeklyCommerceSubscriptionStatementInfo(?, ?, ?);"
        var list = mutableListOf<Map<String, Any>>()
        try {
            dsl.configuration().connectionProvider().acquire().use { conn ->
                conn.prepareCall(query).use { cStmt ->
                    cStmt.setLong(1, placeId)
                    cStmt.setString(2, initDate)
                    cStmt.setString(3, endDate)

                    val rs = cStmt.executeQuery()
                    while (rs.next()) {
                        val commerceName = rs.getString(1)
                        val charWeek = rs.getString(2)
                        val initialBalanceAmount = rs.getDouble(3)
                        val salesAmount = rs.getDouble(4)
                        val collectionAmount = rs.getDouble(5)
                        val paymentsToCommerceAmount = rs.getDouble(6)
                        val paymentsToVaaleAmount = rs.getDouble(7)
                        val subscriptionAmount = rs.getDouble(8)
                        val cashbackAmount = rs.getDouble(9)
                        val balanceAmount = rs.getDouble(10)

                        list.add(
                            mapOf(
                                "commerceName" to commerceName,
                                "charWeek" to charWeek,
                                "initialBalanceAmount" to initialBalanceAmount,
                                "salesAmount" to salesAmount,
                                "collectionAmount" to collectionAmount,
                                "paymentsToCommerceAmount" to paymentsToCommerceAmount,
                                "paymentsToVaaleAmount" to paymentsToVaaleAmount,
                                "subscriptionAmount" to subscriptionAmount,
                                "cashbackAmount" to cashbackAmount,
                                "balanceAmount" to balanceAmount
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            throw SQLException(e.cause)
        }

        return list
    }
}