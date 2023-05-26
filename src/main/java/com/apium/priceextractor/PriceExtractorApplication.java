package com.apium.priceextractor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
public class PriceExtractorApplication {
    public static void main(String[] args) {
        SpringApplication.run(PriceExtractorApplication.class, args);
    }
}

