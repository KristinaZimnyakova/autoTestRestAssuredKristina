package tests;

import bookingMethods.CreateBooking;
import io.restassured.specification.RequestSpecification;
import models.Booking;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import services.LoginService;

import java.util.List;
import java.util.stream.Stream;

import static bookingMethods.BookingGenerator.bookingGenerator;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DDTests {

    RequestSpecification session;
    Integer bookingid;

    @BeforeAll
    public void setUpSession(){
        session = LoginService.get();
    }

    @ParameterizedTest
    @MethodSource("bookingSource")
    public void createBookingParameterizedTest(Booking booking){
        bookingid = CreateBooking.createBooking(session, booking);
        System.out.println("создано бронирование " + bookingid);
    }

    public static Stream<Arguments> bookingSource(){
        List<Booking> bookingList = bookingGenerator(5);
        //bookingList.stream().forEach(i -> {System.out.println("Имя " + i.getFirstname() + " Фамилия: " + i.getLastname());});
        Booking booking1 = bookingList.get(0);
        Booking booking2 = bookingList.get(1);
        Booking booking3 = bookingList.get(2);
        Booking booking4 = bookingList.get(3);
        Booking booking5 = bookingList.get(4);
        return Stream.of(
                Arguments.of(booking1),
                Arguments.of(booking2),
                Arguments.of(booking3),
                Arguments.of(booking4),
                Arguments.of(booking5)
        );
    }

}
