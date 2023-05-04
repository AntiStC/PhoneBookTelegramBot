package ru.spb.sspk.ssdmd.phonebook_test.config

import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.starter.SpringWebhookBot
import org.telegram.telegrambots.starter.TelegramBotInitializer
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import java.util.*


@Configuration
@ConditionalOnProperty(prefix = "telegrambots", name = ["enabled"], havingValue = "true", matchIfMissing = true)
class TelegramBotStarterConfiguration {
    @Bean
    @ConditionalOnMissingBean(TelegramBotsApi::class)
    @Throws(TelegramApiException::class)
    fun telegramBotsApi(): TelegramBotsApi {
        return TelegramBotsApi(DefaultBotSession::class.java)
    }

    @Bean
    @ConditionalOnMissingBean
    fun telegramBotInitializer(
        telegramBotsApi: TelegramBotsApi?,
        longPollingBots: ObjectProvider<List<LongPollingBot?>?>,
        webHookBots: ObjectProvider<List<SpringWebhookBot?>?>
    ): TelegramBotInitializer {
        return TelegramBotInitializer(
            telegramBotsApi,
            longPollingBots.getIfAvailable(Collections::emptyList),
            webHookBots.getIfAvailable(Collections::emptyList)
        )
    }
}