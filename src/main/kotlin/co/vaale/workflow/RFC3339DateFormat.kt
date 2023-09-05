package co.vaale.workflow

import com.fasterxml.jackson.databind.util.ISO8601DateFormat
import com.fasterxml.jackson.databind.util.ISO8601Utils
import java.text.FieldPosition
import java.util.*

class RFC3339DateFormat: ISO8601DateFormat() {

    private val serialVersionUID = 1L

    override fun format(date: Date?, toAppendTo: StringBuffer, fieldPosition: FieldPosition?): StringBuffer? {
        val value = ISO8601Utils.format(date, true)
        toAppendTo.append(value)
        return toAppendTo
    }
}