package tests;

import bookingMethods.CreateBooking;
import bookingMethods.DeleteBooking;
import io.restassured.specification.RequestSpecification;
import models.Booking;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import services.LoginService;
import java.util.List;
import static bookingMethods.BookingGenerator.bookingGenerator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingDeleteStory {
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
        bookingid = CreateBooking.createBooking(session, booking);
        System.out.println("создано бронирование " + bookingid);
    }

    @Test
    public void deleteBooking(){
        DeleteBooking.deleteBooking(session, bookingid);
        System.out.println("удалено бронирование " + bookingid);

    }
    @Test
    public void deleteBooking1(){
        DeleteBooking.deleteBooking(session, bookingid);
        System.out.println("удалено бронирование " + bookingid);

    }


}
