package models;

import dataMethods.RandomDate;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bookingdates {
    private String checkin;
    private String checkout;

    public static Bookingdates getRandom(){
        long startDate = RandomDate.randomBetween(1, 5);
        long endDate = RandomDate.randomBetween(6, 150);
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return Bookingdates.builder()
                .checkin(LocalDate.now().plusDays(startDate).format(sdf))
                .checkout(LocalDate.now().plusDays(startDate + endDate).format(sdf))
                .build();
    }

}
