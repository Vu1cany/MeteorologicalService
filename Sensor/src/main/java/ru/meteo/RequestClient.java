package ru.meteo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RequestClient {
    public static void main( String[] args ) {
        Random random = new Random();

        String sensorName = "Sensor №1";
        registerSensor(sensorName);

        double maxTemperature = 100;

        for (int j = 0; j < 1000; j++) {
            addNewMeasurement(sensorName,
                    random.nextBoolean(),
                    random.nextDouble() * maxTemperature);
        }
        
    }

    //Регистрирует новый сенсор
    private static void registerSensor(String sensorName){
        final String registrationURL = "http://localhost:8080/sensors/registration";

        Map<String, Object> registrationRequestBody = new HashMap<>();
        registrationRequestBody.put("name", sensorName);

        makePostRequestWithJSONDate(registrationRequestBody, registrationURL);
    }

    private static void addNewMeasurement(String sensorName, boolean raining, double value){
        final String addNewMeasurementURL = "http://localhost:8080/measurements/add";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("value", value);
        requestBody.put("raining", raining);
        requestBody.put("sensor", Map.of("name", sensorName));

        makePostRequestWithJSONDate(requestBody, addNewMeasurementURL);
    }

    private static void makePostRequestWithJSONDate(Map<String, Object> JSONBody, String url){
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(JSONBody, headers);

        try {
            restTemplate.postForObject(url, request, String.class);
            System.out.println("Измерение успешсно отправлено на сервер");

        } catch (HttpClientErrorException e){
            System.out.println("Возникла ошибка:");
            System.out.println(e.getMessage());
        }
    }
}
