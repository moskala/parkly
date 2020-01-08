package pw.react.backend.parklybackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "parking") //parking czy parkings?

//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Parking implements Serializable {

    private static final long serialVersionUID = -6783504532088859179L;

    public static Parking EMPTY = new Parking();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name="streetNumber")
    private int streetNumber;
    @Column(name = "numberOfSpots")
    private int numberOfSpots;
    @Column(name="workingHoursFrom")
    private int workingHoursFrom;
    @Column(name="workingHoursTo")
    private int workingHoursTo;
    @Column(name = "ownerID")
    private int ownerID;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public int getNumberOfSpots() {
        return numberOfSpots;
    }

    public void setNumberOfSpots(int numberOfSpots) {
        this.numberOfSpots = numberOfSpots;
    }

    public int getWorkingHoursFrom() {
        return workingHoursFrom;
    }

    public void setWorkingHoursFrom(int workingHoursFrom) {
        this.workingHoursFrom = workingHoursFrom;
    }

    public int getWorkingHoursTo() {
        return workingHoursTo;
    }

    public void setWorkingHoursTo(int workingHoursTo) {
        this.workingHoursTo = workingHoursTo;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }
}