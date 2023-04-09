package steps;

import io.qameta.allure.Step;
import models.Booking;
import models.ResponseBookingDto;
import services.ManageBooking;

public class BookingSteps {

    @Step("Создать бронирование")
    public static ResponseBookingDto createBooking(Booking booking){
        return ManageBooking.createBooking(booking);
    }
}
