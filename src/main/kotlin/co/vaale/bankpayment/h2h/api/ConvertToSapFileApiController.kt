package co.vaale.bankpayment.h2h.api

import co.vaale.bankpayment.h2h.model.H2hTransaction
import co.vaale.bankpayment.h2h.port.`in`.GetSapH2hPaymentTextUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@javax.annotation.Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2023-04-14T21:39:38.999508873Z[GMT]")
@RestController
class ConvertToSapFileApiController : ConvertToSapFileApi {

    private val log: Logger = LoggerFactory.getLogger(ConvertToSapFileApiController::class.java)

    @Autowired
    private lateinit var getSapH2hPaymentTextUseCase: GetSapH2hPaymentTextUseCase

    override fun generateSapFile(body: H2hTransaction?): ResponseEntity<String?>? {

        val text = getSapH2hPaymentTextUseCase.invoke(body)
        return ResponseEntity<String?>(text, HttpStatus.OK)

    }
}