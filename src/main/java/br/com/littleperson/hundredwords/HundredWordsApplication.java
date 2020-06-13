package br.com.littleperson.hundredwords;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
public class HundredWordsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HundredWordsApplication.class, args);
	}

}
