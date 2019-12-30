package ru.dias.weatherdemo.services;

import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.dias.weatherdemo.model.Weather;
import ru.dias.weatherdemo.services.dto.OpenWeatherDto;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OpenWeatherService implements WeatherService {
    private static Logger logger = LoggerFactory.getLogger(OpenWeatherService.class);

    private final RestTemplate restTemplate;

    @Value("${app.openweather-api-key}")
    private String apiKey;

    @Value("${app.city-name}")
    private String cityName;

    private final Gson gson = new GsonBuilder().registerTypeAdapter(Weather.class, new JsonDeserializer<Weather>() {
        @Override
        public Weather deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
            JsonObject main = jsonElement.getAsJsonObject().getAsJsonObject("main");
            return new Weather("OpenWeatherMap", cityName, main.get("temp").getAsString());
        }
    }).create();

    @Override
    public List<Weather> getWeather() {
        logger.info("Open performing request...");
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&lang=ru&appid=%s", cityName, apiKey);
        String weatherString = restTemplate.getForObject(url, String.class);
        logger.info("Open request done.");
        return List.of(gson.fromJson(weatherString, Weather.class));
    }

/*
    @Override
    public List<Weather> getWeather() {
///        val(lombok) = final String
        val url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&lang=ru&appid=%s", cityName, apiKey);
///      val(lombok) = OpenWeatherDto
        val dto = restTemplate.getForObject(url, OpenWeatherDto.class);
        return Collections.singletonList(toModel(dto));
    }

    private Weather toModel(OpenWeatherDto dto) {
        return new Weather("Openweather", cityName, dto.getMain().getTemperature());
    }
*/
}