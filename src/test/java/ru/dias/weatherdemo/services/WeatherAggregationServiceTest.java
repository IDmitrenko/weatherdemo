package ru.dias.weatherdemo.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.dias.weatherdemo.model.Weather;

import java.util.Collections;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("Класс WeatherAggregationService")
@SpringBootTest
class WeatherAggregationServiceTest {
// создаем mocky бинов
    @MockBean(name = "openWeatherService")
    private OpenWeatherService openWeatherService;

    @MockBean(name = "yandexWeatherService")
    private YandexWeatherService yandexWeatherService;
// WeatherAggregationService будет зависеть от этих двух mock бинов
    @Autowired
    private WeatherAggregationService weatherAggregationService;

    @BeforeEach
    void setUp() {
        when(openWeatherService.getWeather())
                .thenReturn(singletonList(new Weather("", "", "")));
        when(yandexWeatherService.getWeather())
                .thenReturn(singletonList(new Weather("", "", "")));
    }

    @DisplayName("должен аггрегировать данные о погоде с разных сервисов")
    @Test
    void shouldAggregate() {
        assertThat(weatherAggregationService.getWeather()).hasSize(2);
    }
}
