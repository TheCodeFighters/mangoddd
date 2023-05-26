package com.apium.priceextractor.infrastructure.persistence.jdbctemplate;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.apium.priceextractor.domain.DiscountPercentage;
import com.apium.priceextractor.domain.ProductDiscountAgg;
import com.apium.priceextractor.domain.ProductDiscountRepository;
import com.apium.priceextractor.domain.ProductId;
import jakarta.validation.constraints.NotNull;
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

  public ProductDiscountAgg findOrDefaultByProductId(@NonNull ProductId productId) {
    String sql =
        "SELECT BIN_TO_UUID(id) as id, BIN_TO_UUID(product_id) as product_id, discount_percentage FROM product_discounts WHERE product_id"
            + " = UUID_TO_BIN(:productId)";

    Map<String, Object> params = new HashMap<>();
    params.put("productId", productId.id().toString());

    return jdbcTemplate.query(sql, params, (resultSet, rowNum) -> productDiscountAggExtractor.extractProductDiscountAgg(resultSet)
    ).stream().findFirst().orElse(
        new ProductDiscountAgg(null, productId, DiscountPercentage.fromDouble(0d))
    );
  }

  @Override
  public void save(@NotNull ProductDiscountAgg productDiscountAgg) {
    //TODO: implement
  }
}