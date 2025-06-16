package com.example;

import com.example.db.DatabaseManager;
import com.example.model.APODResponse;
import com.example.service.APODClient;
import com.example.config.AppConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String NASA_API_KEY = AppConfig.NASA_API_KEY; // replace with your real key

    public static void main(String[] args) {
        DatabaseManager db = new DatabaseManager();
        APODClient client = new APODClient(NASA_API_KEY);

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            logger.info("Scheduled task started");
            try {
                APODResponse apod = client.fetchToday();
                db.save(apod);
                logger.info("Saved APOD for {}", apod.date);
            } catch (Exception e) {
                logger.error("Failed to fetch/save APOD", e);
            }
        }, 0, 5, TimeUnit.MINUTES);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down...");
            executor.shutdown();
        }));
    }
}
