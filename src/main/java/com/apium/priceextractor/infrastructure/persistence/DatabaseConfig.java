package com.apium.priceextractor.infrastructure.persistence;

import java.text.SimpleDateFormat;

import com.apium.priceextractor.domain.PriceRepository;
import com.apium.priceextractor.infrastructure.persistence.jdbctemplate.JdbcTemplatePriceRepository;
import com.apium.priceextractor.infrastructure.persistence.jdbctemplate.PriceAggExtractor;
import com.apium.priceextractor.infrastructure.persistence.springdata.SpringDataPriceRepository;
import com.apium.priceextractor.infrastructure.persistence.springdata.crudrepository.SpringDataPriceEntityRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@EnableJpaRepositories(basePackages = "com.apium.priceextractor.infrastructure.persistence.springdata.crudrepository")
public class DatabaseConfig {

  @Bean()
  public PriceRepository springDataPriceRepository(SpringDataPriceEntityRepository springDataPriceEntityRepository) {
    return new SpringDataPriceRepository(springDataPriceEntityRepository);
  }

  @Bean()
  @Primary
  public PriceRepository jdbcTemplatePriceRepository(NamedParameterJdbcTemplate jdbcTemplate, SimpleDateFormat sdf,
      PriceAggExtractor priceAggExtractor) {
    return new JdbcTemplatePriceRepository(jdbcTemplate, sdf, priceAggExtractor);
  }

}
