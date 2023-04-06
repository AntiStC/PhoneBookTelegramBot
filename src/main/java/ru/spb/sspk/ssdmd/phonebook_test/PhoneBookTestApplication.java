package ru.spb.sspk.ssdmd.phonebook_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.spb.sspk.ssdmd.phonebook_test.bot.TelegramBot;

@SpringBootApplication
public class PhoneBookTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhoneBookTestApplication.class, args);
	}

}
