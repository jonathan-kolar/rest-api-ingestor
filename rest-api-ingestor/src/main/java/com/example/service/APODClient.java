package com.example.service;
import com.example.model.APODResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class APODClient {
    private static final Logger logger = LoggerFactory.getLogger(APODClient.class);
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

        logger.debug("HTTP status: {}", response.statusCode());
        logger.debug("Raw response: {}", response.body());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), APODResponse.class);
    }
}