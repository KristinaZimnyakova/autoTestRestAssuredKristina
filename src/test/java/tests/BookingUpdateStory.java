package tests;

import bookingMethods.CreateBooking;
import io.restassured.RestAssured;
import models.Booking;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import services.LoginService;
import java.util.List;
import static bookingMethods.BookingGenerator.bookingGenerator;
import static org.hamcrest.Matchers.equalTo;

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
        List<Booking> bookingList = bookingGenerator(1);
        Booking booking = bookingList.get(0);
        bookingid = CreateBooking.createBooking(session, booking);
        System.out.println("создано бронирование " + bookingid);
    }
    @AfterEach
    public void setNullBookingId(){
        bookingid = null;
    }
    @Test
    public void updateBooking(){
        RestAssured.baseURI = LoginService.URL;
        List<Booking> bookingList = bookingGenerator(1);
        Booking booking = bookingList.get(0);
       session.body(booking)
                .when().log().ifValidationFails().put("/booking/" + bookingid)
                .then().log().ifValidationFails().statusCode(200)
               .body("firstname", equalTo(booking.getFirstname()))
               .body("lastname", equalTo(booking.getLastname()))
               .body("totalprice", equalTo(booking.getTotalprice()))
               .body("depositpaid", equalTo(booking.getDepositpaid()))
               .body("additionalneeds", equalTo(booking.getAdditionalneeds()));
        System.out.println("отредактировано бронирование " + bookingid);
    }



}
