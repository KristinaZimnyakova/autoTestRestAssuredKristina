package tests;

import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import models.Booking;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
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
    Integer bookingid;

    @BeforeAll
    public void setUpSession(){
        session = LoginService.get();
    }

    @AfterEach
    public void deleteBooking(){
        ManageBooking.deleteBooking(bookingid);
        System.out.println("удалено бронирование " + bookingid);
    }

    @ParameterizedTest
    @MethodSource("bookingSource")
    public void createBookingParameterizedTest(Booking booking){
        ResponseBody bookingBody = ManageBooking.createBooking(booking);
        bookingid = bookingBody.jsonPath().get("bookingid");
        System.out.println("создано бронирование " + bookingid);
        Assertions.assertEquals(bookingBody.path("booking.firstname"), booking.getFirstname());
        Assertions.assertEquals(bookingBody.path("booking.lastname"), booking.getLastname());
        Assertions.assertEquals(bookingBody.path("booking.totalprice"), booking.getTotalprice());
        Assertions.assertEquals(bookingBody.path("booking.depositpaid"), booking.getDepositpaid());
        Assertions.assertEquals(bookingBody.path("booking.additionalneeds"), booking.getAdditionalneeds());
        Assertions.assertEquals(bookingBody.path("booking.bookingdates.checkin"), booking.getBookingdates().getCheckin());
        Assertions.assertEquals(bookingBody.path("booking.bookingdates.checkout"), booking.getBookingdates().getCheckout());
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
