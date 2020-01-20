package pw.react.backend.parklybackend.model;

import java.time.LocalDateTime;

public class PossibleParkingReservation {

    private long parkingId;
    private String city;
    private String street;
    private int streetNumber;
    private int totalCost;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private int spotNumber;
}
