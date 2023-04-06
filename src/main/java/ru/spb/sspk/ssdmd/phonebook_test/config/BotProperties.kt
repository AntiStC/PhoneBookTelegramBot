package ru.spb.sspk.ssdmd.phonebook_test.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "telegram")
@Component
data class BotProperties(
    val name: String,
    val token: String
)