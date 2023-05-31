package com.apium.priceextractor.infrastructure.eda.messagerelay;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.apium.priceextractor.PriceExtractorApplication;
import com.apium.priceextractor.application.priceDiscount.CreateProductDiscountRequestDto;
import com.apium.priceextractor.application.priceDiscount.ProductPriceDiscountService;
import com.apium.priceextractor.domain.PriceRepository;
import com.apium.priceextractor.domain.ProductDiscountRepository;
import com.apium.priceextractor.infrastructure.eda.broker.MessageBroker;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = PriceExtractorApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MessageRelayServiceIT {

  @Autowired
  private ProductPriceDiscountService productPriceDiscountService;

  @Autowired
  private MessageRelayService messageRelayService;

  @Autowired
  private MessageBroker messageBroker;

  @Autowired
  private OutboxRepository outboxRepository;

  @Test
  @DisplayName("")
  public void test_0() {
    productPriceDiscountService.createProductPriceDiscount(
        new CreateProductDiscountRequestDto(
            "4d4ba101-2b15-4c48-ae96-c8b60b4179c4",
            "a037cff9-94f8-4839-8d57-a41df3ac4ebd",
            3d
        )
    );
    messageRelayService.processMessages();

    assertEquals(0, outboxRepository.findAllMessages().size());
    assertEquals(1, messageBroker.getEventsByTopic("product-price-discount-created").size());
  }

}