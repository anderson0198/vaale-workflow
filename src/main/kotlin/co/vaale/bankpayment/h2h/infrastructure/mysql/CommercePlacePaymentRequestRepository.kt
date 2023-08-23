package co.vaale.bankpayment.h2h.infrastructure.mysql

import co.com.groupware.common.api.model.ApiPagination
import co.com.groupware.lenpli.model.CommercePlacePaymentRequest
import co.com.groupware.lenpli.model.database.mapping.*
import co.vaale.bankpayment.h2h.model.ICommercePlacePaymentRequest
import co.vaale.bankpayment.h2h.port.out.ICommercePlacePaymentRequestRepository
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.SortField
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.lang.Boolean
import java.sql.SQLException
import java.util.*
import kotlin.Exception
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.TODO
import kotlin.arrayOf

@Repository
class CommercePlacePaymentRequestRepository: ICommercePlacePaymentRequestRepository {

    @Autowired
    private lateinit var dsl: DSLContext

    override fun getById(id: Long?, schema: String?): CommercePlacePaymentRequest {
        TODO("Not yet implemented")
    }

    override fun get(
        filter: ICommercePlacePaymentRequest.Filter?,
        schema: String?
    ): MutableList<CommercePlacePaymentRequest> {

        val list = try {

            val where = whereByFilter(filter)
            val orderBy = arrayOf<SortField<*>>(DSL.inline(1).asc())

            dsl.select(
                CommercePlacePaymentRequestTable.ID.`as`("id"),
                CommercePlacePaymentRequestTable.AMOUNT.`as`("amount"),
                CommercePlacePaymentRequestTable.DESCRIPTION.`as`("description"),
                CommercePlacePaymentRequestTable.ENTRY_DATE.`as`("entryDate"),
                CommercePlacePaymentRequestTable.PAYMENT_DATE.`as`("paymentDate"),
                CommercePlacePaymentRequestTable.IS_DONE.`as`("isDone"),
                CommercePlacePaymentRequestTable.IS_ADVANCE_REQUEST.`as`("isAdvanceRequest"),
                CommercePlacePaymentRequestTable.PAYMENT_INTEREST.`as`("paymentInterest"),
                CommercePlaceTable.ID.`as`("commercePlace.id"),
                CommercePlaceTable.NAME.`as`("commercePlace.name"),
                CommercePlaceTable.EMAIL.`as`("commercePlace.email"),
                CommercePlaceTable.CELL_PHONE.`as`("commercePlace.cellPhone"),
                CommercePlaceTable.ALTERNATIVE_TELEPHONE_NUMBER.`as`("commercePlace.alternativeTelephoneNumber"),
                CommercePlaceTable.ADDRESS.`as`("commercePlace.address"),
                CommercePlaceTable.LAT.`as`("commercePlace.lat"),
                CommercePlaceTable.LON.`as`("commercePlace.lon"),
                CommercePlaceTable.IMG_URL.`as`("commercePlace.imgUrl"),
                CommercePlaceTable.OPENING_TIME.`as`("commercePlace.openingTime"),
                CommercePlacePaymentMethodTable.NAME.`as`("commercePlacePaymentMethod.name"),
                CommercePlacePaymentMethodTable.ACCOUNT_NUMBER.`as`("commercePlacePaymentMethod.accountNumber"),
                CommercePlacePaymentMethodTable.ACCOUNT_HOLDER_NAME.`as`("commercePlacePaymentMethod.accountHolderName"),
                CommercePlacePaymentMethodTable.ACCOUNT_HOLDER_DOCUMENT_KEY.`as`("commercePlacePaymentMethod.accountHolderDocumentKey"),
                CommercePlacePaymentMethodTable.ACCOUNT_HOLDER_DOCUMENT_TYPE_ID.`as`("commercePlacePaymentMethod.accountHolderDocumentType.id"),
                DSL.field(dsl.select(DocumentTypeTable.NAME).from(DocumentTypeTable.DOCUMENT_TYPE).where(DocumentTypeTable.ID.eq(CommercePlacePaymentMethodTable.ACCOUNT_HOLDER_DOCUMENT_TYPE_ID))).`as`("commercePlacePaymentMethod.accountHolderDocumentType.name"),
                DSL.field(dsl.select(DocumentTypeTable.H2H_CODE).from(DocumentTypeTable.DOCUMENT_TYPE).where(DocumentTypeTable.ID.eq(CommercePlacePaymentMethodTable.ACCOUNT_HOLDER_DOCUMENT_TYPE_ID))).`as`("commercePlacePaymentMethod.accountHolderDocumentType.h2hCode"),
                CityTable.ID.`as`("commercePlace.city.id"),
                CityTable.NAME.`as`("commercePlace.city.name"),
                CommerceTable.ID.`as`("commercePlace.commerce.id"),
                CommerceTable.NAME.`as`("commercePlace.commerce.name"),
                CommerceTable.DOCUMENT_KEY.`as`("commercePlace.commerce.documentKey"),
                CommerceTable.DOCUMENT_TYPE_ID.`as`("commercePlace.commerce.documentType.id"),
                DSL.field(dsl.select(DocumentTypeTable.NAME).from(DocumentTypeTable.DOCUMENT_TYPE).where(DocumentTypeTable.ID.eq(CommerceTable.DOCUMENT_TYPE_ID))).`as`("commercePlace.commerce.documentType.name"),
                DSL.field(dsl.select(DocumentTypeTable.H2H_CODE).from(DocumentTypeTable.DOCUMENT_TYPE).where(DocumentTypeTable.ID.eq(CommerceTable.DOCUMENT_TYPE_ID))).`as`("commercePlace.commerce.documentType.h2hCode"),
                BankTable.ID.`as`("commercePlacePaymentMethod.bank.id"),
                BankTable.NAME.`as`("commercePlacePaymentMethod.bank.name"),
                BankTable.H2H_CODE.`as`("commercePlacePaymentMethod.bank.h2hCode"),
                BankAccountTypeTable.ID.`as`("commercePlacePaymentMethod.bankAccountType.id"),
                BankAccountTypeTable.NAME.`as`("commercePlacePaymentMethod.bankAccountType.name"),
                BankAccountTypeTable.H2H_CODE.`as`("commercePlacePaymentMethod.bankAccountType.h2hCode")
            )
                .from(CommercePlacePaymentRequestTable.COMMERCE_PLACE_PAYMENT_REQUEST)
                .innerJoin(CommercePlaceTable.COMMERCE_PLACE)
                .on(CommercePlacePaymentRequestTable.COMMERCE_PLACE_ID.eq(CommercePlaceTable.ID))
                .innerJoin(CommerceTable.COMMERCE).on(CommercePlaceTable.COMMERCE_ID.eq(CommerceTable.ID))
                .innerJoin(CommercePlacePaymentMethodTable.COMMERCE_PLACE_PAYMENT_METHOD)
                .on(CommercePlacePaymentRequestTable.COMMERCE_PLACE_PAYMENT_METHOD_ID.eq(CommercePlacePaymentMethodTable.ID))
                .leftJoin(BankTable.BANK).on(BankTable.ID.eq(CommercePlacePaymentMethodTable.BANK_ID))
                .leftJoin(BankAccountTypeTable.BANK_ACOUNT_TYPE).on(BankAccountTypeTable.ID.eq(CommercePlacePaymentMethodTable.BANK_ACOUNT_TYPE_ID))
                .leftJoin(CityTable.CITY).on(CommercePlaceTable.CITY_ID.eq(CityTable.ID))
                .where(where)
                .orderBy<Date>(CommercePlacePaymentRequestTable.ENTRY_DATE.desc())
                .fetchInto<CommercePlacePaymentRequest>(CommercePlacePaymentRequest::class.java)

        } catch (e: Exception) {
            throw SQLException(e.cause)
        }

        return list
    }

