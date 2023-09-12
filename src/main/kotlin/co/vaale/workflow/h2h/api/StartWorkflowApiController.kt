package co.vaale.workflow.h2h.api

import co.com.groupware.common.api.model.ApiRequestResponse
import co.com.groupware.lenpli.model.InlineResponse200
import co.vaale.workflow.h2h.port.`in`.CollectUseCase
import co.vaale.workflow.h2h.port.out.ILoanPaymentRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.IOException
import javax.servlet.http.HttpServletRequest

@javax.annotation.Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2023-08-23T23:01:21.827376416Z[GMT]")
@RestController
class StartWorkflowApiController @Autowired constructor(private val objectMapper: ObjectMapper, private val request: HttpServletRequest) : StartWorkflowApi {

    private val log: Logger = LoggerFactory.getLogger(StartWorkflowApiController::class.java)

    @Autowired
    private lateinit var collectUseCase: CollectUseCase

    @GetMapping("/startCollectionWorkflow")
    override fun startCollectionWorkflow(): ResponseEntity<ApiRequestResponse?>? {
        val apiResponse = ApiRequestResponse()
        apiResponse.code = 200
        apiResponse.type = "REGISTER"
        apiResponse.message = "Reg"
        apiResponse.systemMessage = ""
        apiResponse.stackTrace = ""
        try {
            collectUseCase.invoke()
        } catch (e: Exception) {
            apiResponse.code = 500
            apiResponse.message = e.message
        }

        return ResponseEntity.status(HttpStatus.OK).build()
    }
}
