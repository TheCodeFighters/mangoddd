package com.apium.priceextractor.infrastructure.persistence.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import com.apium.priceextractor.domain.BrandId;
import com.apium.priceextractor.domain.PositiveMonetaryAmount;
import com.apium.priceextractor.domain.PriceAgg;
import com.apium.priceextractor.domain.PriceId;
import com.apium.priceextractor.domain.Priority;
import com.apium.priceextractor.domain.ProductId;
import com.apium.priceextractor.domain.exception.DomainEntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class PriceAggExtractor {

  public PriceAgg extractPriceAgg(ResultSet resultSet) {
    try {
      PriceId resultId = new PriceId(UUID.fromString(resultSet.getString("id")));
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
          PositiveMonetaryAmount.fromDoubleAndCurrency(resultPrice, resultCurr)
      );
    } catch (SQLException e) {
      throw new DomainEntityNotFoundException("result error");
    }
  }

}
