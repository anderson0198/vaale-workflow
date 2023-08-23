package co.vaale.bankpayment.h2h.api

import co.com.groupware.common.api.model.ApiRequestResponse
import co.com.groupware.common.api.model.ApiResponseData
import co.vaale.bankpayment.h2h.model.H2hTransaction
import co.vaale.bankpayment.h2h.port.`in`.GetCommercePaymentTransactionRequestUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@javax.annotation.Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2023-04-15T01:43:53.783827847Z[GMT]")
@RestController
class GetCommercePaymentTransactionRequestApiController : GetCommercePaymentTransactionRequestApi {

    private val log: Logger = LoggerFactory.getLogger(GetCommercePaymentTransactionRequestApiController::class.java)

    @Autowired
    private lateinit var getCommercePaymentTransactionRequestUseCase: GetCommercePaymentTransactionRequestUseCase

    override fun getCommercePaymentRequest(listCommercePlaceId: String?, listPlanId: String?): ResponseEntity<ApiResponseData<H2hTransaction>?>? {
        val responseData: ApiResponseData<H2hTransaction> = ApiResponseData<H2hTransaction>()
        val apiResponse = ApiRequestResponse()
        apiResponse.code = 200
        apiResponse.type = "OK"
        apiResponse.systemMessage = ""
        apiResponse.stackTrace = ""

        try {
            responseData.data = getCommercePaymentTransactionRequestUseCase.invoke(listCommercePlaceId, listPlanId)
            responseData.setApiResponse(apiResponse)
        } catch (e: Exception) {
            apiResponse.code = 500
            apiResponse.message = e.message
            responseData.setApiResponse(apiResponse)
        }

        return ResponseEntity<ApiResponseData<H2hTransaction>?>(responseData, HttpStatus.OK)
    }
}