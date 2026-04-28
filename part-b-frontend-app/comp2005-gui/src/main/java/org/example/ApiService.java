package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Patient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ApiService {

    private static final String LOCAL_URL = "http://localhost:8080/api";
    private static final String EXTERNAL_URL = "https://web.socem.plymouth.ac.uk/COMP2005/api";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // calls GET /api/F1/{patientId} locally, take as input patient id as number
    public List<Integer> getRoomsByPatient(int patientId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(LOCAL_URL + "/F1/" + patientId))
                .GET()
                .build();

        // store response as string
        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        // return response into integer list
        return objectMapper.readValue(
                response.body(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Integer.class)
        );
    }

    // get patient info from external api
    public Patient getPatient(int patientId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(EXTERNAL_URL + "/Patients/" + patientId))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 404) {
            return null;
        }

        return objectMapper.readValue(response.body(), Patient.class);
    }
}