package services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Booking;
import models.ResponseBookingDto;

public class ManageBooking {

    public static ResponseBookingDto createBooking(Booking booking){
        RestAssured.baseURI = LoginService.URL;
        return  LoginService.get().body(booking)
                .when().log().ifValidationFails().post("/booking")
                .then().log().ifValidationFails().statusCode(200).extract().as(ResponseBookingDto.class);
    }

    public static void deleteBooking(ResponseBookingDto responseBookingDto){
        RestAssured.baseURI = LoginService.URL;
        LoginService.get().when().pathParam("id", responseBookingDto.getBookingid()).log().ifValidationFails().delete("/booking/{id}")
                .then().statusCode(201);
    }

    public static Booking updateBooking(Booking booking, Integer bookingid){
        RestAssured.baseURI = LoginService.URL;
        return LoginService.get().body(booking)
                .when().log().ifValidationFails().pathParam("id", bookingid).put("/booking/{id}")
                .then().log().ifValidationFails().statusCode(200).extract().as(Booking.class);
    }

    public static Booking getBooking(Integer bookingid){
        RestAssured.baseURI = LoginService.URL;
        return LoginService.get()
                .when().log().ifValidationFails().pathParam("id", bookingid).get("/booking/{id}")
                .then().log().ifValidationFails().statusCode(200).extract().as(Booking.class);
    }

    public static Response getAllBookings(){
        RestAssured.baseURI = LoginService.URL;
        return LoginService.get()
                .when().log().ifValidationFails().get("/booking")
                .then().log().ifValidationFails().statusCode(200).extract().response();

    }

}
