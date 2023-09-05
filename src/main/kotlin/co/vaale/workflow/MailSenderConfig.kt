package co.vaale.workflow

import MailSenderProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

//@Configuration
@EnableConfigurationProperties(MailSenderProperties::class)
class MailSenderConfig(
    private val mailSenderProperties: MailSenderProperties
)