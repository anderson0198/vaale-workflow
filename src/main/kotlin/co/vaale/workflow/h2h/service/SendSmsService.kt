package co.vaale.workflow.h2h.service
import co.com.groupware.common.utils.SendEmail
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.sns.AmazonSNSClientBuilder
import com.amazonaws.services.sns.model.MessageAttributeValue
import com.amazonaws.services.sns.model.PublishRequest
import com.amazonaws.services.sns.model.PublishResult

import org.springframework.stereotype.Service

@Service
class SendSmsService {
    fun sendSms(to: String, message: String) {
        try {
            val awsCredentials: AWSCredentials =
                BasicAWSCredentials("AKIAZQN5FB5RHVJ3QONV", "q+1xjfeWEYChp7DeJ2L5nnv/7Lf3rMX6cfa8Pzh5")
            val snsClient =
                AmazonSNSClientBuilder.standard().withCredentials(AWSStaticCredentialsProvider(awsCredentials)).build()

            val smsAttributes: Map<String?, MessageAttributeValue?> = HashMap()

            val result: PublishResult = snsClient.publish(
                PublishRequest()
                    .withMessage(message)
                    .withPhoneNumber("+$to")
                    .withMessageAttributes(smsAttributes)
            )
            println("--sendSms result $result")
        } catch (e: Exception) {
            println("--ERROR sendSms ${e.message}")
        }
    }


}