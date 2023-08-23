package co.vaale.bankpayment.h2h.infrastructure.mysql

import co.com.groupware.common.api.model.ApiPagination
import co.com.groupware.lenpli.model.CommercePlacePaymentMethod
import co.com.groupware.lenpli.model.database.mapping.CommercePlacePaymentMethodTable
import co.vaale.bankpayment.h2h.model.ICommercePlacePaymentMethod
import co.vaale.bankpayment.h2h.port.out.ICommercePlacePaymentMethodRepository
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.SortField
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.SQLException

@Repository
class CommercePlacePaymentMethodRepository : ICommercePlacePaymentMethodRepository {

    @Autowired
    private lateinit var dsl: DSLContext

    override fun getById(id: Long?, schema: String?): CommercePlacePaymentMethod {
        TODO("Not yet implemented")
    }

    override fun get(
        filter: ICommercePlacePaymentMethod.Filter?,
        schema: String?
    ): MutableList<CommercePlacePaymentMethod> {

        val list = try {

            val where = whereByFilter(filter)
            val orderBy = arrayOf<SortField<*>>(DSL.inline(1).asc())
            dsl.select(CommercePlacePaymentMethodTable.ID.`as`("id"))
                .from(CommercePlacePaymentMethodTable.COMMERCE_PLACE_PAYMENT_METHOD)
                .where(where)
                .fetchInto<CommercePlacePaymentMethod>(CommercePlacePaymentMethod::class.java)
        } catch (e: Exception) {
            throw SQLException(e.cause)
        }

        return list
    }

    override fun getWithPagination(
        apiPagination: ApiPagination?,
        filter: ICommercePlacePaymentMethod.Filter?,
        schema: String?
    ): MutableList<CommercePlacePaymentMethod> {
        TODO("Not yet implemented")
    }

    override fun countByFilter(filter: ICommercePlacePaymentMethod.Filter?, schema: String?): Int {
        TODO("Not yet implemented")
    }

    private fun whereByFilter(filter: ICommercePlacePaymentMethod.Filter?): Condition? {
        var where: Condition = DSL.trueCondition()

        if (filter?.isActive != null) {
            where = where.and(CommercePlacePaymentMethodTable.IS_ACTIVE.eq(filter?.isActive))
        }

        if (filter?.commercePlaceId != null) {
            where = where.and(CommercePlacePaymentMethodTable.COMMERCE_PLACE_ID.eq(filter?.commercePlaceId))
        }

        return where
    }
}