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
 * Transacción H2H
 * @param payer 
 * @param recipientItems 
 * @param description Descripción
 * @param paymentType Tipo de pago
 * @param application Aplicación
 * @param sequence Secuencia
 * @param creationDate Fecha de creación
 * @param applicationdate Fecha de aplicación
 */
data class H2hTransaction (

    val payer: H2hPayer? = null,
    val recipientItems: List<H2hRecipient>? = null,
    /* Descripción */
    val description: kotlin.String? = null,
    /* Tipo de pago */
    val paymentType: kotlin.Int? = null,
    /* Aplicación */
    val application: kotlin.String? = null,
    /* Secuencia */
    val sequence: kotlin.String? = null,
    /* Fecha de creación */
    val creationDate: kotlin.String? = null,
    /* Fecha de aplicación */
    val applicationdate: kotlin.String? = null
) {
}