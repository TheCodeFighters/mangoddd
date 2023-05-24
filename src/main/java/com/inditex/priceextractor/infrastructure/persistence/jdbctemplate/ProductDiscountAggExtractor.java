package com.inditex.priceextractor.infrastructure.persistence.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.inditex.priceextractor.domain.DiscountPercentage;
import com.inditex.priceextractor.domain.PositiveNumber;
import com.inditex.priceextractor.domain.ProductDiscountAgg;
import com.inditex.priceextractor.domain.ProductDiscountId;
import com.inditex.priceextractor.domain.ProductId;
import com.inditex.priceextractor.domain.exception.DomainEntityNotFoundException;

import org.springframework.stereotype.Component;

@Component
public class ProductDiscountAggExtractor {

  public ProductDiscountAgg extractProductDiscountAgg(ResultSet resultSet) {
    try {
      ProductDiscountId resultId = new ProductDiscountId(UUID.fromString(resultSet.getString("id")));
      ProductId resultProductId = new ProductId(UUID.fromString(resultSet.getString("product_id")));
      Double resultPrice = resultSet.getDouble("product_percentage");

      return new ProductDiscountAgg(
          resultId,
          resultProductId,
          new DiscountPercentage(new PositiveNumber(resultPrice))
      );
    } catch (SQLException e) {
      throw new DomainEntityNotFoundException("result error");
    }
  }

}
