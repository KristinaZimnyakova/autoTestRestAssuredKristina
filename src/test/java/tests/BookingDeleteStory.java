package tests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import models.Booking;
import models.ResponseBookingDto;
import org.junit.jupiter.api.*;
import services.LoginService;
import services.ManageBooking;

import java.util.List;
import static dataMethods.BookingGenerator.bookingGenerator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("�������� ������������")
public class BookingDeleteStory {
    RequestSpecification session;
    ResponseBookingDto responseBookingDto;
    Booking booking;

    @BeforeAll
    public void setUpSession(){
        session = LoginService.get();
    }

    @BeforeEach
    @DisplayName("�������� ������������")
    public void createBooking(){
        booking = bookingGenerator(1).get(0);
        responseBookingDto = ManageBooking.createBooking(booking);
    }

    @AfterEach
    @DisplayName("��������� ������������ ������ �����������")
    public void getBooking(){
        RestAssured.baseURI = LoginService.URL;
        String response = session.when().get("/booking/" + responseBookingDto.getBookingid())
                .then().statusCode(404).extract().body().asString();
        Assertions.assertEquals("Not Found", response);
    }

    @AfterAll
    @DisplayName("��������� ������������ ��� � ������ ���� ������������")
    public void getAllBooking(){
        List<Integer> bookings = ManageBooking.getAllBookings().jsonPath().getList("bookingid", Integer.class);
        Assertions.assertTrue(bookings.stream().noneMatch(integer -> integer == responseBookingDto.getBookingid()));
    }

    @Test
    @DisplayName("��������")
    public void deleteBooking(){
        ManageBooking.deleteBooking(responseBookingDto);
    }



}
