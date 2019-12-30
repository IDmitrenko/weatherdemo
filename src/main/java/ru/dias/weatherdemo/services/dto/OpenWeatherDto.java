package ru.dias.weatherdemo.services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/* DTO - Промежуточный обьект для маппинга (обертка над данными в удобном формате)
// DAO - для работы с БД //
Patern DTO возвращает JSON
  {
     ...,
     "main":{
       "temp": "0",
       ....
     }
   }
   с помощью этого обьекта легко распарсить данные
 */
@Data
public class OpenWeatherDto {

    @JsonProperty("main")
    private MainDto main;

    @Data
    public static class MainDto {

        @JsonProperty("temp")
        private String temperature;
    }
}