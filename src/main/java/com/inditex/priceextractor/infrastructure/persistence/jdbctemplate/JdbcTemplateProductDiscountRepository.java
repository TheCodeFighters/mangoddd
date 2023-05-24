package com.inditex.priceextractor.infrastructure.persistence.jdbctemplate;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.inditex.priceextractor.domain.ProductDiscountAgg;
import com.inditex.priceextractor.domain.ProductDiscountId;
import com.inditex.priceextractor.domain.ProductDiscountRepository;
import com.inditex.priceextractor.domain.ProductId;
import com.inditex.priceextractor.domain.exception.DomainEntityNotFoundException;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.NonNull;

public class JdbcTemplateProductDiscountRepository implements ProductDiscountRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  private final SimpleDateFormat sdf;

  private final ProductDiscountAggExtractor productDiscountAggExtractor;

  public JdbcTemplateProductDiscountRepository(NamedParameterJdbcTemplate jdbcTemplate, SimpleDateFormat sdf,
      ProductDiscountAggExtractor productDiscountAggExtractor) {
    this.jdbcTemplate = jdbcTemplate;
    this.sdf = sdf;
    this.productDiscountAggExtractor = productDiscountAggExtractor;
  }

  public ProductDiscountAgg findOrFailByProductId(@NonNull ProductId productId) {
    String sql =
        "SELECT BIN_TO_UUID(id) as id, BIN_TO_UUID(product_id) as product_id, product_discount_percentage WHERE :product_id = UUID_TO_BIN"
            + "(:productId)";

    Map<String, Object> params = new HashMap<>();
    params.put("productId", productId.id().toString());

    return jdbcTemplate.query(sql, params, (resultSet, rowNum) -> productDiscountAggExtractor.extractProductDiscountAgg(resultSet)
    ).stream().findFirst().orElseThrow(() ->
        new DomainEntityNotFoundException("PriceAgg not found"));
  }
}