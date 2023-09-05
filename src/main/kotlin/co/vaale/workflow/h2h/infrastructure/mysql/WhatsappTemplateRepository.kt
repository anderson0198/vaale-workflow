package co.vaale.workflow.h2h.infrastructure.mysql

import co.com.groupware.common.api.model.ApiPagination
import co.com.groupware.lenpli.model.WhatsappTemplate
import co.com.groupware.lenpli.model.database.mapping.WhatsappTemplateTable
import co.vaale.workflow.h2h.model.IWhatsappTemplate
import co.vaale.workflow.h2h.port.out.IWhatsappTemplateRepository
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.SortField
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.SQLException

@Repository
class WhatsappTemplateRepository: IWhatsappTemplateRepository {
    @Autowired
    private lateinit var dsl: DSLContext
    override fun getById(id: Long?, schema: String?): WhatsappTemplate {
        TODO("Not yet implemented")
    }

    override fun get(filter: IWhatsappTemplate.Filter?, schema: String?): MutableList<WhatsappTemplate> {
        val list = try {
            val where = whereByFilter(filter)
            val orderBy = arrayOf<SortField<*>>(DSL.inline(1).asc())
            dsl.select(WhatsappTemplateTable.ID.`as`("id"),
                WhatsappTemplateTable.NAME.`as`("name"),
                WhatsappTemplateTable.ATTRIBUTES.`as`("attributes"),
                WhatsappTemplateTable.CHANNEL_ID.`as`("channel_id"),
                WhatsappTemplateTable.COMPONENT.`as`("component")
            )
                .from(WhatsappTemplateTable.WHATSAPP_TEMPLATE)
                .where(where)
                .fetchInto<WhatsappTemplate>(WhatsappTemplate::class.java)
        } catch (e: Exception) {
            throw SQLException(e.cause)
        }
        return list

    }

    override fun getWithPagination(
        apiPagination: ApiPagination?,
        filter: IWhatsappTemplate.Filter?,
        schema: String?
    ): MutableList<WhatsappTemplate> {
        TODO("Not yet implemented")
    }

    override fun countByFilter(filter: IWhatsappTemplate.Filter?, schema: String?): Int {
        TODO("Not yet implemented")
    }

    override fun save(t: WhatsappTemplate?, schema: String?): Long {
        TODO("Not yet implemented")
    }

    override fun update(t: WhatsappTemplate?, schema: String?): Long {
        TODO("Not yet implemented")
    }

    override fun active(id: Long?, active: Boolean?, schema: String?): Long {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long?, schema: String?): Long {
        TODO("Not yet implemented")
    }

    private fun whereByFilter(filter: IWhatsappTemplate.Filter?): Condition? {
        var where: Condition = DSL.trueCondition()
        if (filter?.name != null) {
            where = where.and(WhatsappTemplateTable.NAME.eq(filter?.name))
        }
        return where
    }
}