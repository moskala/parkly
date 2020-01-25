package pw.react.backend.parklybackend.model;

import com.fasterxml.jackson.annotation.*;
import pw.react.backend.parklybackend.dto.ParkingDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "Parking")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Parking implements Serializable {

    public static Parking EMPTY = new Parking();

    @JsonIdentityReference(alwaysAsId = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name="streetNumber")
    private int streetNumber;

    @Column(name="workingHoursFrom")
    private int workingHoursFrom;

    @Column(name="workingHoursTo")
    private int workingHoursTo;

    @Column(name="costPerHour")
    private  int costPerHour;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    private ParkingOwner owner;

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

    public ParkingOwner getOwner() {
        return owner;
    }

    public void setOwner(ParkingOwner ownerID) {
        this.owner = ownerID;
    }

    public int getCostPerHour() {
        return costPerHour;
    }

    public void setCostPerHour(int costPerHour) {
        this.costPerHour = costPerHour;
    }

    public static Parking valueFrom(@NotNull ParkingDto parkingDto){
        Parking parking = new Parking();
        parking.setId(parkingDto.getId());
        parking.setCity(parkingDto.getCity());
        parking.setStreet(parkingDto.getStreet());
        parking.setStreetNumber(parkingDto.getNumber());
        parking.setCostPerHour(parkingDto.getPrice());
        parking.setWorkingHoursFrom(parkingDto.getOpens());
        parking.setWorkingHoursTo(parkingDto.getCloses());

        return parking;
    }

    public static boolean isAddressPartValid(String address){
        if(address == null || address.isEmpty() || address.trim().isEmpty()) return false;
        return true;
    }

    public static boolean isNumberValid(int number){
        if(number < 1) return false;
        return true;
    }

    public static boolean areWorkingHoursValid(int hour){
        if(hour < 0 || hour > 24) return false;
        return true;
    }

}