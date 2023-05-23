package com.inditex.priceextractor.infrastructure.persistence;

import java.text.SimpleDateFormat;

import com.inditex.priceextractor.domain.PriceRepository;
import com.inditex.priceextractor.infrastructure.persistence.jdbctemplate.JdbcTemplatePriceRepository;
import com.inditex.priceextractor.infrastructure.persistence.jdbctemplate.PriceAggExtractor;
import com.inditex.priceextractor.infrastructure.persistence.springdata.SpringDataPriceRepository;
import com.inditex.priceextractor.infrastructure.persistence.springdata.crudrepository.SpringDataPriceEntityRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@EnableJpaRepositories(basePackages = "com.inditex.priceextractor.infrastructure.persistence.springdata.crudrepository")
public class DatabaseConfig {

  @Bean()
  @Primary
  public PriceRepository springDataRepository(SpringDataPriceEntityRepository springDataPriceEntityRepository) {
    return new SpringDataPriceRepository(springDataPriceEntityRepository);
  }

  @Bean()
  public PriceRepository jdbcTemplateRepository(NamedParameterJdbcTemplate jdbcTemplate,
      @Qualifier("SimpleDateFormatForDatabase") SimpleDateFormat sdf, PriceAggExtractor priceAggExtractor) {
    return new JdbcTemplatePriceRepository(jdbcTemplate, sdf, priceAggExtractor);
  }

}
