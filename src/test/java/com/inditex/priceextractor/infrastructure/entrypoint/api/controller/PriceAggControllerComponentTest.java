package com.inditex.priceextractor.infrastructure.entrypoint.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import java.util.stream.Stream;

import com.inditex.priceextractor.PriceExtractorApplication;
import com.inditex.priceextractor.domain.BrandId;
import com.inditex.priceextractor.domain.PriceId;
import com.inditex.priceextractor.domain.ProductId;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = PriceExtractorApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PriceAggControllerComponentTest {

  public static final ProductId GIVEN_PRODUCT_ID = new ProductId(UUID.fromString("7f0e9fcb-e004-462b-a42e-1764cc4b3067"));

  public static final ProductId GIVEN_PRODUCT_WITH_DISCOUNT_ID = new ProductId(UUID.fromString("5d2102e9-6311-4c1b-a34d-326e7df8a235"));

  public static final BrandId GIVEN_BRAND_ID = new BrandId(UUID.fromString("5ecffb3d-3472-4420-91cd-80ecd83981d8"));

  @Autowired
  MockMvc mvc;

  @ParameterizedTest
  @MethodSource("provideGivenDataSet")
  public void givenValidRequest_whenGetCurrentPrice_thenStatus200AndValidResponse(String givenApplicationDateAsStr,
      ProductId givenProductId, BrandId givenBrandId, PriceId expectedPriceList, String expectedStartDateAsStr, String expectedEndDateAsStr,
      double expectedPrice) throws Exception {
    String expectedBrandId = GIVEN_BRAND_ID.id().toString();

    mvc.perform(
            get(
                String.format(
                    "/price?application_date=%s&product_id=%s&brand_id=%s",
                    givenApplicationDateAsStr,
                    givenProductId.id().toString(),
                    givenBrandId.id().toString()
                )
            )
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.jsonPath("$.price_list").value(expectedPriceList.toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.product_id").value(givenProductId.toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.brand_id").value(expectedBrandId))
        .andExpect(MockMvcResultMatchers.jsonPath("$.start_date").value(expectedStartDateAsStr))
        .andExpect(MockMvcResultMatchers.jsonPath("$.end_date").value(expectedEndDateAsStr))
        .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(expectedPrice))
        .andExpect(status().isOk());
  }

  private static Stream<Arguments> provideGivenDataSet() {
    return Stream.of(
        Arguments.of("2020-06-14-10.00.00", GIVEN_PRODUCT_ID,
            GIVEN_BRAND_ID, new PriceId(UUID.fromString("d75f8fbb-f0f8-41b5-b109-17cf5498287b")), "2020-06-14-00.00.00",
            "2020-12-31-23.59.59", 35.50),
        Arguments.of("2020-06-14-16.00.00", GIVEN_PRODUCT_ID,
            GIVEN_BRAND_ID, new PriceId(UUID.fromString("2fe9b9de-1808-4b31-8dd2-34a98825003f")), "2020-06-14-15.00.00",
            "2020-06-14-18.30.00", 25.45),
        Arguments.of("2020-06-14-21.00.00", GIVEN_PRODUCT_ID,
            GIVEN_BRAND_ID, new PriceId(UUID.fromString("d75f8fbb-f0f8-41b5-b109-17cf5498287b")), "2020-06-14-00.00.00",
            "2020-12-31-23.59.59", 35.50),
        Arguments.of("2020-06-15-10.00.00", GIVEN_PRODUCT_ID,
            GIVEN_BRAND_ID, new PriceId(UUID.fromString("9c277bf4-6c6d-4932-aebb-47deb49e8263")), "2020-06-15-00.00.00",
            "2020-06-15-11.00.00", 30.50),
        Arguments.of("2020-06-16-21.00.00", GIVEN_PRODUCT_ID,
            GIVEN_BRAND_ID, new PriceId(UUID.fromString("4d6b4f1e-3310-4b40-b803-88f9b6d2c668")), "2020-06-15-16.00.00",
            "2020-12-31-23.59.59", 38.95),
        Arguments.of("2023-01-16-21.00.00", GIVEN_PRODUCT_WITH_DISCOUNT_ID,
            GIVEN_BRAND_ID, new PriceId(UUID.fromString("d4845579-5cc7-4505-867f-72a25c2e5acd")), "2023-01-01-16.00.00",
            "2023-03-31-23.59.59", 242.5)
    );
  }

  @Test
  public void givenRequestWithNotPriceAssociated_whenGetCurrentPrice_thenStatus204()
      throws Exception {
    String givenApplicationDateAsStr = "2020-06-14-10.00.00";

    mvc.perform(
            get(
                String.format(
                    "/price?application_date=%s&product_id=%s&brand_id=%s",
                    givenApplicationDateAsStr,
                    GIVEN_PRODUCT_ID.id().toString(),
                    "d4eb380b-c25b-44ae-a1a2-8074eb1dca2a"
                )
            )
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist())
        .andExpect(status().isNoContent());
  }

  @Test
  public void givenRequestWithInvalidDateFormat_whenGetCurrentPrice_thenStatus403()
      throws Exception {

    String givenApplicationDateAsStr = "cmt:2020-06-14-10.00.00";

    mvc.perform(
            get(
                String.format(
                    "/price?application_date=%s&product_id=%s&brand_id=%s",
                    givenApplicationDateAsStr,
                    GIVEN_PRODUCT_ID.id().toString(),
                    GIVEN_BRAND_ID.id().toString()
                )
            )
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void givenNotValidQueryParams_whenGetCurrentPrice_thenStatus400()
      throws Exception {

    mvc.perform(get("/price")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }
}
