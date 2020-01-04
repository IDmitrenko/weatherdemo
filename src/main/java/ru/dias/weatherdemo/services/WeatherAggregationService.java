package ru.dias.weatherdemo.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.dias.weatherdemo.model.Weather;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service("weatherService")
public class WeatherAggregationService implements WeatherServiceAggregation {
    private static Logger logger = LoggerFactory.getLogger(WeatherAggregationService.class);

    private final List<WeatherService> weatherServices;
    private final WeatherCache weatherCache;
    private static final List<Weather> EMPTY_LIST = new ArrayList<>();

/*
    public WeatherAggregationService(List<WeatherService> weatherServices, WeatherCache weatherCache) {
        this.weatherServices = weatherServices;
        this.weatherCache = weatherCache;
    }
*/

/*
    @Override
    public List<Weather> getWeather() {
        List<Weather> weatherList = new ArrayList<>();
        weatherServices.forEach(ws -> weatherList.addAll(ws.getWeather()));
        return weatherList;
    }
*/

    @Override
    public List<Weather> getWeather() {
        // - смотрим есть ненулевое значение в кэше
        // - если нет, то делаем запрос, результат помещаем в кэш
        // возвращаем значение из кэша
        return weatherCache.getValue().orElseGet(() -> {
            var weather = doRequest();
            weatherCache.putValue(weather);
            return weather;
        });
    }

    private List<Weather> doRequest() {
        // создаем по количеству сервисов CompletableFuture, где будем хранить результат запроса
        CompletableFuture[] weatherFutures = new CompletableFuture[weatherServices.size()];

        int idx = 0;
        // запускаем асинхронно сервисы
        for (WeatherService weatherService : weatherServices) {
            weatherFutures[idx++] = CompletableFuture.supplyAsync(weatherService::getWeather);
        }

        try {
            // ждем пока завершится хотя бы одна из этих Future
            CompletableFuture<Object> combinedFuture = CompletableFuture.anyOf(weatherFutures);
            // отдаем результаты первого завершившегося сервиса пользователю
            return (List<Weather>) combinedFuture.get(30, TimeUnit.SECONDS);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return EMPTY_LIST;
        }
    }
}