package dataMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomDate {

    public static long randomBetween(long minValue, long maxValue) {
        return minValue + Math.round(Math.random() * (maxValue - minValue));
    }

    public static boolean getRandomBoolean(){
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
