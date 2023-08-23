package co.vaale.bankpayment.h2h.api

import co.vaale.bankpayment.h2h.model.H2hTransaction
import co.vaale.bankpayment.h2h.port.`in`.GetPabH2hPaymentTextUseCase
import co.vaale.bankpayment.h2h.port.`in`.GetSapH2hPaymentTextUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@javax.annotation.Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2023-06-01T21:27:34.684683015Z[GMT]")
@RestController
class ConvertToPabFileApiController : ConvertToPabFileApi {

    private val log: Logger = LoggerFactory.getLogger(ConvertToPabFileApiController::class.java)

    @Autowired
    private lateinit var getPabH2hPaymentTextUseCase: GetPabH2hPaymentTextUseCase

    override fun generatePabFile(body: H2hTransaction?): ResponseEntity<String?>? {

        val text = getPabH2hPaymentTextUseCase.invoke(body)
        return ResponseEntity<String?>(text, HttpStatus.OK)
    }
}