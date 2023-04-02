package bookingMethods;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import services.LoginService;

public class DeleteBooking {

    public static void deleteBooking(RequestSpecification session, Integer bookingid){
        RestAssured.baseURI = LoginService.URL;
        session.when().delete("/booking/" + bookingid)
                .then().statusCode(201);
    }
}
