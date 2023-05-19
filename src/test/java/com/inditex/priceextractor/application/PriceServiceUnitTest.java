package com.inditex.priceextractor.application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import com.inditex.priceextractor.domain.PriceAggregate;
import com.inditex.priceextractor.domain.PriceRepository;
import com.inditex.priceextractor.domain.Priority;
import com.inditex.priceextractor.infrastructure.format.date.SimpleDateFormatConfig;

import javax.money.Monetary;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PriceServiceUnitTest {

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
    GetCurrentPriceRequestDto givenRequest = new GetCurrentPriceRequestDto(
        "2020-06-14-10.00.00",
        35455,
        1
    );

    PriceAggregate expectedPriceAggregate = creteExpectedPrice(givenRequest);

    Date givenApplicationDate = simpleDateFormat.parse(givenRequest.applicationDate());

    when(
        priceRepositoryMock.findRate(
            givenRequest.productId(),
            givenRequest.brandId(),
            givenApplicationDate
        )
    ).thenReturn(Optional.of(expectedPriceAggregate));

    PriceDto priceResponseDto = priceService.getCurrentPrice(givenRequest);

    verify(
        priceRepositoryMock,
        times(1)
    ).findRate(
        givenRequest.productId(),
        givenRequest.brandId(),
        givenApplicationDate
    );

    Assertions.assertEquals(PriceDto.fromPrice(simpleDateFormat, expectedPriceAggregate), priceResponseDto);

  }

  private PriceAggregate creteExpectedPrice(GetCurrentPriceRequestDto givenRequest) throws ParseException {
    return new PriceAggregate(
        1L,
        givenRequest.brandId(),
        simpleDateFormat.parse("2020-06-14-00.00.00"),
        simpleDateFormat.parse("2020-12-31-23.59.59"),
        35455L,
        new Priority(0),
        Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(34.50).create()
    );
  }

  @Test()
  public void givenValidRequestWithNoPriceAssociated_thenRuntimeExceptionIsThrown() throws ParseException {
    GetCurrentPriceRequestDto givenRequest = new GetCurrentPriceRequestDto(
        "2028-06-14-10.00.00",
        35455,
        1
    );

    Date givenApplicationDate = simpleDateFormat.parse(givenRequest.applicationDate());

    when(
        priceRepositoryMock.findRate(
            givenRequest.productId(),
            givenRequest.brandId(),
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
        givenRequest.productId(),
        givenRequest.brandId(),
        givenApplicationDate
    );

  }

}
