package co.vaale.bankpayment.h2h.infrastructure.mysql

import co.com.groupware.lenpli.model.database.mapping.*
import co.vaale.bankpayment.h2h.port.out.IPaymentMembershipRepository
import io.swagger.client.models.PaymentMembership
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.SortField
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.SQLException
import java.util.*

@Repository
class PaymentMembershipRepository : IPaymentMembershipRepository {

    @Autowired
    private lateinit var dsl: DSLContext


    override fun getPaymentMembership(listCommercePlaceId: String?): List<PaymentMembership?>? {
        val list = try {

            val where = whereByFilter(listCommercePlaceId)
            val orderBy = arrayOf<SortField<*>>(DSL.inline(1).asc())

            dsl.select(
                CommercePlaceTable.ID.`as`("commercePlaceId"),
                CommercePlaceTable.NAME.`as`("commercePlaceName"),
                DSL.field(dsl.select(DSL.ifnull(DSL.sum(LoanQuotaUsedTable.AMOUNT), 0)).from(CommercePaymentTable.COMMERCE_PAYMENT).innerJoin(LoanQuotaUsedTable.LOAN_QUOTA_USED).on(CommercePaymentTable.ID.eq(LoanQuotaUsedTable.COMMERCE_PAYMENT_ID)).where(LoanQuotaUsedTable.IS_CANCELED.eq(false).and(CommercePaymentTable.COMMERCE_PLACE_ID.eq(CommercePlaceTable.ID)))).`as`("totalSalesAmount"),
                DSL.field(dsl.selectCount().from(CommercePlaceSubscriptionPlanPaymentTable.COMMERCE_PLACE_SUBSCRIPTION_PLAN_PAYMENT).where(CommercePlaceSubscriptionPlanPaymentTable.COMMERCE_PLACE_ID.eq(CommercePlaceTable.ID))).`as`("countSubscriptionPlanPayment"),
                DSL.field(dsl.select(DSL.cast(SettingTable.VALUE, Double::class.java)).from(SettingTable.SETTING).where(SettingTable.ID.eq("PREMIUM_MEMBERSHIP_CAP_VALUE"))).`as`("premiumMembershipCapValue"),
                DSL.field(dsl.select(DSL.cast(SettingTable.VALUE, Double::class.java)).from(SettingTable.SETTING).where(SettingTable.ID.eq("MIN_PREMIUM_MEMBERSHIP_CAP_VALUE"))).`as`("minPremiumMembershipCapValue"),
                DSL.field(dsl.select(DSL.cast(SettingTable.VALUE, Double::class.java)).from(SettingTable.SETTING).where(SettingTable.ID.eq("PREMIUM_MEMBERSHIP_VALUE"))).`as`("premiumMembershipValue")
                )
                .from(CommercePlaceTable.COMMERCE_PLACE)
                .innerJoin(CommercePlaceSubscriptionPlanTable.COMMERCE_PLACE_SUBSCRIPTION_PLAN).on(CommercePlaceSubscriptionPlanTable.COMMERCE_PLACE_ID.eq(CommercePlaceTable.ID))
                .where(where)
                .fetchInto<PaymentMembership>(PaymentMembership::class.java)

        } catch (e: Exception) {
            throw SQLException(e.cause)
        }

        return list
    }

    private fun whereByFilter(listCommercePlaceId: String?): Condition? {
        var where: Condition = DSL.trueCondition()
        where = where.and(CommercePlaceTable.IS_ACTIVE.eq(true)).and(CommercePlaceSubscriptionPlanTable.COMMERCE_PLAN_ID.eq(3))

        if (listCommercePlaceId != null && listCommercePlaceId?.length!! > 0) {
            try {
                val list = listCommercePlaceId?.split(",")?.map { it.trim() }
                where = where.and(CommercePlaceTable.ID.`in`(list))
            } catch (e: java.lang.Exception) {

            }
        }

        return where
    }
}