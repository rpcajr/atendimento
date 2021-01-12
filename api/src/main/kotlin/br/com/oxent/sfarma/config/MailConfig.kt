package br.com.oxent.sfarma.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*

@Configuration
class MailConfig {
    @Bean
    fun javaMailSender(): JavaMailSender {
        val props = Properties()
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = true
        props["mail.smtp.starttls.enable"] = true
        val mailSender = JavaMailSenderImpl()
        mailSender.javaMailProperties = props
        mailSender.host = ""
        mailSender.port = 587
        mailSender.username = ""
        mailSender.password = ""
        return mailSender
    }
}