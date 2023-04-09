package tests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import models.Booking;
import org.junit.jupiter.api.*;
import services.LoginService;

import java.util.List;

import static dataMethods.BookingGenerator.bookingGenerator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Неуспешное создание бронирования")
public class BookingCreateFailStory {

    RequestSpecification session;

    @BeforeAll
    public void setUpSession(){
        session = LoginService.get();
    }

    @Test
    @DisplayName("Создание бронирования с невалидными данными")
    public void createFailBooking(){
        Booking booking = bookingGenerator(1).get(0);
        booking.setDepositpaid(null);
        RestAssured.baseURI = LoginService.URL;
        session.body(booking)
                .when().post("/booking")
                .then().statusCode(500);
    }

}
