package bookingMethods;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import models.Booking;
import services.LoginService;

public class CreateBooking {


    public static Integer createBooking(RequestSpecification session, Booking booking){
        RestAssured.baseURI = LoginService.URL;
        Integer bookingid = session.body(booking)
                .when().post("/booking")
                .then().statusCode(200)
                .extract().body().jsonPath().get("bookingid");
        return bookingid;
    }
}
