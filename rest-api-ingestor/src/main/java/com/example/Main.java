package com.example;

import com.example.service.IngestionService;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        IngestionService service = new IngestionService();

        scheduler.scheduleAtFixedRate(service::fetchAndSaveImageData, 0, 5, TimeUnit.MINUTES);
    }
}