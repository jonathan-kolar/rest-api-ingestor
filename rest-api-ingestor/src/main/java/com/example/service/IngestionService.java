package com.example.service;

import com.example.client.NasaApiClient;
import com.example.model.NasaImage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;

public class IngestionService {
    public void fetchAndSaveImageData() {
        try {
            String response = NasaApiClient.fetchApodData();
            ObjectMapper mapper = new ObjectMapper();
            NasaImage image = mapper.readValue(response, NasaImage.class);

            String log = String.format("%s - Title: %s, URL: %s%n",
                    LocalDateTime.now(), image.getTitle(), image.getUrl());

            Files.writeString(
                    Path.of("apod-log.txt"),
                    log,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND
            );

            System.out.println("Logged: " + image.getTitle());

        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to fetch NASA data: " + e.getMessage());
        }
    }
}
