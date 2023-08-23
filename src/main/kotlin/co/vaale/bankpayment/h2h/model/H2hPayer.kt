/**
 * Lenpli
 * Sistema monitoreo y control 
 *
 * OpenAPI spec version: 1.0.0
 * Contact: apps@groupware.com.co
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
package co.vaale.bankpayment.h2h.model


/**
 * Datos del pagador
 * @param name Nombre de la empresa pagadora
 * @param documentNumber Identificación de la empresa pagadora
 * @param documentType Tipo de documento del pagador
 * @param accountNumber Número de cuenta de la empresa pagadora
 * @param accountType Tipo de cuenta de la empresa pagadora
 */
data class H2hPayer (

    /* Nombre de la empresa pagadora */
    val name: kotlin.String? = null,
    /* Identificación de la empresa pagadora */
    val documentNumber: kotlin.String? = null,
    /* Tipo de documento del pagador */
    val documentType: kotlin.Int? = null,
    /* Número de cuenta de la empresa pagadora */
    val accountNumber: kotlin.String? = null,
    /* Tipo de cuenta de la empresa pagadora */
    val accountType: kotlin.String? = null
) {
}