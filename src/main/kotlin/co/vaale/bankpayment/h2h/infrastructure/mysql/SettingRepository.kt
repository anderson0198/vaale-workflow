package co.vaale.bankpayment.h2h.infrastructure.mysql

import co.com.groupware.common.api.model.ApiPagination
import co.com.groupware.lenpli.model.Setting
import co.com.groupware.lenpli.model.database.mapping.SettingTable
import co.vaale.bankpayment.h2h.model.ISetting
import co.vaale.bankpayment.h2h.port.out.ISettingRepository
import org.jooq.DSLContext
import org.jooq.Record3
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.SQLException

@Repository
class SettingRepository : ISettingRepository {

    @Autowired
    private lateinit var dsl: DSLContext

    override fun getById(id: String?, schema: String?): Setting {
        var `object`: Setting? = null
        try {
            val objectDb: Record3<*, *, *>? = dsl.select(
                SettingTable.ID.`as`("id"), SettingTable.VALUE.`as`("value"), SettingTable.IS_ACTIVE.`as`("isActive")
            )
                .from(SettingTable.SETTING)
                .where(SettingTable.ID.eq(id))
                .fetchAny()
            if (objectDb != null) {
                `object` = objectDb.into(Setting::class.java)
            }
        } catch (e: Exception) {
            throw SQLException(e.cause)
        }

        return `object`!!
    }

    override fun get(filter: ISetting.Filter?, schema: String?): MutableList<Setting> {
        TODO("Not yet implemented")
    }

    override fun getWithPagination(
        apiPagination: ApiPagination?,
        filter: ISetting.Filter?,
        schema: String?
    ): MutableList<Setting> {
        TODO("Not yet implemented")
    }

    override fun countByFilter(filter: ISetting.Filter?, schema: String?): Int {
        TODO("Not yet implemented")
    }
}