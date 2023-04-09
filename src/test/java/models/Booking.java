package models;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
    private String firstname;
    private String lastname;
    private Integer totalprice;
    private Boolean depositpaid;
    private Bookingdates bookingdates;
    private String additionalneeds;
}
