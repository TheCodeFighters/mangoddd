package com.apium.priceextractor.infrastructure.entrypoint.api.controller;

import com.apium.priceextractor.application.priceservice.GetCurrentPriceRequestDto;
import com.apium.priceextractor.domain.PriceDto;
import com.apium.priceextractor.application.priceservice.PriceService;
import com.apium.priceextractor.domain.exception.DateFormatException;
import com.apium.priceextractor.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PriceController {

  private final PriceService priceService;

  public PriceController(PriceService priceService) {
    this.priceService = priceService;
  }

  @GetMapping("/price")
  @ResponseBody
  public ResponseEntity<PriceDto> getPrice(
      @RequestParam("application_date") String applicationDate,
      @RequestParam("product_id") String productId,
      @RequestParam("brand_id") String brandId
  ) {

        try {
            return ResponseEntity.ok()
                .body(
                    this.priceService.getCurrentPrice(
                        new GetCurrentPriceRequestDto(applicationDate, productId, brandId)
                    )
                );
        } catch (DateFormatException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (DomainException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
