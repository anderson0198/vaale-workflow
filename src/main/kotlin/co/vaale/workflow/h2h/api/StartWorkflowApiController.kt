package co.vaale.workflow.h2h.api

import co.com.groupware.lenpli.model.InlineResponse200
import co.vaale.workflow.h2h.port.`in`.LoanPaymentUseCase
import co.vaale.workflow.h2h.port.out.ILoanPaymentRepository
import co.vaale.workflow.h2h.service.WorkflowCollectService
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
    private lateinit var loanPaymentRepository: ILoanPaymentRepository

    @GetMapping("/startCollectionWorkflow")
    override fun startCollectionWorkflow(): ResponseEntity<InlineResponse200> {
        val accept = request.getHeader("Accept")
        if (accept != null && accept.contains("application/json")) {
            try {
                return ResponseEntity.ok(
                    objectMapper.readValue(
                        """
                        {
                            "systemMessage": "systemMessage",
                            "code": 0,
                            "stackTrace": "stackTrace",
                            "type": "type",
                            "message": "message"
                        }
                        """.trimIndent(),
                        InlineResponse200::class.java
                    )
                )
            } catch (e: IOException) {
                log.error("Couldn't serialize response for content type application/json", e)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
            }
        }
        try {

            println(loanPaymentRepository.getClientWithActivePurchases())
        }catch (e: Exception){

        }
        return ResponseEntity.status(HttpStatus.OK).build()
    }
}
