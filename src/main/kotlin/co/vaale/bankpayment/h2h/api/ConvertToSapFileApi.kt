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
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotNull

@javax.annotation.Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2023-04-14T21:39:38.999508873Z[GMT]")
@Validated
interface ConvertToSapFileApi {

    @Operation(
        summary = "Genera archivo SAP",
        description = "Genera archivo de pagos SAP para H2H",
        security = [SecurityRequirement(
            name = "bankpayment_auth",
            scopes = ["vaale.bankpayment.read:sap", "vaale.bankpayment.wrire:sap"]
        )],
        tags = ["Convert To Sap File", "bankpayment"]
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
        value = ["/bankpayment/converter/sap"],
        produces = ["text/plain"],
        consumes = ["application/json"],
        method = [RequestMethod.POST]
    )
    fun generateSapFile(
        @Parameter(
            `in` = ParameterIn.DEFAULT,
            description = "H2hTransaction",
            required = true,
            schema = Schema()
        ) @RequestBody body: @Valid H2hTransaction?
    ): ResponseEntity<String?>?
}