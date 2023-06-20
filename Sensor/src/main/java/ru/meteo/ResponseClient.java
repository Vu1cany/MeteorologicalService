package ru.meteo;

import org.springframework.web.client.RestTemplate;
import ru.meteo.dto.MeasurementDTO;
import ru.meteo.dto.MeasurementsResponse;

import java.util.Collections;
import java.util.List;

public class ResponseClient {
    public static void main(String[] args) {

        List<MeasurementDTO> measurements = getMeasurementsFromServer();
        int i = 0;
        for (MeasurementDTO measurement : measurements) {
            System.out.println(
                    i++ + " - " +
                    measurement.getValue() + " - " +
                    measurement.getRaining() + " - " +
                    measurement.getSensor().getName());
        }

        System.out.println(getRainyDaysCountFromServer());
    }

    private static List<MeasurementDTO> getMeasurementsFromServer(){
        final RestTemplate restTemplate = new RestTemplate();
        final String URL = "http://localhost:8080/measurements";

        MeasurementsResponse response = restTemplate.getForObject(URL, MeasurementsResponse.class);

        if (response == null || response.getMeasurements() == null){
            return Collections.emptyList();
        }

        return response.getMeasurements();
    }

    private static long getRainyDaysCountFromServer() {
        final RestTemplate restTemplate = new RestTemplate();
        final String URL = "http://localhost:8080/measurements/rainyDaysCount";

        return restTemplate.getForObject(URL, Long.class);
    }
}
