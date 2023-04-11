package ru.spb.sspk.ssdmd.phonebook_test.config

import org.modelmapper.ModelMapper
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component


@Configuration
@ConfigurationProperties(prefix = "telegram")
open class AppConfiguration {

    @Bean
    open fun modelMapper(): ModelMapper? {
        return ModelMapper()
    }

//    @Component
//    data class BotProperties(val name: String, val token: String)
}