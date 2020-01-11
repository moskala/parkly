package pw.react.backend.parklybackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation {

    public static Reservation EMPTY = new Reservation();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name="parkingId")
    private Parking parkingId;
    @Column(name = "dateFrom")
    private LocalDateTime dateFrom;
    @Column(name = "dateTo")
    private LocalDateTime dateTo;
    @Column(name = "userFirstName")
    private String userFirstName;
    @Column(name = "userLastName")
    private String userLastName;
    @Column(name = "userEmail")
    private String userEmail;
    @Column(name = "userToken")
    private String userToken;
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    public Reservation(){}

    public Reservation(ReservationCreateRequest request) {
        dateFrom = request.getDateFrom();
        dateTo = request.getDateTo();
        userFirstName = request.getUserFirstName();
        userLastName = request.getUserLastName();
        userEmail = request.getUserEmail();
        userToken = request.getUserToken();
    }

    public Parking getParkingId() {
        return parkingId;
    }

    public void setParkingId(Parking parkingId) {
        this.parkingId = parkingId;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date LocalDateTime) {
        this.dateTo = dateTo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
