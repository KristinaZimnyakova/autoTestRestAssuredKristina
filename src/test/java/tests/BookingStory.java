package tests;

import models.Booking;
import models.Bookingdates;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import services.LoginService;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingStory {

    RequestSpecification session;

    @BeforeAll
    public void setUpSession(){
        session = LoginService.get();
    }

    @Test
    public void createBooking(){
        Bookingdates bookingdates = Bookingdates.builder().checkin("2018-01-01").checkout("2019-01-01").build();
        Booking booking = Booking.builder().firstname("Jim").lastname("Brown").totalprice(111).depositpaid(true)
                .bookingdates(bookingdates).additionalneeds("Breakfast").build();
        session.body(booking)
                .when().log().ifValidationFails().post("/booking")
                .then().log().all().statusCode(200);
    }


}
