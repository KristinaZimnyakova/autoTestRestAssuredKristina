package tests;

import io.restassured.RestAssured;
import io.restassured.response.ResponseBody;
import models.Booking;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import services.LoginService;
import services.ManageBooking;

import java.util.List;
import static dataMethods.BookingGenerator.bookingGenerator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingUpdateStory {

    RequestSpecification session;
    Integer bookingid;

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
        System.out.println("������� ������������ " + bookingid);
    }
    @AfterEach
    //����� �������� ���������, ���� ����� "�� ��������" ��
    public void setNullBookingId(){
        bookingid = null;
    }

    @Test
    public void updateBookingWithValidation(){
        RestAssured.baseURI = LoginService.URL;
        List<Booking> bookingList = bookingGenerator(1);
        Booking booking = bookingList.get(0);
        ResponseBody bookingBody = ManageBooking.updateBooking(session, booking, bookingid);
        Assertions.assertEquals(bookingBody.path("firstname"), booking.getFirstname());
        Assertions.assertEquals(bookingBody.path("lastname"), booking.getLastname());
        Assertions.assertEquals(bookingBody.path("totalprice"), booking.getTotalprice());
        Assertions.assertEquals(bookingBody.path("depositpaid"), booking.getDepositpaid());
        Assertions.assertEquals(bookingBody.path("additionalneeds"), booking.getAdditionalneeds());
        Assertions.assertEquals(bookingBody.path("bookingdates.checkin"), booking.getBookingdates().getCheckin());
        Assertions.assertEquals(bookingBody.path("bookingdates.checkout"), booking.getBookingdates().getCheckout());
        System.out.println("��������������� ������������ " + bookingid);
    }



}
