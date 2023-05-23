package com.inditex.priceextractor.infrastructure.persistence.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import com.inditex.priceextractor.domain.BrandId;
import com.inditex.priceextractor.domain.PositiveMonetaryAmount;
import com.inditex.priceextractor.domain.PriceAgg;
import com.inditex.priceextractor.domain.PriceId;
import com.inditex.priceextractor.domain.Priority;
import com.inditex.priceextractor.domain.ProductDiscountId;
import com.inditex.priceextractor.domain.ProductId;
import com.inditex.priceextractor.domain.exception.DomainEntityNotFoundException;

import javax.money.Monetary;
import org.springframework.stereotype.Component;

@Component
public class PriceAggExtractor {

  public PriceAgg extractPriceAgg(ResultSet resultSet) {
    try {
      PriceId resultId = new PriceId(UUID.fromString(resultSet.getString("price_list")));
      BrandId resultBrandId = new BrandId(UUID.fromString(resultSet.getString("brand_id")));
      Date resultStartDate = new Date(resultSet.getTimestamp("start_date").getTime());
      Date resultEndDate = new Date(resultSet.getTimestamp("end_date").getTime());
      ProductId resultProductId = new ProductId(UUID.fromString(resultSet.getString("product_id")));
      Integer resultPriority = resultSet.getInt("priority");
      Double resultPrice = resultSet.getDouble("price");
      String resultCurr = resultSet.getString("curr");
      ProductDiscountId resultProductDiscountId = ProductDiscountId.fromString(resultSet.getString("product_discount_id"));

      return new PriceAgg(
          resultId,
          resultBrandId,
          resultStartDate,
          resultEndDate,
          resultProductId,
          new Priority(resultPriority),
          PositiveMonetaryAmount.fromDoubleAndCurrency(resultPrice, resultCurr),
          resultProductDiscountId
      );
    } catch (SQLException e) {
      throw new DomainEntityNotFoundException("result error");
    }
  }

}
