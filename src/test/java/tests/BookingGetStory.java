package tests;

import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import models.Booking;
import org.junit.jupiter.api.*;
import services.LoginService;
import services.ManageBooking;

import static dataMethods.BookingGenerator.bookingGenerator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingGetStory {

    RequestSpecification session;
    Integer bookingid;
    Booking booking = bookingGenerator(1).get(0);

    @BeforeAll
    public void setUpSession(){
        session = LoginService.get();
    }

    @BeforeEach
    public void createBooking(){
        bookingid = ManageBooking.createBooking(booking).jsonPath().get("bookingid");
        System.out.println("создано бронирование " + bookingid);
    }

    @AfterEach
    public void deleteBooking(){
        ManageBooking.deleteBooking(bookingid);
        System.out.println("удалено бронирование " + bookingid);
    }

    @Test
    //сравнение атрибутов бронирования из get запроса с атрибутами сгенерированного бронирования
    public void getBookingWithValidation(){
        ResponseBody bookingBody = ManageBooking.getBooking(bookingid);
        Assertions.assertEquals(bookingBody.path("firstname"), booking.getFirstname());
        Assertions.assertEquals(bookingBody.path("lastname"), booking.getLastname());
        Assertions.assertEquals(bookingBody.path("totalprice"), booking.getTotalprice());
        Assertions.assertEquals(bookingBody.path("depositpaid"), booking.getDepositpaid());
        Assertions.assertEquals(bookingBody.path("additionalneeds"), booking.getAdditionalneeds());
        Assertions.assertEquals(bookingBody.path("bookingdates.checkin"), booking.getBookingdates().getCheckin());
        Assertions.assertEquals(bookingBody.path("bookingdates.checkout"), booking.getBookingdates().getCheckout());
    }


}
