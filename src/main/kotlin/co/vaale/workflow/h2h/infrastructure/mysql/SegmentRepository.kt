package co.vaale.workflow.h2h.infrastructure.mysql

import co.com.groupware.common.api.model.ApiPagination
import co.com.groupware.lenpli.model.database.mapping.SegmentTable
import co.vaale.workflow.h2h.model.ISegment
import co.vaale.workflow.h2h.port.out.ISegmentRepository
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.CallableStatement
import java.sql.ResultSet
import java.sql.SQLException
import javax.swing.text.Segment
@Repository
class SegmentRepository : ISegmentRepository {

    @Autowired
    private lateinit var dsl: DSLContext
    override fun getActivityByProcessId(data: String): List<Map<String, Any>> {
        val rows: MutableList<Map<String, Any>> = ArrayList()
        val conn = dsl.configuration().connectionProvider().acquire()
        val query = "{ CALL lenpli.getSegment(?)}"

        var cStmt: CallableStatement? = null
        var rs: ResultSet? = null
        try {
            cStmt = conn.prepareCall(query)
            cStmt.setString(1, data)
            rs = cStmt.executeQuery()

            while (rs.next()) {
                val segmentId = rs.getLong(1)
                val segmentName = rs.getString(2)

                val filterId = rs.getLong(3)
                val filterOperation = rs.getString(4)
                val filterAttributeName = rs.getString(5)
                val filterSegmentId = rs.getLong(6)
                val filerValue = rs.getString(7)

                val processId = rs.getLong(8)
                val processName = rs.getString(9)
                val processSegmentId = rs.getLong(10)





                rows.add(
                    mapOf(
                        "segmentId" to segmentId,
                        "segmentName" to segmentName,
                        "filterId" to filterId,
                        "filterOperation" to filterOperation,
                        "filterAttributeName" to filterAttributeName,
                        "filterSegmentId" to filterSegmentId,
                        "filerValue" to filerValue,
                        "processId" to processId,
                        "processName" to processName,
                        "processSegmentId" to processSegmentId
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


    override fun getById(id: Long?, schema: String?): Segment {
        TODO("Not yet implemented")
    }

    override fun get(filter: ISegment.Filter?, schema: String?): MutableList<Segment> {
        TODO("Not yet implemented")
    }

    override fun getWithPagination(
        apiPagination: ApiPagination?,
        filter: ISegment.Filter?,
        schema: String?
    ): MutableList<Segment> {
        TODO("Not yet implemented")
    }

    override fun countByFilter(filter: ISegment.Filter?, schema: String?): Int {
        TODO("Not yet implemented")
    }

    override fun save(t: Segment?, schema: String?): Long {
        TODO("Not yet implemented")
    }

    override fun update(t: Segment?, schema: String?): Long {
        TODO("Not yet implemented")
    }

    override fun active(id: Long?, active: Boolean?, schema: String?): Long {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long?, schema: String?): Long {
        TODO("Not yet implemented")
    }

    private fun whereByFilter(filter: ISegment.Filter?): Condition? {
        var where: Condition = DSL.trueCondition()
        if (filter?.name != null) {
            where = where.and(SegmentTable.NAME.eq(filter?.name))
        }
        return where
    }

}