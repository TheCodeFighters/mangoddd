package com.inditex.priceextractor.infrastructure.persistence.jdbctemplate;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.inditex.priceextractor.domain.BrandId;
import com.inditex.priceextractor.domain.PositiveMonetaryAmount;
import com.inditex.priceextractor.domain.PriceAgg;
import com.inditex.priceextractor.domain.PriceId;
import com.inditex.priceextractor.domain.PriceRepository;
import com.inditex.priceextractor.domain.Priority;
import com.inditex.priceextractor.domain.ProductId;

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

  public Optional<PriceAgg> findRate(
      @NonNull ProductId productId,
      @NonNull BrandId brandId,
      @NonNull Date date
  ) {
    String sql = "SELECT * FROM prices WHERE product_id = :productId AND brand_id = :brandId " +
        "AND :applicationDate BETWEEN start_date AND end_date " +
        "ORDER BY priority DESC LIMIT 1";

    Map<String, Object> params = new HashMap<>();
    params.put("productId", productId.id().toString());
    params.put("brandId", brandId.id().toString());
    params.put("applicationDate", sdf.format(date.getTime()));

    return jdbcTemplate.query(sql, params, (resultSet, rowNum) -> {
      PriceId resultId = new PriceId(UUID.fromString(resultSet.getString("price_list")));
      BrandId resultBrandId = new BrandId(UUID.fromString(resultSet.getString("brand_id")));
      Date resultStartDate = new Date(resultSet.getTimestamp("start_date").getTime());
      Date resultEndDate = new Date(resultSet.getTimestamp("end_date").getTime());
      ProductId resultProductId = new ProductId(UUID.fromString(resultSet.getString("product_id")));
      Integer resultPriority = resultSet.getInt("priority");
      Double resultPrice = resultSet.getDouble("price");
      String resultCurr = resultSet.getString("curr");

      return new PriceAgg(
          resultId,
          resultBrandId,
          resultStartDate,
          resultEndDate,
          resultProductId,
          new Priority(resultPriority),
          new PositiveMonetaryAmount(Monetary.getDefaultAmountFactory().setCurrency(resultCurr).setNumber(resultPrice).create())
      );
    }).stream().findFirst();
  }
}