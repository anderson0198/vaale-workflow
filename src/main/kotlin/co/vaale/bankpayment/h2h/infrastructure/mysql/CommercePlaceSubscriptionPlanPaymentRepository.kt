package co.vaale.bankpayment.h2h.infrastructure.mysql

import co.com.groupware.common.api.model.ApiPagination
import co.com.groupware.lenpli.model.CommercePlaceSubscriptionPlanPayment
import co.com.groupware.lenpli.model.database.mapping.CommercePlaceSubscriptionPlanPaymentTable
import co.vaale.bankpayment.h2h.model.ICommercePlaceSubscriptionPlanPayment
import co.vaale.bankpayment.h2h.port.out.ICommercePlaceSubscriptionPlanPaymentRepository
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.SQLException
import java.text.SimpleDateFormat
import java.util.Date

@Repository
class CommercePlaceSubscriptionPlanPaymentRepository : ICommercePlaceSubscriptionPlanPaymentRepository {

    @Autowired
    private lateinit var dsl: DSLContext

    override fun getById(id: Long?, schema: String?): CommercePlaceSubscriptionPlanPayment {
        TODO("Not yet implemented")
    }

    override fun get(
        filter: ICommercePlaceSubscriptionPlanPayment.Filter?,
        schema: String?
    ): MutableList<CommercePlaceSubscriptionPlanPayment> {
        TODO("Not yet implemented")
    }

    override fun getWithPagination(
        apiPagination: ApiPagination?,
        filter: ICommercePlaceSubscriptionPlanPayment.Filter?,
        schema: String?
    ): MutableList<CommercePlaceSubscriptionPlanPayment> {
        TODO("Not yet implemented")
    }

    override fun countByFilter(filter: ICommercePlaceSubscriptionPlanPayment.Filter?, schema: String?): Int {
        TODO("Not yet implemented")
    }

    override fun save(t: CommercePlaceSubscriptionPlanPayment?, schema: String?): Long {
        var id: Long = 0
        id = try {
            dsl.insertInto(
                CommercePlaceSubscriptionPlanPaymentTable.COMMERCE_PLACE_SUBSCRIPTION_PLAN_PAYMENT,
                CommercePlaceSubscriptionPlanPaymentTable.COMMERCE_PLACE_ID,
                CommercePlaceSubscriptionPlanPaymentTable.AMOUNT,
                CommercePlaceSubscriptionPlanPaymentTable.DESCRIPTION,
                CommercePlaceSubscriptionPlanPaymentTable.ENTRY_DATE
            )
                .values(
                    t?.commercePlace?.id,
                    t?.amount,
                    t?.description,
                    if (t?.entryDate != null)  SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(t?.entryDate) else Date()
                )
                .returningResult<Long>(CommercePlaceSubscriptionPlanPaymentTable.ID)
                .fetchOne()
            dsl.lastID().toLong()
        } catch (e: Exception) {
            throw SQLException(e.cause)
        }
        return id
    }

    override fun update(t: CommercePlaceSubscriptionPlanPayment?, schema: String?): Long {
        TODO("Not yet implemented")
    }

    override fun active(id: Long?, active: Boolean?, schema: String?): Long {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long?, schema: String?): Long {
        TODO("Not yet implemented")
    }
}