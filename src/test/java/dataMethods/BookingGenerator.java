package dataMethods;

import models.Booking;
import models.Bookingdates;
import java.util.ArrayList;
import java.util.List;
import static dataMethods.FileWork.randomName;
import static dataMethods.RandomDate.*;

public class BookingGenerator {

public static List<Booking> bookingGenerator(Integer number){
    String filePathFirsNames = String.format("%s\\FirstNames.txt", FileWork.userDir);
    String filePathLastNames = String.format("%s\\SecondNames.txt", FileWork.userDir);
    List<String> firstname = FileWork.fileReading(filePathFirsNames);
    List<String> lastname = FileWork.fileReading(filePathLastNames);

    List<Booking> bookingList = new ArrayList<>();
    for (int i=0; i<number; i++) {
        Booking booking = Booking.builder()
                .firstname(randomName(firstname))
                .lastname(randomName(lastname))
                .totalprice(Math.toIntExact(RandomDate.randomBetween(1000, 999999)))
                .depositpaid(getRandomBoolean())
                .bookingdates(Bookingdates.getRandom())
                .additionalneeds(randomName(additionalneeds()))
                .build();
        bookingList.add(booking);
    }
    return bookingList;
}


}
