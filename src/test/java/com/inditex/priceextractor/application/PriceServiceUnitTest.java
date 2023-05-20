package com.inditex.priceextractor.application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.inditex.priceextractor.domain.BrandId;
import com.inditex.priceextractor.domain.PositiveMonetaryAmount;
import com.inditex.priceextractor.domain.PriceAgg;
import com.inditex.priceextractor.domain.PriceId;
import com.inditex.priceextractor.domain.PriceRepository;
import com.inditex.priceextractor.domain.Priority;
import com.inditex.priceextractor.domain.ProductId;
import com.inditex.priceextractor.infrastructure.format.date.SimpleDateFormatConfig;

import javax.money.Monetary;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

  SimpleDateFormat simpleDateFormat;

  PriceService priceService;

  @BeforeEach
  public void setup() {
    openMocks = MockitoAnnotations.openMocks(this);
    simpleDateFormat = new SimpleDateFormatConfig().simpleDateFormat();
    priceService = new PriceService(priceRepositoryMock, simpleDateFormat);
  }

  @AfterEach
  void tearDown() throws Exception {
    openMocks.close();
  }

  @Test
  public void givenValidRequest_thenValidPriceResponseDtoReturned() throws ParseException {

    PriceAgg givenPriceAgg = cretePriceAgg(GIVEN_VALID_REQUEST);

    Date givenApplicationDate = simpleDateFormat.parse(GIVEN_VALID_REQUEST.applicationDate());
    ProductId givenProductId = new ProductId(UUID.fromString(GIVEN_VALID_REQUEST.productId()));
    BrandId givenBrandId = new BrandId(UUID.fromString(GIVEN_VALID_REQUEST.brandId()));

    when(
        priceRepositoryMock.findRate(
            givenProductId,
            givenBrandId,
            givenApplicationDate
        )
    ).thenReturn(Optional.of(givenPriceAgg));

    PriceDto priceResponseDto = priceService.getCurrentPrice(GIVEN_VALID_REQUEST);

    verify(
        priceRepositoryMock,
        times(1)
    ).findRate(
        givenProductId,
        givenBrandId,
        givenApplicationDate
    );

    Assertions.assertEquals(PriceDto.fromPrice(simpleDateFormat, givenPriceAgg), priceResponseDto);

  }

  private PriceAgg cretePriceAgg(GetCurrentPriceRequestDto givenRequest) throws ParseException {
    return new PriceAgg(
        new PriceId(UUID.fromString("d75f8fbb-f0f8-41b5-b109-17cf5498287b")),
        new BrandId(UUID.fromString(givenRequest.brandId())),
        simpleDateFormat.parse("2020-06-14-00.00.00"),
        simpleDateFormat.parse("2020-12-31-23.59.59"),
        new ProductId(UUID.fromString(givenRequest.productId())),
        new Priority(0),
        new PositiveMonetaryAmount(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(34.50).create())
    );
  }

  @Test()
  public void givenValidRequestWithNoPriceAssociated_thenRuntimeExceptionIsThrown() throws ParseException {
    GetCurrentPriceRequestDto givenRequest = GIVEN_VALID_REQUEST;

    Date givenApplicationDate = simpleDateFormat.parse(GIVEN_VALID_REQUEST.applicationDate());
    ProductId givenProductId = new ProductId(UUID.fromString(GIVEN_VALID_REQUEST.productId()));
    BrandId givenBrandId = new BrandId(UUID.fromString(GIVEN_VALID_REQUEST.brandId()));

    when(
        priceRepositoryMock.findRate(
            givenProductId,
            givenBrandId,
            givenApplicationDate
        )
    ).thenReturn(Optional.empty());

    assertThrows(
        RuntimeException.class,
        () -> priceService.getCurrentPrice(givenRequest)
    );

    verify(
        priceRepositoryMock,
        times(1)
    ).findRate(
        givenProductId,
        givenBrandId,
        givenApplicationDate
    );

  }

}
