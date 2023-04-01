package models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bookingdates {
    private String checkin;
    private String checkout;
}
