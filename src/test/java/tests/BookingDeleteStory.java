package tests;

import io.restassured.RestAssured;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import models.Booking;
import org.junit.jupiter.api.*;
import services.LoginService;
import services.ManageBooking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import static dataMethods.BookingGenerator.bookingGenerator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingDeleteStory {
    RequestSpecification session;
    Integer bookingid;
    List<Integer> bookingIdList = new ArrayList<>();


    @BeforeAll
    public void setUpSession(){
        session = LoginService.get();
    }

    @BeforeEach
    public void createBooking(){
        List<Booking> bookingList = bookingGenerator(1);
        Booking booking = bookingList.get(0);
        ResponseBody bookingBody = ManageBooking.createBooking(session, booking);
        bookingid = bookingBody.jsonPath().get("bookingid");
        System.out.println("создано бронирование " + bookingid);
        bookingIdList.add(bookingid);
        //System.out.println(bookingIdList);
    }

    @AfterEach
    //удаленное бронирование нельзя просмотреть
    public void getBooking(){
        RestAssured.baseURI = LoginService.URL;
        String response = session.when().get("/booking/" + bookingid)
                .then().statusCode(404).extract().body().asString();
        Assertions.assertEquals("Not Found", response);
    }

    @AfterAll
    //удаленных бронирований нет в списке всех бронирований
    public void getAllBooking(){
        ResponseBody bookingBody = ManageBooking.getAllBookings(session);
        List<Integer> bookingListIdFromResponse = bookingBody.path("bookingid");
        //System.out.println(bookingListIdFromResponse);
        for (int i:bookingIdList) {
            boolean isExists = new HashSet<>(bookingListIdFromResponse).contains(i);
            Assertions.assertEquals(false, isExists);
        }
    }

    @Test
    public void deleteBooking(){
        ManageBooking.deleteBooking(session, bookingid);
        System.out.println("удалено бронирование " + bookingid);
    }

    @Test
    public void deleteBooking1(){
        ManageBooking.deleteBooking(session, bookingid);
        System.out.println("удалено бронирование " + bookingid);
    }


}
