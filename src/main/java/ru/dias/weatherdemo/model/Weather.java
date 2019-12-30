package ru.dias.weatherdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
// геттеры, сеттеры, toString, equals, hashcode
@AllArgsConstructor
// коструктор для полей
public class Weather {

    private String source;
    private String city;
    private String temperature;

}
