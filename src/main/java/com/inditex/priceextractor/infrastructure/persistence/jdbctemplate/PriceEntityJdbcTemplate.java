package com.inditex.priceextractor.infrastructure.persistence.jdbctemplate;

import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.inditex.priceextractor.domain.Price;
import com.inditex.priceextractor.domain.PriceRepository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component("JDCTemplate")
public class PriceEntityJdbcTemplate implements PriceRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public PriceEntityJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Optional<Price> findRate(
      long productId,
      long brandId,
      @NonNull Date date
  ) {
    String sql = "SELECT * FROM PRICES WHERE product_id = :productId AND brand_id = :brandId " +
        "AND start_date <= :date AND end_date >= :date " +
        "ORDER BY priority DESC LIMIT 1";

    Map<String, Object> params = new HashMap<>();
    params.put("productId", productId);
    params.put("brandId", brandId);
    params.put("date", date);

    return jdbcTemplate.query(sql, params, (resultSet, rowNum) -> {
      long resultId = resultSet.getLong("id");
      long resultBrandId = resultSet.getLong("id");
      Date resultStartDate = resultSet.getDate("id");
      Date resultEndDate = resultSet.getDate("id");
      long resultProductId = resultSet.getLong("id");
      int resultPriority = resultSet.getInt("id");
      long resultPrice = resultSet.getLong("id");
      String resultCurr = resultSet.getString("id");

      return new Price(
          resultId,
          resultBrandId,
          resultStartDate,
          resultEndDate,
          resultProductId,
          resultPriority,
          resultPrice,
          Currency.getInstance(resultCurr)
      );
    }).stream().findFirst();
  }
}