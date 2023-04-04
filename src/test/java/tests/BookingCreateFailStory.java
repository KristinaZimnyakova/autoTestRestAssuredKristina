package tests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import models.Booking;
import org.junit.jupiter.api.*;
import services.LoginService;

import java.util.List;

import static dataMethods.BookingGenerator.bookingGenerator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingCreateFailStory {

    RequestSpecification session;

    @BeforeAll
    public void setUpSession(){
        session = LoginService.get();
    }

    @Test
    //—оздание бронировани€ с невалидными данными
    public void createFailBooking(){
        List<Booking> bookingList = bookingGenerator(1);
        Booking booking = bookingList.get(0);
        booking.setDepositpaid(null);
        RestAssured.baseURI = LoginService.URL;
        session.body(booking)
                .when().post("/booking")
                .then().statusCode(500);
    }

}
