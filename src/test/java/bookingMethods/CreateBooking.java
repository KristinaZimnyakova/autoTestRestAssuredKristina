package bookingMethods;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import models.Booking;
import services.LoginService;

import static org.hamcrest.Matchers.equalTo;

public class CreateBooking {


    public static Integer createBooking(RequestSpecification session, Booking booking){
        RestAssured.baseURI = LoginService.URL;
        Integer bookingid = session.body(booking)
                .when().post("/booking")
                .then().statusCode(200)
                .body("booking.firstname", equalTo(booking.getFirstname()))
                .body("booking.lastname", equalTo(booking.getLastname()))
                .body("booking.totalprice", equalTo(booking.getTotalprice()))
                .body("booking.depositpaid", equalTo(booking.getDepositpaid()))
                .body("booking.additionalneeds", equalTo(booking.getAdditionalneeds()))
                .body("booking.bookingdates.checkin", equalTo(booking.getBookingdates().getCheckin()))
                .body("booking.bookingdates.checkout", equalTo(booking.getBookingdates().getCheckout()))
                .extract().body().jsonPath().get("bookingid");
        return bookingid;
    }
}
