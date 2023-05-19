package com.inditex.priceextractor.infrastructure.persistence.jdbctemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.inditex.priceextractor.domain.PriceAggregate;
import com.inditex.priceextractor.domain.PriceRepository;
import com.inditex.priceextractor.domain.Priority;

import javax.money.Monetary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.NonNull;

public class PriceEntityJdbcTemplate implements PriceRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  private final SimpleDateFormat sdf;

  public PriceEntityJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate, SimpleDateFormat sdf) {
    this.jdbcTemplate = jdbcTemplate;
    this.sdf = sdf;
  }

  public Optional<PriceAggregate> findRate(
      long productId,
      long brandId,
      @NonNull Date date
  ) {
    String sql = "SELECT * FROM prices WHERE product_id = :productId AND brand_id = :brandId " +
        "AND :applicationDate BETWEEN start_date AND end_date " +
        "ORDER BY priority DESC LIMIT 1";

    Map<String, Object> params = new HashMap<>();
    params.put("productId", productId);
    params.put("brandId", brandId);
    params.put("applicationDate", sdf.format(date.getTime()));

    return jdbcTemplate.query(sql, params, (resultSet, rowNum) -> {
      long resultId = resultSet.getLong("price_list");
      long resultBrandId = resultSet.getLong("brand_id");
      Date resultStartDate = new Date(resultSet.getTimestamp("start_date").getTime());
      Date resultEndDate = new Date(resultSet.getTimestamp("end_date").getTime());
      long resultProductId = resultSet.getLong("product_id");
      int resultPriority = resultSet.getInt("priority");
      double resultPrice = resultSet.getDouble("price");
      String resultCurr = resultSet.getString("curr");

      return new PriceAggregate(
          resultId,
          resultBrandId,
          resultStartDate,
          resultEndDate,
          resultProductId,
          new Priority(resultPriority),
          Monetary.getDefaultAmountFactory().setCurrency(resultCurr).setNumber(resultPrice).create()
      );
    }).stream().findFirst();
  }
}