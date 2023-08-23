package co.vaale.bankpayment.h2h.api

import co.com.groupware.common.api.model.ApiRequestResponse
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
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import javax.validation.Valid


@javax.annotation.Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2023-04-21T21:24:26.616067176Z[GMT]")
@Validated
interface AddPaymentRequestApi {

    @Operation(
        summary = "Crear las solicitudes de pago de los comercios",
        description = "Crear las solicitudes de pago con el saldo actual de los comercios",
        security = [SecurityRequirement(
            name = "bankpayment_auth",
            scopes = ["vaale.bankpayment.read:sap", "vaale.bankpayment.wrire:sap", "vaale.bankpayment.wrire:pab"]
        )],
        tags = ["Add Payment Request", "bankpayment"]
    )
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "successful operation",
            content = arrayOf(
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiRequestResponse::class)
                )
            )
        ), ApiResponse(responseCode = "400", description = "Invalid status value"), ApiResponse(
            responseCode = "401",
            description = "Unauthorized"
        ), ApiResponse(responseCode = "405", description = "Invalid input"), ApiResponse(
            responseCode = "500",
            description = "Server error"
        )]
    )
    @RequestMapping(
        value = ["/bankpayment/payment/request"],
        produces = ["application/json"],
        method = [RequestMethod.POST]
    )
    fun addPaymentRequest(
        @Parameter(
            `in` = ParameterIn.QUERY,
            description = "Lista de comercios",
            schema = Schema()
        ) @RequestParam(value = "listCommercePlaceId", required = false) listCommercePlaceId: @Valid String?,
        @Parameter(
            `in` = ParameterIn.QUERY,
            description = "Fecha inicial (yyyy-MM-dd HH:mm:ss)",
            schema = Schema()
        ) @RequestParam(value = "initDate", required = false) initDate: @Valid String?,
        @Parameter(
            `in` = ParameterIn.QUERY,
            description = "Fecha final de corte (yyyy-MM-dd HH:mm:ss)",
            schema = Schema()
        ) @RequestParam(value = "endDate", required = false) endDate: @Valid String?
    ): ResponseEntity<ApiRequestResponse?>?


}