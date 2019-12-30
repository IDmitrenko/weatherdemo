package ru.dias.weatherdemo.services;

import ru.dias.weatherdemo.model.Weather;

import java.util.List;

public interface WeatherService {
    List<Weather> getWeather();
}
