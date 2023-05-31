package com.apium.priceextractor.infrastructure.entrypoint.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.apium.priceextractor.PriceExtractorApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
class ProductDiscountControllerComponentTest {

  @Autowired
  MockMvc mvc;

  @Test
  @DisplayName("given a valid request for create ProductPriceDiscount then this aggregate has been created in ths systen and 204 returned")
  public void test_0() throws Exception {

    mvc.perform(
            post(
                "/product-price-discount"
            )
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist())
        .andExpect(status().isCreated());
  }

}