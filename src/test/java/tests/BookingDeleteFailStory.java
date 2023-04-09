package tests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import models.Booking;
import models.ResponseBookingDto;
import org.junit.jupiter.api.*;
import services.LoginService;
import services.ManageBooking;

import static dataMethods.BookingGenerator.bookingGenerator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Неуспешное удаление")
public class BookingDeleteFailStory {

    RequestSpecification session;
    Booking booking;
    ResponseBookingDto responseBookingDto;

    @BeforeAll
    public void setUpSession(){
        session = LoginService.get();
    }

    @BeforeEach
    public void createBooking(){
        booking = bookingGenerator(1).get(0);
        responseBookingDto = ManageBooking.createBooking(booking);
        ManageBooking.deleteBooking(responseBookingDto);
    }

    @Test
    @DisplayName("Повторное удаление ранее удаленного бронирования")
    public void deleteNonExistentBooking(){
        RestAssured.baseURI = LoginService.URL;
        String response = session.when().log().ifValidationFails().pathParam("id", responseBookingDto.getBookingid())
                        .delete("/booking/{id}").then().statusCode(405).extract().body().asString();
        Assertions.assertEquals("Method Not Allowed", response);
    }



}
