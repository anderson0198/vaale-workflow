package co.vaale.workflow.h2h.service

import co.vaale.workflow.Application
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
@Service
class SendWhatsappService {

    fun send(to: String, name: String, components: String, phoneNumberId: String) {
        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        val json = "{" +
                "  \"type\": \"template\"," +
                "  \"to\": [" +
                "    \"$to\"" +
                "  ]," +
                "  \"template\": {" +
                "    \"name\": \"$name\"," +
                "    \"language\": {" +
                "      \"code\": \"es\"," +
                "      \"policy\": \"deterministic\"" +
                "    }," +
                "    \"components\": $components" +
                "  }" +
                "}"

        val body = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val request: Request = Request.Builder()
            .url("https://api.vaale.co/diloq-chatbot-ms/whatsapp/$phoneNumberId/message")
            .post(body)
            .build()
        val call = client.newCall(request)
        val response = call.execute()
        println("--response $response")
    }



}
