package services;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import models.User;


public class LoginService {
    public static final String URL = "https://restful-booker.herokuapp.com";

    private static String token;

    public static RequestSpecification get(){
        RestAssured.baseURI = URL;
        if(token == null){
            User user = User.builder().password("password123").username("admin").build();
            token = RestAssured.given().contentType(ContentType.JSON).body(user)
                    .when().log().ifValidationFails().post("/auth")
                    .then().log().ifValidationFails().statusCode(200).extract().body().jsonPath().get("token");
        }
        return RestAssured.given()
                .cookie("token", token)
                .header("Accept", "application/json")
                .header("Content-Type", " application/json");

    }

}
