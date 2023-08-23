package co.vaale.bankpayment.h2h.api

import co.com.groupware.common.api.model.ApiResponseData
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
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import javax.validation.Valid


@javax.annotation.Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2023-04-15T01:43:53.783827847Z[GMT]")
@Validated
interface GetCommercePaymentTransactionRequestApi {

    @Operation(
        summary = "Busca las solicitudes de pago",
        description = "Busca las solicitudes de pago",
        security = [SecurityRequirement(
            name = "commerce_auth",
            scopes = ["commerce.read:place", "commerce.write:place", "commerce.read:loan", "commerce.write:loan", "commerce.read:purchase", "commerce.write:purchase"]
        )],
        tags = ["Get Commerce Payment Transaction Request", "bankpayment"]
    )
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "successful operation",
            content = arrayOf(Content(mediaType = "application/json", schema = Schema(implementation = ApiResponseData::class)))
        ), ApiResponse(responseCode = "400", description = "Invalid status value"), ApiResponse(
            responseCode = "401",
            description = "Unauthorized"
        ), ApiResponse(responseCode = "405", description = "Invalid input"), ApiResponse(
            responseCode = "500",
            description = "Server error"
        )]
    )
    @RequestMapping(
        value = ["/bankpayment/transaction/sap"],
        produces = ["application/json"],
        method = [RequestMethod.GET]
    )
    fun getCommercePaymentRequest(
        @Parameter(
            `in` = ParameterIn.QUERY,
            description = "Lista de comercios",
            schema = Schema()
        ) @RequestParam(value = "listCommercePlaceId", required = false) listCommercePlaceId: @Valid String?,
        @Parameter(
            `in` = ParameterIn.QUERY,
            description = "Lista de planes",
            schema = Schema()
        ) @RequestParam(value = "listPlanId", required = false) listPlanId: @Valid String?
    ): ResponseEntity<ApiResponseData<H2hTransaction>?>?


}