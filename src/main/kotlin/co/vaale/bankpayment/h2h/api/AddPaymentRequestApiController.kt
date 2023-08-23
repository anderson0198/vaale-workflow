package co.vaale.bankpayment.h2h.api

import co.com.groupware.common.api.model.ApiRequestResponse
import co.vaale.bankpayment.h2h.port.`in`.AddPaymentRequestUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@javax.annotation.Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2023-04-21T21:24:26.616067176Z[GMT]")
@RestController
class AddPaymentRequestApiController : AddPaymentRequestApi {

    private val log: Logger = LoggerFactory.getLogger(AddPaymentRequestApiController::class.java)

    @Autowired
    private lateinit var addPaymentRequestUseCase: AddPaymentRequestUseCase

    override fun addPaymentRequest(
         listCommercePlaceId: String?,
         initDate: String?,
         endDate: String?
     ): ResponseEntity<ApiRequestResponse?>? {
        val apiResponse = ApiRequestResponse()
        apiResponse.code = 200
        apiResponse.type = "REGISTER"
        apiResponse.message = "Reg"
        apiResponse.systemMessage = ""
        apiResponse.stackTrace = ""
        try {
            addPaymentRequestUseCase.invoke(listCommercePlaceId, initDate, endDate)
        } catch (e: Exception) {
            apiResponse.code = 500
            apiResponse.message = e.message
        }

        return ResponseEntity(apiResponse, HttpStatus.OK)
    }

}