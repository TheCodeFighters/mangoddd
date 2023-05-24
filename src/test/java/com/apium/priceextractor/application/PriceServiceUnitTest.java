package com.apium.priceextractor.application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.apium.priceextractor.application.priceservice.GetCurrentPriceRequestDto;
import com.apium.priceextractor.application.priceservice.PriceDto;
import com.apium.priceextractor.application.priceservice.PriceService;
import com.apium.priceextractor.domain.BrandId;
import com.apium.priceextractor.domain.DiscountPercentage;
import com.apium.priceextractor.domain.PositiveMonetaryAmount;
import com.apium.priceextractor.domain.PositiveNumber;
import com.apium.priceextractor.domain.PriceAgg;
import com.apium.priceextractor.domain.PriceId;
import com.apium.priceextractor.domain.PriceRepository;
import com.apium.priceextractor.domain.Priority;
import com.apium.priceextractor.domain.ProductDiscountAgg;
import com.apium.priceextractor.domain.ProductDiscountId;
import com.apium.priceextractor.domain.ProductDiscountRepository;
import com.apium.priceextractor.domain.ProductId;
import com.apium.priceextractor.domain.exception.DateFormatException;
import com.apium.priceextractor.domain.exception.DomainEntityNotFoundException;
import com.apium.priceextractor.infrastructure.format.date.SimpleDateFormatConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PriceServiceUnitTest {

  public static final GetCurrentPriceRequestDto GIVEN_VALID_REQUEST = new GetCurrentPriceRequestDto(
      "2020-06-14-10.00.00",
      "8a6cf9ed-44cb-4962-8f1f-81e2748a4e7c",
      "2594b409-fb76-4d12-bbaf-63391b39218d"
  );

  AutoCloseable openMocks;

  @Mock
  PriceRepository priceRepositoryMock;

  @Mock
  ProductDiscountRepository productDiscountRepositoryMock;

  SimpleDateFormat simpleDateFormat;

  PriceService priceService;

  @BeforeEach
  public void setup() {
    openMocks = MockitoAnnotations.openMocks(this);
    simpleDateFormat = new SimpleDateFormatConfig().simpleDateFormat();
    priceService = new PriceService(priceRepositoryMock, simpleDateFormat, productDiscountRepositoryMock);
  }

  @AfterEach
  void tearDown() throws Exception {
    openMocks.close();
  }

  @Test
  public void givenValidRequest_thenValidPriceResponseDtoReturned() throws ParseException {

    PriceAgg givenPriceAgg = cretePriceAgg();

    Date givenApplicationDate = simpleDateFormat.parse(GIVEN_VALID_REQUEST.applicationDate());
    ProductId givenProductId = ProductId.fromString(GIVEN_VALID_REQUEST.productId());
    BrandId givenBrandId = new BrandId(UUID.fromString(GIVEN_VALID_REQUEST.brandId()));

    when(priceRepositoryMock.findOrFailRate(givenProductId, givenBrandId, givenApplicationDate)).thenReturn(givenPriceAgg);

    when(productDiscountRepositoryMock.findOrFailByProductId(givenProductId)).thenThrow(
        new DomainEntityNotFoundException("ProductDiscount not found"));

    PriceDto priceResponseDto = priceService.getCurrentPrice(GIVEN_VALID_REQUEST);

    verify(priceRepositoryMock, times(1)).findOrFailRate(givenProductId, givenBrandId, givenApplicationDate);

    Assertions.assertEquals(PriceDto.fromPrice(simpleDateFormat, givenPriceAgg), priceResponseDto);

  }

  @Test
  @DisplayName("given a valid request with a product with ProductPriceDiscount then PriceAgg with discount is returned")
  public void test_1() throws ParseException {

    PriceAgg givenPriceAgg = cretePriceAgg();

    Date givenApplicationDate = simpleDateFormat.parse(GIVEN_VALID_REQUEST.applicationDate());
    ProductId givenProductId = ProductId.fromString(GIVEN_VALID_REQUEST.productId());
    BrandId givenBrandId = new BrandId(UUID.fromString(GIVEN_VALID_REQUEST.brandId()));

    when(priceRepositoryMock.findOrFailRate(givenProductId, givenBrandId, givenApplicationDate)).thenReturn(givenPriceAgg);

    ProductDiscountAgg givenProductDiscountAgg = createPriceDiscount();

    when(productDiscountRepositoryMock.findOrFailByProductId(givenProductId)).thenReturn(givenProductDiscountAgg);

    PriceDto priceResponseDto = priceService.getCurrentPrice(GIVEN_VALID_REQUEST);

    verify(priceRepositoryMock, times(1)).findOrFailRate(givenProductId, givenBrandId, givenApplicationDate);

    givenPriceAgg = givenPriceAgg.changePrice(PositiveMonetaryAmount.fromDoubleAndCurrency(33.465, "EUR"));

    Assertions.assertEquals(PriceDto.fromPrice(simpleDateFormat, givenPriceAgg), priceResponseDto);

  }

  @Test
  @DisplayName("given a valid request with a product with ProductPriceDiscount then PriceAgg with discount is returned")
  public void test_2() {

    GetCurrentPriceRequestDto givenInvalidRequest = new GetCurrentPriceRequestDto(
        "202asdfsdf0-06-14-10.00.00",
        "8a6cf9ed-44cb-4962-8f1f-81e2748a4e7c",
        "2594b409-fb76-4d12-bbaf-63391b39218d"
    );

    assertThrows(DateFormatException.class, () -> priceService.getCurrentPrice(givenInvalidRequest));
  }

  private PriceAgg cretePriceAgg() throws ParseException {
    return new PriceAgg(
        PriceId.fromString("d75f8fbb-f0f8-41b5-b109-17cf5498287b"),
        BrandId.fromString(GIVEN_VALID_REQUEST.brandId()),
        simpleDateFormat.parse("2020-06-14-00.00.00"),
        simpleDateFormat.parse("2020-12-31-23.59.59"),
        ProductId.fromString(GIVEN_VALID_REQUEST.productId()),
        new Priority(0),
        PositiveMonetaryAmount.fromDoubleAndCurrency(34.50, "EUR")
    );
  }

  private ProductDiscountAgg createPriceDiscount() {
    return new ProductDiscountAgg(
        ProductDiscountId.fromString("d75f8fbb-f0f8-41b5-b109-17cf5498287b"),
        ProductId.fromString(GIVEN_VALID_REQUEST.productId()),
        new DiscountPercentage(new PositiveNumber(3d))
    );
  }

  @Test()
  public void givenValidRequestWithNoPriceAssociated_thenRuntimeExceptionIsThrown() throws ParseException {

    Date givenApplicationDate = simpleDateFormat.parse(GIVEN_VALID_REQUEST.applicationDate());
    ProductId givenProductId = new ProductId(UUID.fromString(GIVEN_VALID_REQUEST.productId()));
    BrandId givenBrandId = new BrandId(UUID.fromString(GIVEN_VALID_REQUEST.brandId()));

    when(priceRepositoryMock.findOrFailRate(givenProductId, givenBrandId, givenApplicationDate)).thenThrow(
        new DomainEntityNotFoundException("PriceAgg does not exist"));

    assertThrows(DomainEntityNotFoundException.class, () -> priceService.getCurrentPrice(GIVEN_VALID_REQUEST));

    verify(priceRepositoryMock, times(1)).findOrFailRate(givenProductId, givenBrandId, givenApplicationDate);
  }

}
