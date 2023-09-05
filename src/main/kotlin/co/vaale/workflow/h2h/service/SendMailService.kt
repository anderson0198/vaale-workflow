package co.vaale.workflow.h2h.service

import co.com.groupware.common.utils.SendEmail
import co.vaale.workflow.Application
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service
import java.io.BufferedReader

@Service
class SendMailService {
    fun sendMail(to: String, reply: String) {
        try {
            val emailUsername = "hey@vaale.co"
            val emailPassword = "mepzvfejrymcewqr"
            val emailHost = "smtp.gmail.com"
            val emailPort = "587"

            val htmlInput = Thread.currentThread().contextClassLoader.getResourceAsStream("segmentFile.html")
            val reader = BufferedReader(htmlInput.reader())
            val htmlTemplate =  reader.readText()
            val message: String = htmlTemplate
                /*.replace("@@username", username)
                .replace("@@password", password)*/

            SendEmail.send(
                to,
                "Vaale <> Reporte ",
                message,
                null,
                null,
                emailUsername,
                emailPassword,
                emailHost,
                emailPort
            )
        } catch (e: Exception) {
            println("--ERROR sendMail ${e.message}")
        }
    }
}
fun main(args: Array<String>) {
    val context = runApplication<Application>(*args)

    val sendMailService = context.getBean(SendMailService::class.java)
    sendMailService.sendMail("anderson@vaale.co","nn")

    // Cerrar el contexto de Spring
    context.close()
}