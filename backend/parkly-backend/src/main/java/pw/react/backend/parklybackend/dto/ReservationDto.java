package pw.react.backend.parklybackend.dto;

import pw.react.backend.parklybackend.model.Parking;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

public class ReservationDto implements Serializable {

    private long id;

    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private int totalCost;

    private String userFirstName;
    private String userLastName;
    private String userEmail;

    private long parkingId;
    private long spotId;
    private String city;
    private String street;
    private int streetNumber;

    private LocalDateTime createdAt;
}
