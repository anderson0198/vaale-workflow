package co.vaale.bankpayment.h2h.api

import co.vaale.bankpayment.h2h.model.H2hTransaction
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import javax.validation.Valid


interface ConvertToPabFileApi {

    @Operation(
        summary = "Genera archivo PAB",
        description = "Genera archivo de pagos PAB para H2H",
        security = [SecurityRequirement(
            name = "bankpayment_auth",
            scopes = ["vaale.bankpayment.read:sap", "vaale.bankpayment.wrire:sap", "vaale.bankpayment.wrire:pab"]
        )],
        tags = ["Convert To Pab File", "bankpayment"]
    )
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "successful operation",
            content = arrayOf(Content(mediaType = "text/plain", schema = Schema(implementation = String::class)))
        ), ApiResponse(responseCode = "400", description = "Invalid status value"), ApiResponse(
            responseCode = "401",
            description = "Unauthorized"
        ), ApiResponse(responseCode = "405", description = "Invalid input"), ApiResponse(
            responseCode = "500",
            description = "Server error"
        )]
    )
    @RequestMapping(
        value = ["/bankpayment/converter/pab"],
        produces = ["text/plain"],
        consumes = ["application/json"],
        method = [RequestMethod.POST]
    )
    fun generatePabFile(
        @Parameter(
            `in` = ParameterIn.DEFAULT,
            description = "H2hTransaction",
            required = true,
            schema = Schema()
        ) @RequestBody body: @Valid H2hTransaction?
    ): ResponseEntity<String?>?

}