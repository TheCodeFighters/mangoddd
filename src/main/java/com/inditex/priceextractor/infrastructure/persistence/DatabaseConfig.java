package com.inditex.priceextractor.infrastructure.persistence;

import java.text.SimpleDateFormat;

import com.inditex.priceextractor.domain.PriceRepository;
import com.inditex.priceextractor.infrastructure.persistence.jdbctemplate.PriceEntityJdbcTemplate;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
//@EnableJpaRepositories(basePackages = "com.inditex.priceextractor.infrastructure.persistence.springdata.crudrepository")
public class DatabaseConfig {

  //  @Bean()
  //  public PriceRepository springDataRepository(PriceEntitySpringDataRepository priceEntitySpringDataRepository) {
  //    return new PriceSpringDataRepository(priceEntitySpringDataRepository);
  //  }

  @Bean()
  public PriceRepository jdbcTemplateRepository(NamedParameterJdbcTemplate jdbcTemplate,
      @Qualifier("SimpleDateFormatForDatabase") SimpleDateFormat sdf) {
    return new PriceEntityJdbcTemplate(jdbcTemplate, sdf);
  }

}
