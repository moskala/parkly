package pw.react.backend.parklybackend.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Entity
@Table(name = "parking") //parking czy parkings?

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Parking implements Serializable {

    private static final long serialVersionUID = -6783504532088859179L;

    public static Parking EMPTY = new Parking();

    @JsonIdentityReference(alwaysAsId = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Column(name = "city")
    private String city;
    @NotNull
    @Column(name = "street")
    private String street;
    @NotNull
    @Min(value = 1, message = "Street number has to be greater than 0")
    @Column(name="streetNumber")
    private int streetNumber;
    @NotNull
    @Min(value=1,message = "Number of spots has to be greater than 0")
    @Column(name = "numberOfSpots")
    private int numberOfSpots;
    @NotNull
    @Min(value = 0,message = "Working hours cannot be negative")
    @Max(value = 24, message="Working hours cannot be more than 23")
    @Column(name="workingHoursFrom")
    private int workingHoursFrom;
    @NotNull
    @Min(value = 0,message = "Working hours cannot be negative")
    @Max(value = 24, message="Working hours cannot be more than 23")
    @Column(name="workingHoursTo")
    private int workingHoursTo;

    @ManyToOne
    @JoinColumn(name = "FK_ownerId")
    private ParkingOwner ownerID;

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

    public ParkingOwner getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(ParkingOwner ownerID) {
        this.ownerID = ownerID;
    }

}