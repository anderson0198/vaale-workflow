package co.vaale.workflow.h2h.service

import co.com.groupware.common.utils.SendEmail
import co.vaale.workflow.Application
import co.vaale.workflow.h2h.port.out.ICreateCSV
import co.vaale.workflow.h2h.port.out.ISendMail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.File
import javax.activation.DataHandler
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMultipart
import javax.mail.util.ByteArrayDataSource

@Service
class SendMailService :ISendMail {
    @Autowired
    private lateinit var createCSVPort: ICreateCSV
    override fun sendMail(to: String, reply: String, byteArray: ByteArray) {
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
                byteArray,
                "ReporteDeClientesConDeudaActiva.csv",
                emailUsername,
                emailPassword,
                emailHost,
                emailPort
            )
        } catch (e: Exception) {
            println("--ERROR sendMail ${e.message}")
        }
    }

    override fun getTo(activityList: List<Map<String, Any?>>) : String? {
        var result: String? = null
        for (activity in activityList) {
            if (activity != null) {
                val to = activity["segment_file_to"] as String?
                result = to
                if (result != null && result!= ""){
                    break
                }
            }
        }
        return result
    }

    override fun invoke(clientList: List<Map<String, Any?>>, to: String) {
            if (to != null && to !="") {
                val byteArray = createCSVPort.createCsvFile(clientList)
                sendMail(to, "", byteArray)
            }
    }


}
