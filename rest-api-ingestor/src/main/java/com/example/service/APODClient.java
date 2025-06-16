package com.example.service;
import com.example.model.APODResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APODClient {
    private final String apiKey;

    public APODClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public APODResponse fetchToday() throws Exception {
        String uri = "https://api.nasa.gov/planetary/apod?api_key=" + apiKey;
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI(uri))
            .GET()
            .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), APODResponse.class);
    }
}