    override fun getWithPagination(
        apiPagination: ApiPagination?,
        filter: ICommercePlacePaymentRequest.Filter?,
        schema: String?
    ): MutableList<CommercePlacePaymentRequest> {
        TODO("Not yet implemented")
    }

    override fun countByFilter(filter: ICommercePlacePaymentRequest.Filter?, schema: String?): Int {
        TODO("Not yet implemented")
    }

    override fun save(t: CommercePlacePaymentRequest?, schema: String?): Long {
        var id: Long = 0
        id = try {
            dsl.insertInto(
                CommercePlacePaymentRequestTable.COMMERCE_PLACE_PAYMENT_REQUEST,
                CommercePlacePaymentRequestTable.COMMERCE_PLACE_ID,
                CommercePlacePaymentRequestTable.COMMERCE_PLACE_PAYMENT_METHOD_ID,
                CommercePlacePaymentRequestTable.AMOUNT,
                CommercePlacePaymentRequestTable.PAYMENT_INTEREST,
                CommercePlacePaymentRequestTable.IS_ADVANCE_REQUEST,
                CommercePlacePaymentRequestTable.IS_DONE,
                CommercePlacePaymentRequestTable.DESCRIPTION,
                CommercePlacePaymentRequestTable.IS_ACTIVE,


            )
                .values(
                    t?.commercePlace?.id,
                    t?.commercePlacePaymentMethod?.id,
                    t?.amount,
                    t?.paymentInterest,
                    t?.isIsAdvanceRequest,
                    false,
                    t?.description,
                    t?.isIsActive
                )
                .returningResult<Long>(CommercePlacePaymentRequestTable.ID)
                .fetchOne()
            dsl.lastID().toLong()
        } catch (e: Exception) {
            throw SQLException(e.cause)
        }
        return id
    }

    override fun update(t: CommercePlacePaymentRequest?, schema: String?): Long {
        TODO("Not yet implemented")
    }

    override fun active(id: Long?, active: kotlin.Boolean?, schema: String?): Long {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long?, schema: String?): Long {
        TODO("Not yet implemented")
    }

    private fun whereByFilter(filter: ICommercePlacePaymentRequest.Filter?): Condition? {
        var where: Condition = DSL.trueCondition()
        where = where.and(CommercePlacePaymentRequestTable.IS_ACTIVE.eq(Boolean.TRUE))

        if (filter?.isDone != null) {
            where = where.and(CommercePlacePaymentRequestTable.IS_DONE.eq(filter?.isDone))
        }

        if (filter?.listPlanId != null && filter?.listPlanId?.length!! > 0) {
            try {
                val list = filter?.listPlanId?.split(",")?.map { it.trim() }
                where = where.and(CommercePlacePaymentRequestTable.COMMERCE_PLACE_ID.`in`(DSL.field(dsl.select(CommercePlaceSubscriptionPlanTable.COMMERCE_PLACE_ID).from(CommercePlaceSubscriptionPlanTable.COMMERCE_PLACE_SUBSCRIPTION_PLAN).where(CommercePlaceSubscriptionPlanTable.COMMERCE_PLAN_ID.`in`(list)))))
            } catch (e: java.lang.Exception) {

            }
        }

        if (filter?.listCommercePlaceId != null && filter?.listCommercePlaceId?.length!! > 0) {
            try {
                val list = filter?.listCommercePlaceId?.split(",")?.map { it.trim() }
                where = where.and(CommercePlacePaymentRequestTable.COMMERCE_PLACE_ID.`in`(list))
            } catch (e: java.lang.Exception) {

            }
        }

        return where
    }
}