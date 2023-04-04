package tests;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import models.Booking;
import org.junit.jupiter.api.*;
import services.LoginService;
import services.ManageBooking;

import java.util.List;

import static dataMethods.BookingGenerator.bookingGenerator;
import static org.hamcrest.Matchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingGetStory {

    RequestSpecification session;
    Integer bookingid;
    List<Booking> bookingList = bookingGenerator(1);
    Booking booking = bookingList.get(0);

    @BeforeAll
    public void setUpSession(){
        session = LoginService.get();
    }

    @BeforeEach
    public void createBooking(){
        ResponseBody bookingBody = ManageBooking.createBooking(session, booking);
        bookingid = bookingBody.jsonPath().get("bookingid");
        System.out.println("создано бронирование " + bookingid);
    }

    @AfterEach
    public void deleteBooking(){
        ManageBooking.deleteBooking(session, bookingid);
        System.out.println("удалено бронирование " + bookingid);
    }

    @Test
    //сравнение атрибутов бронирования из get запроса с атрибутами сгенерированного бронирования
    public void getBookingWithValidation(){
        ResponseBody bookingBody = ManageBooking.getBooking(session, bookingid);
        Assertions.assertEquals(bookingBody.path("firstname"), booking.getFirstname());
        Assertions.assertEquals(bookingBody.path("lastname"), booking.getLastname());
        Assertions.assertEquals(bookingBody.path("totalprice"), booking.getTotalprice());
        Assertions.assertEquals(bookingBody.path("depositpaid"), booking.getDepositpaid());
        Assertions.assertEquals(bookingBody.path("additionalneeds"), booking.getAdditionalneeds());
        Assertions.assertEquals(bookingBody.path("bookingdates.checkin"), booking.getBookingdates().getCheckin());
        Assertions.assertEquals(bookingBody.path("bookingdates.checkout"), booking.getBookingdates().getCheckout());
    }


}
