package bookingMethods;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomDate {

    public static String randomeBookingDate(){
        int year = (int) ( Math.random() * 40 );
        LocalDate date = LocalDate.of(1993, 7, 28).plusYears(year);
        return date.toString();
    }

    public static Integer totalPriceGeneratior(){
        Integer totalprice = (int) (Math.random()*(601)) - 200;
        return totalprice;
    }

    public static boolean depositpaid(){
        Random random = new Random();
        return random.nextBoolean();
    }

    public static List<String> additionalneeds() {
        ArrayList<String> additionalneeds = new ArrayList<>();
        additionalneeds.add("All-inclusive");
        additionalneeds.add("Half-board");
        additionalneeds.add("Breakfast");
        return additionalneeds;
    }




}
