package ru.dias.weatherdemo.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.dias.weatherdemo.model.Weather;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class YandexWeatherService implements WeatherService {
    private static Logger logger = LoggerFactory.getLogger(YandexWeatherService.class);

    private static final List<Weather> EMPTY_LIST = new ArrayList<>();

    @Value("${app.city-name}")
    // взять из properties
    private String cityName;

/*
    @SneakyThrows
    // заглушка checked исключений
    @Override
    public List<Weather> getWeather() {
        Document doc = Jsoup.connect(String.format("https://yandex.ru/pogoda/%s", cityName)).get();
        Element tempValue = doc.selectFirst(".temp__value");
        return List.of(new Weather("YandexWeather", cityName, tempValue.text()));
    }
*/

    @Override
    public List<Weather> getWeather() {
        try {
            logger.info("Yandex performing request...");
            Thread.sleep(1000);
            Document doc = Jsoup.connect(String.format("https://yandex.ru/pogoda/%s", cityName)).get();
            Element tempValue = doc.selectFirst(".temp__value");
            logger.info("Yandex request done.");
            return List.of(new Weather("YandexWeather", cityName, tempValue.text()));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return EMPTY_LIST;
        }
    }
}
