package com.inditex.priceextractor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
public class PriceExtractorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PriceExtractorApplication.class, args);
    }

}

