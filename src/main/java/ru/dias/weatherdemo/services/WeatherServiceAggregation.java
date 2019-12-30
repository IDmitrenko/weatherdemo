package ru.dias.weatherdemo.services;

import ru.dias.weatherdemo.model.Weather;

import java.util.List;

public interface WeatherServiceAggregation {
    List<Weather> getWeather();
}
