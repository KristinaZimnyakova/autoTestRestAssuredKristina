package tests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import models.Booking;
import models.ResponseBookingDto;
import org.junit.jupiter.api.*;
import services.LoginService;
import services.ManageBooking;
import steps.BookingSteps;

import static dataMethods.BookingGenerator.bookingGenerator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("—ценарий бронировани€")
public class BookingStory {

    RequestSpecification session;
    Booking booking;
    ResponseBookingDto responseBookingDto;

    @BeforeAll
    public void setUpSession(){
        session = LoginService.get();
    }

    @BeforeEach
    @DisplayName("—оздание бронировани€")
    public void createBooking(){
        booking = bookingGenerator(1).get(0);
        responseBookingDto = BookingSteps.createBooking(booking);
    }
    @AfterEach
    @DisplayName("”даление бронировани€")
    public void deleteBooking(){
        ManageBooking.deleteBooking(responseBookingDto);
    }

    @AfterAll
    @DisplayName("ѕросмотр созданного и удаленного бронировани€")
    //чтобы попробовать использование анотации - get запрос дл€ последнего созданного бронировани€
    public void getBooking(){
        RestAssured.baseURI = LoginService.URL;
        String response = session.when().pathParam("id", responseBookingDto.getBookingid()).log().ifValidationFails()
                .get("/booking/{id}").then().statusCode(404).extract().body().asString();
        Assertions.assertEquals("Not Found", response);
    }

    @Test
    @DisplayName("¬алидаци€ полей при создании бронировани€")
    public void create(){
        Assertions.assertEquals(booking.getFirstname(), responseBookingDto.getBooking().getFirstname());
        Assertions.assertEquals(booking.getLastname(), responseBookingDto.getBooking().getLastname());
        Assertions.assertEquals(booking.getTotalprice(), responseBookingDto.getBooking().getTotalprice());
        Assertions.assertEquals(booking.getDepositpaid(), responseBookingDto.getBooking().getDepositpaid());
        Assertions.assertEquals(booking.getBookingdates().getCheckin(), responseBookingDto.getBooking().getBookingdates().getCheckin());
        Assertions.assertEquals(booking.getBookingdates().getCheckout(), responseBookingDto.getBooking().getBookingdates().getCheckout());
    }
    @Test
    @DisplayName("ќбновление бронировани€ и валидаци€ полей")
    public void update(){
        Booking newBooking = bookingGenerator(1).get(0);
        Booking actual = ManageBooking.updateBooking(newBooking, responseBookingDto.getBookingid());
        Assertions.assertEquals(newBooking.getFirstname(), actual.getFirstname());
        Assertions.assertEquals(newBooking.getLastname(), actual.getLastname());
        Assertions.assertEquals(newBooking.getTotalprice(), actual.getTotalprice());
        Assertions.assertEquals(newBooking.getDepositpaid(), actual.getDepositpaid());
        Assertions.assertEquals(newBooking.getBookingdates().getCheckin(), actual.getBookingdates().getCheckin());
        Assertions.assertEquals(newBooking.getBookingdates().getCheckout(), actual.getBookingdates().getCheckout());
    }

    @Test
    @DisplayName("¬алидаци€ полей при просмотре бронировани€")
    public void get(){
        Booking actual = ManageBooking.getBooking(responseBookingDto.getBookingid());
        Assertions.assertEquals(booking.getFirstname(), actual.getFirstname());
        Assertions.assertEquals(booking.getLastname(), actual.getLastname());
        Assertions.assertEquals(booking.getDepositpaid(), actual.getDepositpaid());
        Assertions.assertEquals(booking.getTotalprice(), actual.getTotalprice());
        Assertions.assertEquals(booking.getBookingdates().getCheckin(), actual.getBookingdates().getCheckin());
        Assertions.assertEquals(booking.getBookingdates().getCheckout(), actual.getBookingdates().getCheckout());
    }
















}
