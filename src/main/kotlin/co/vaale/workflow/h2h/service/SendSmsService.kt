package co.vaale.workflow.h2h.service

import co.com.groupware.common.api.model.ApiRequestResponse
import co.vaale.workflow.h2h.port.out.ISendSms
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


@Service
class SendSmsService : ISendSms {
     override fun sendSMS(to: String, text: String) {
        println("--sendSMS")
        val response = ApiRequestResponse()
        val uri = "https://api103.hablame.co/api/sms/v3/send/priority"
        //System.out.println("--URI " + uri);
        val url: URL
        var conn: HttpURLConnection? = null
        try {
            // 1. URL
            url = URL(uri)

            // 2. Open connection
            conn = url.openConnection() as HttpURLConnection

            // 3. Specify POST method
            conn.setRequestMethod("POST")

            // 4. Set the headers
            conn.setRequestProperty("Content-Type", "application/json")
            conn.setRequestProperty("Account", "10025751")
            conn.setRequestProperty(
                "ApiKey",
                "946FofmHxLu3iFM2ReLXqVGFTZzfGP"
            ) //AC96000b7133e58849f89bb2703033b217: fcafbb854e5bc7a3c3867a28d4c9d186
            conn.setRequestProperty("Token", "003d955fffd26eb8daa7593621603848")
            conn.setDoOutput(true)

            // 5. Add JSON data into POST request body

            // 5.1 Use Jackson object mapper to convert Contnet object into JSON
            //ObjectMapper mapper = new ObjectMapper();
            //mapper.setVisibility("", Visibility.ANY);

            // 5.2 Get connection output stream
            //DataOutputStream wr = new DataOutputStream(conn.getOutputStream());

            // 5.3 Copy Content "JSON" into
            //mapper.writeValue(wr, content);

            // 5.4 Send the request
            //wr.flush();

            // 5.5 close
            //wr.close();
            val input = "{" +
                    "  \"toNumber\": \"57" + to + "\"," +
                    "  \"sms\": \"" + text + "\"," +
                    "  \"flash\": \"0\"," +
                    "  \"sc\": \"890202\"," +
                    "  \"request_dlvr_rcpt\": \"0\"," +
                    "  \"sendDate\": \"string\"" +
                    "}"
            val os: OutputStream = conn.getOutputStream()
            os.write(input.toByteArray())
            os.flush()
            os.close()

            // 6. Get the response
            val responseCode: Int = conn.getResponseCode()
            response.code = responseCode
            if (responseCode == 201) {
                val `in` = BufferedReader(InputStreamReader(conn.getInputStream(), "UTF8"))
                var inputLine: String?
                val responseMsg = StringBuffer()
                while (`in`.readLine().also { inputLine = it } != null) {
                    responseMsg.append(inputLine)
                }
                `in`.close()

                // 7. Print result
                println("Envio SMS a $to")
                println("respuesta de SMS $responseCode , ${responseMsg.toString()}")
                response.message = responseMsg.toString()
            } else {
                response.message = "Invalid user credentials"
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (conn != null) {
                conn.disconnect()
            }
        }

        //return response;
    }




}

