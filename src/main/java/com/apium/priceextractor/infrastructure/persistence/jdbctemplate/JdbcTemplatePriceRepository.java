package com.apium.priceextractor.infrastructure.persistence.jdbctemplate;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.apium.priceextractor.domain.BrandId;
import com.apium.priceextractor.domain.Date;
import com.apium.priceextractor.domain.PriceAgg;
import com.apium.priceextractor.domain.PriceRepository;
import com.apium.priceextractor.domain.ProductId;
import com.apium.priceextractor.domain.exception.DomainEntityNotFoundException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.NonNull;

public class JdbcTemplatePriceRepository implements PriceRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  private final SimpleDateFormat sdf;

  private final PriceAggExtractor priceAggExtractor;

  public JdbcTemplatePriceRepository(NamedParameterJdbcTemplate jdbcTemplate, SimpleDateFormat sdf, PriceAggExtractor priceAggExtractor) {
    this.jdbcTemplate = jdbcTemplate;
    this.sdf = sdf;
    this.priceAggExtractor = priceAggExtractor;
  }

  public PriceAgg findOrFailRate(
      @NonNull ProductId productId,
      @NonNull BrandId brandId,
      @NonNull Date date
  ) {
    String sql =
        "SELECT BIN_TO_UUID(id) as id, BIN_TO_UUID(brand_id) as brand_id, curr, end_date, price, priority, BIN_TO_UUID"
            + "(product_id) as product_id,start_date FROM prices WHERE product_id = UUID_TO_BIN (:productId) AND brand_id = UUID_TO_BIN"
            + "(:brandId) AND :applicationDate BETWEEN start_date AND end_date ORDER BY priority DESC LIMIT 1";

    Map<String, Object> params = new HashMap<>();
    params.put("productId", productId.id().toString());
    params.put("brandId", brandId.id().toString());
    params.put("applicationDate", sdf.format(date.date().getTime()));

    return jdbcTemplate.query(sql, params, (resultSet, rowNum) -> priceAggExtractor.extractPriceAgg(resultSet)
    ).stream().findFirst().orElseThrow(() ->
        new DomainEntityNotFoundException("PriceAgg not found"));
  }
}