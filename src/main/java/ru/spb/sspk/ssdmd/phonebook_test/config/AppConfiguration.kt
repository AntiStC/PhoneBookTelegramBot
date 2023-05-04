package ru.spb.sspk.ssdmd.phonebook_test.config

import org.modelmapper.ModelMapper
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling


@Configuration
@EnableScheduling
@ConditionalOnProperty(name = ["scheduler.enabled"], matchIfMissing = true)
class AppConfiguration {

    @Bean
    fun modelMapper(): ModelMapper {
        return ModelMapper()
    }
}