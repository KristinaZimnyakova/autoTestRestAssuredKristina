import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;

public class LoginService {
    static final String URL = "https://restful-booker.herokuapp.com/auth";

    static RequestSpecification session = null;

    public static RequestSpecification get(){
        if(session == null) {
            User user = User.builder().username("admin").password("password123").build();
            String token = RestAssured.given().contentType(ContentType.JSON).body(user)
                    .when().log().all().post(URL)
                    .then().log().all().statusCode(200).extract().body().jsonPath().get("token");
            return RestAssured.given().header("token", token);
        }
        return session;
    }
}
