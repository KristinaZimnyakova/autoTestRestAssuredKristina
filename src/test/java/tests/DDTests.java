package tests;

import io.restassured.specification.RequestSpecification;
import models.Booking;
import models.ResponseBookingDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import services.LoginService;
import services.ManageBooking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static dataMethods.BookingGenerator.bookingGenerator;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DDTests {

    RequestSpecification session;
    ResponseBookingDto responseBookingDto;
    Booking bookingCreate;

    @BeforeAll
    public void setUpSession(){
        session = LoginService.get();
    }

    @AfterEach
    @DisplayName("”даление бронировани€")
    public void deleteBooking(){
        ManageBooking.deleteBooking(responseBookingDto);
    }

    @ParameterizedTest
    @DisplayName("ѕараметризированный теест создани€ бронировани€, валидаци€ полей")
    @MethodSource("bookingSource")
    public void createBookingParameterizedTest(Booking booking){
        bookingCreate = bookingGenerator(1).get(0);
        responseBookingDto = ManageBooking.createBooking(bookingCreate);
        Assertions.assertEquals(bookingCreate.getFirstname(), responseBookingDto.getBooking().getFirstname());
        Assertions.assertEquals(bookingCreate.getLastname(), responseBookingDto.getBooking().getLastname());
        Assertions.assertEquals(bookingCreate.getTotalprice(), responseBookingDto.getBooking().getTotalprice());
        Assertions.assertEquals(bookingCreate.getDepositpaid(), responseBookingDto.getBooking().getDepositpaid());
        Assertions.assertEquals(bookingCreate.getBookingdates().getCheckin(), responseBookingDto.getBooking().getBookingdates().getCheckin());
        Assertions.assertEquals(bookingCreate.getBookingdates().getCheckout(), responseBookingDto.getBooking().getBookingdates().getCheckout());
    }

    public static Stream<Arguments> bookingSource(){
        List<Booking> bookingList = bookingGenerator(4);
        List<Arguments> argumentsList = new ArrayList<>();
        for (Booking i:bookingList) {
            argumentsList.add(Arguments.of(i));
        }
        return argumentsList.stream();
    }

}
