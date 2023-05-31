package com.apium.priceextractor.infrastructure.entrypoint.api.controller;

import com.apium.priceextractor.application.priceDiscount.CreateProductDiscountRequestDto;
import com.apium.priceextractor.application.priceDiscount.ProductPriceDiscountService;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductDiscountController {

  private final ProductPriceDiscountService productPriceDiscountService;

  public ProductDiscountController(ProductPriceDiscountService productPriceDiscountService) {
    this.productPriceDiscountService = productPriceDiscountService;
  }

  @PostMapping("/product-price-discount")
  @ResponseBody
  public ResponseEntity<Empty> createPrice() {
    try {
      productPriceDiscountService.createProductPriceDiscount(
          new CreateProductDiscountRequestDto(
              "4d4ba101-2b15-4c48-ae96-c8b60b4179c4",
              "a037cff9-94f8-4839-8d57-a41df3ac4ebd",
              3d
          )
      );
      return ResponseEntity.status(HttpStatus.CREATED).body(null);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(null);
    }
  }
}
