package tests;

import io.restassured.RestAssured;
import io.restassured.response.ResponseBody;
import models.Booking;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import services.LoginService;
import services.ManageBooking;

import static dataMethods.BookingGenerator.bookingGenerator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingUpdateStory {

    RequestSpecification session;
    Integer bookingid;

    @BeforeAll
    public void setUpSession(){
        session = LoginService.get();
    }

    @BeforeEach
    public void createBooking(){
        bookingid = ManageBooking.createBooking(bookingGenerator(1).get(0)).jsonPath().get("bookingid");
        System.out.println("создано бронирование " + bookingid);
    }
    @AfterEach
    //можно заменить удалением, если нужно "не засорять" БД
    public void setNullBookingId(){
        bookingid = null;
    }

    @Test
    public void updateBookingWithValidation(){
        RestAssured.baseURI = LoginService.URL;
        Booking booking = bookingGenerator(1).get(0);
        ResponseBody bookingBody = ManageBooking.updateBooking(booking, bookingid);
        Assertions.assertEquals(bookingBody.path("firstname"), booking.getFirstname());
        Assertions.assertEquals(bookingBody.path("lastname"), booking.getLastname());
        Assertions.assertEquals(bookingBody.path("totalprice"), booking.getTotalprice());
        Assertions.assertEquals(bookingBody.path("depositpaid"), booking.getDepositpaid());
        Assertions.assertEquals(bookingBody.path("additionalneeds"), booking.getAdditionalneeds());
        Assertions.assertEquals(bookingBody.path("bookingdates.checkin"), booking.getBookingdates().getCheckin());
        Assertions.assertEquals(bookingBody.path("bookingdates.checkout"), booking.getBookingdates().getCheckout());
        System.out.println("отредактировано бронирование " + bookingid);
    }



}
