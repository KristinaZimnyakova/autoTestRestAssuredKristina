package services;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import models.User;
import org.junit.jupiter.api.Assertions;


public class LoginService {
    public static final String URL = "https://restful-booker.herokuapp.com";

    static RequestSpecification session = null;

    public static RequestSpecification get(){
        RestAssured.baseURI = URL;
        if(session == null) {
            User user = User.builder().username("admin").password("password123").build();
            String token = RestAssured.given().contentType(ContentType.JSON).body(user)
                    .when().log().ifValidationFails().post("/auth")
                    .then().log().ifValidationFails().statusCode(200).extract().body().jsonPath().get("token");
            session = RestAssured.given()
                    .cookie("token", token)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json");
            return session;
        }
        return session;
    }
}
