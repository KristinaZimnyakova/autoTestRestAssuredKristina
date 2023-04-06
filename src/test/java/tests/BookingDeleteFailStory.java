package tests;

import io.restassured.RestAssured;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import models.Booking;
import org.junit.jupiter.api.*;
import services.LoginService;
import services.ManageBooking;

import java.util.List;

import static dataMethods.BookingGenerator.bookingGenerator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingDeleteFailStory {

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
        ManageBooking.deleteBooking(bookingid);
        System.out.println("удалено бронирование " + bookingid);
    }

    @Test
    //повторное удаление ранее удаленного бронирования
    public void deleteNonExistentBooking(){
        RestAssured.baseURI = LoginService.URL;
        String response = session.when().log().ifValidationFails().delete("/booking/" + bookingid)
                .then().statusCode(405).extract().body().asString();
        Assertions.assertEquals("Method Not Allowed", response);
    }



}
