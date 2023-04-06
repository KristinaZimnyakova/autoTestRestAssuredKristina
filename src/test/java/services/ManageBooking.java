package services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import models.Booking;

public class ManageBooking {

    public static ResponseBody createBooking(Booking booking){
        RestAssured.baseURI = LoginService.URL;
        return (Response) LoginService.get().body(booking)
                .when().log().ifValidationFails().post("/booking")
                .then().log().ifValidationFails().statusCode(200).extract().body();
    }

    public static void deleteBooking(Integer bookingid){
        RestAssured.baseURI = LoginService.URL;
        LoginService.get().when().pathParam("id", bookingid).log().ifValidationFails().delete("/booking/{id}")
                .then().statusCode(201);

    }

    public static ResponseBody updateBooking(Booking booking, Integer bookingid){
        RestAssured.baseURI = LoginService.URL;
        return (Response) LoginService.get().body(booking)
                .when().log().ifValidationFails().pathParam("id", bookingid).put("/booking/{id}")
                .then().log().ifValidationFails().statusCode(200).extract().body();
    }

    public static ResponseBody getBooking(Integer bookingid){
        RestAssured.baseURI = LoginService.URL;
        return (Response) LoginService.get()
                .when().log().ifValidationFails().pathParam("id", bookingid).get("/booking/{id}")
                .then().log().ifValidationFails().statusCode(200).extract().body();
    }

    public static ResponseBody getAllBookings(){
        RestAssured.baseURI = LoginService.URL;
        return (Response) LoginService.get()
                .when().log().ifValidationFails().get("/booking")
                .then().log().ifValidationFails().statusCode(200).extract().body();
    }

}
