package pw.react.backend.parklybackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ReservationCreateRequest {

    private long parkingId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateFrom;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateTo;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userToken;

    public long getParkingId() {
        return parkingId;
    }

    public void setParkingId(long parkingId) {
        this.parkingId = parkingId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
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

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
