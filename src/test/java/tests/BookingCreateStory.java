package tests;

import io.restassured.RestAssured;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import models.Booking;
import org.junit.jupiter.api.*;
import services.LoginService;
import services.ManageBooking;

import static dataMethods.BookingGenerator.bookingGenerator;

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
        ManageBooking.deleteBooking(bookingid);
        System.out.println("удалено бронирование " + bookingid);
    }

    @AfterAll
    //чтобы попробовать использование анотации - get запрос для последнего присвоенного значения bookingid
    public void getBooking(){
        RestAssured.baseURI = LoginService.URL;
        String response = session.when().pathParam("id", bookingid).log().ifValidationFails().get("/booking/{id}")
                .then().statusCode(404).extract().body().asString();
        Assertions.assertEquals("Not Found", response);
    }

    @Test
    public void createBooking(){
        bookingid = ManageBooking.createBooking(bookingGenerator(1).get(0)).jsonPath().get("bookingid");
        System.out.println("создано бронирование " + bookingid);
    }

    @Test
    public void createBookingWithValidation(){
        Booking booking = bookingGenerator(1).get(0);
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

}
