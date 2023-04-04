package services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import models.Booking;

public class ManageBooking {

    public static ResponseBody createBooking(RequestSpecification session, Booking booking){
        RestAssured.baseURI = LoginService.URL;
        ResponseBody bookingBody = (Response) session.body(booking)
                .when().log().ifValidationFails().post("/booking")
                .then().log().ifValidationFails().statusCode(200).extract().body();
        return bookingBody;
    }

    public static void deleteBooking(RequestSpecification session, Integer bookingid){
            RestAssured.baseURI = LoginService.URL;
            session.when().log().ifValidationFails().delete("/booking/" + bookingid)
                    .then().statusCode(201);
    }

    public static ResponseBody updateBooking(RequestSpecification session, Booking booking, Integer bookingid){
        RestAssured.baseURI = LoginService.URL;
        ResponseBody bookingBody = (Response) session.body(booking)
                .when().log().ifValidationFails().put("/booking/" + bookingid)
                .then().log().ifValidationFails().statusCode(200).extract().body();
        return bookingBody;
    }

    public static ResponseBody getBooking(RequestSpecification session, Integer bookingid){
        RestAssured.baseURI = LoginService.URL;
        ResponseBody bookingBody =  (Response) session
                .when().log().ifValidationFails().get("/booking/" + bookingid)
                .then().log().ifValidationFails().statusCode(200).extract().body();
        return bookingBody;
    }

    public static ResponseBody getAllBookings(RequestSpecification session){
        RestAssured.baseURI = LoginService.URL;
        ResponseBody bookingBody =  (Response) session
                .when().log().ifValidationFails().get("/booking")
                .then().log().ifValidationFails().statusCode(200).extract().body();
        return bookingBody;
    }

}
