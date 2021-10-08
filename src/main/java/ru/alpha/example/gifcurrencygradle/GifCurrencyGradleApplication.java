package ru.alpha.example.gifcurrencygradle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GifCurrencyGradleApplication {

	public static void main(String[] args) {
		SpringApplication.run(GifCurrencyGradleApplication.class, args);
	}

}