package models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
    private String firstname;
    private String lastname;
    private Integer totalprice;
    Boolean depositpaid;
    private Bookingdates bookingdates;
    private String additionalneeds;
}
