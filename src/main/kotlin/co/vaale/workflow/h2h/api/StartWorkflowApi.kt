package co.vaale.workflow.h2h.api



import co.com.groupware.common.api.model.ApiRequestResponse
import co.com.groupware.lenpli.model.InlineResponse200
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.bind.annotation.CookieValue

import javax.validation.Valid
import javax.validation.constraints.*
import java.util.List
import java.util.Map

@javax.annotation.Generated(value = ["io.swagger.codegen.v3.generators.java.SpringCodegen"], date = "2023-08-23T23:01:21.827376416Z[GMT]")
@Validated
interface StartWorkflowApi {

    @Operation(summary = "", description = "", tags = [])
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful operation", content = arrayOf(
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = InlineResponse200::class)
                )
            )),
            ApiResponse(responseCode = "400", description = "Invalid status value"),
            ApiResponse(responseCode = "401", description = "Unauthorized"),
            ApiResponse(responseCode = "405", description = "Invalid input"),
            ApiResponse(responseCode = "500", description = "Server error")
        ]
    )
    fun startCollectionWorkflow(): ResponseEntity<ApiRequestResponse?>?

}
