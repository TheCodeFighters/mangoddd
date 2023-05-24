package com.apium.priceextractor.infrastructure.format.date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.text.SimpleDateFormat;

@Configuration
public class SimpleDateFormatConfig {
    @Bean
    @Primary
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
    }

    @Bean("SimpleDateFormatForDatabase")
    public SimpleDateFormat simpleDateFormatForDatabase(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
    }
}
