package tests;

import bookingMethods.CreateBooking;
import bookingMethods.DeleteBooking;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import models.Booking;
import org.junit.jupiter.api.*;
import services.LoginService;
import java.util.List;
import static bookingMethods.BookingGenerator.bookingGenerator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingCreateStory {

    RequestSpecification session;
    Integer bookingid;

    @BeforeAll
    public void setUpSession(){
        session = LoginService.get();
    }

    @AfterEach
    public void deleteBooking(){
        DeleteBooking.deleteBooking(session, bookingid);
        System.out.println("удалено бронирование " + bookingid);
    }

    @AfterAll
    public void getBooking(){
        RestAssured.baseURI = LoginService.URL;
        String response = session.when().get("/booking/" + bookingid)
                .then().statusCode(404).extract().body().asString();
        Assertions.assertEquals("Not Found", response);
    }

    @Test
    public void createBooking(){
        List<Booking> bookingList = bookingGenerator(1);
        Booking booking = bookingList.get(0);
        bookingid = CreateBooking.createBooking(session, booking);
        System.out.println("создано бронирование " + bookingid);
    }

    @Test
    public void createBooking2(){
        List<Booking> bookingList = bookingGenerator(1);
        Booking booking = bookingList.get(0);
        bookingid = CreateBooking.createBooking(session, booking);
        System.out.println("создано бронирование " + bookingid);
    }





}
