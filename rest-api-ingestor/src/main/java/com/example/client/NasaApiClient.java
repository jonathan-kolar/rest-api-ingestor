package com.example.client;

import com.example.config.AppConfig;
import java.net.URI;
import java.net.http.*;
import java.io.IOException;

public class NasaApiClient {
    private static final HttpClient client = HttpClient.newHttpClient();

    public static String fetchApodData() throws IOException, InterruptedException {
        String url = AppConfig.APOD_ENDPOINT + "?api_key=" + AppConfig.NASA_API_KEY;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}