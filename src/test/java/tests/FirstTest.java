package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.LoginService;

import static org.hamcrest.Matchers.equalTo;

public class FirstTest {

    @Test
    public void login(){
        RestAssured.baseURI = LoginService.URL;
        User user = User.builder().username("admin").password("password123").build();
        String token = RestAssured.given().contentType(ContentType.JSON).body(user)
                .when().log().ifValidationFails().post("/auth")
                .then().log().ifValidationFails().statusCode(200).extract().body().jsonPath().get("token");
        Assertions.assertNotNull(token);
    }
    @Test
    public void failLogin(){
        RestAssured.baseURI = LoginService.URL;
        User user = User.builder().username("admin").password("123").build();
        RestAssured.given().contentType(ContentType.JSON).body(user)
                .when().log().ifValidationFails().post("/auth")
                .then().log().ifValidationFails().statusCode(200).body("reason", equalTo("Bad credentials"));
    }
}
