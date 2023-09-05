package co.vaale.workflow.h2h.infrastructure.mysql

import co.com.groupware.common.api.model.ApiPagination
import co.com.groupware.lenpli.model.Activity
import co.vaale.workflow.h2h.model.IActivity
import co.vaale.workflow.h2h.port.out.IActivityRepository
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.CallableStatement
import java.sql.ResultSet
import java.sql.SQLException
@Repository
class ActivityRepository: IActivityRepository {
    @Autowired
    private lateinit var dsl: DSLContext
    override fun getActivityByProcessId(processId: Int): List<Map<String, Any>> {
        val rows: MutableList<Map<String, Any>> = ArrayList()
        val conn = dsl.configuration().connectionProvider().acquire()
        val query = "{ CALL lenpli.getActivityByProcessId(?)}"

        var cStmt: CallableStatement? = null
        var rs: ResultSet? = null
        try {
            cStmt = conn.prepareCall(query)
            cStmt.setInt(1, processId)
            rs = cStmt.executeQuery()

            while (rs.next()) {
                val activityId = rs.getLong(1)
                val activityName = rs.getString(2)

                val whatsappTemplateName = rs.getString(5)
                val whatsappTemplateComponent = rs.getString(6)
                val whatsappTemplateAttributes = rs.getString(7)
                val whatsappTemplateChannelId = rs.getString(8)

                val smsTemplateAttributes = rs.getString(10)
                val smsTemplateChannelId = rs.getString(11)
                val smsTemplateText = rs.getString(12)


                val segmentFileTo = rs.getString(14)
                val segmentFileReply = rs.getString(15)



                rows.add(
                    mapOf(
                        "activityId" to activityId,
                        "activityName" to activityName,
                        "whatsappTemplateName" to whatsappTemplateName,
                        "whatsappTemplateComponent" to whatsappTemplateComponent,
                        "whatsappTemplateAttributes" to whatsappTemplateAttributes,
                        "whatsappTemplateChannelId" to whatsappTemplateChannelId,
                        "smsTemplateText" to smsTemplateText,
                        "smsTemplateAttributes" to smsTemplateAttributes,
                        "smsTemplateChannelId" to smsTemplateChannelId,
                        "segmentFileTo" to segmentFileTo,
                        "segmentFileReply" to segmentFileReply
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

    override fun getById(id: Long?, schema: String?): Activity {
        TODO("Not yet implemented")
    }

    override fun get(filter: IActivity.Filter?, schema: String?): MutableList<Activity> {
        TODO("Not yet implemented")
    }

    override fun getWithPagination(
        apiPagination: ApiPagination?,
        filter: IActivity.Filter?,
        schema: String?
    ): MutableList<Activity> {
        TODO("Not yet implemented")
    }

    override fun countByFilter(filter: IActivity.Filter?, schema: String?): Int {
        TODO("Not yet implemented")
    }

    override fun save(t: Activity?, schema: String?): Long {
        TODO("Not yet implemented")
    }

    override fun update(t: Activity?, schema: String?): Long {
        TODO("Not yet implemented")
    }

    override fun active(id: Long?, active: Boolean?, schema: String?): Long {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long?, schema: String?): Long {
        TODO("Not yet implemented")
    }
}