package ru.dias.weatherdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import ru.dias.weatherdemo.services.WeatherAggregationService;

/*
О формате логов
http://openjdk.java.net/jeps/158

// параметры настройки GC
// устанавливаем размер кучи
-Xms256m
-Xmx256m
// параметры для логирования GC
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
// параметры для дампа при OutOfMemory
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/dump
// указываем что будем использовать сборщик мусора G1
-XX:+UseG1GC
// целевой target будет 10 мс
-XX:MaxGCPauseMillis=10
*/

@SpringBootApplication
public class WeatherdemoApplication {
	private static Logger logger = LoggerFactory.getLogger(WeatherAggregationService.class);

/* можно определить здесь, а можно в config
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
*/

	public static void main(String[] args) {
		logger.info("starting app...");
		SpringApplication.run(WeatherdemoApplication.class, args);
	}

}
