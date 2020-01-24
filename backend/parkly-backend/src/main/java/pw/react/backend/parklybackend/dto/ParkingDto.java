package pw.react.backend.parklybackend.dto;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import pw.react.backend.parklybackend.dao.ParkingRepository;
import pw.react.backend.parklybackend.model.Parking;
import pw.react.backend.parklybackend.model.ParkingOwner;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ParkingDto implements Serializable {

    public static ParkingDto EMPTY = new ParkingDto();

    private long id;
    private String city;
    private String street;
    private int streetNumber;
    private int numberOfSpots;
    private int workingHoursFrom;
    private int workingHoursTo;
    private int costPerHour;
    private long ownerId;

    public static ParkingDto valueFrom(Parking parking){
        ParkingDto parkingDto = new ParkingDto();
        parkingDto.setId(parking.getId());
        parkingDto.setCity(parking.getCity());
        parkingDto.setStreet(parking.getStreet());
        parkingDto.setStreetNumber(parking.getStreetNumber());
//        parkingDto.setNumberOfSpots(parking.getNumberOfSpots());
        parkingDto.setWorkingHoursFrom(parking.getWorkingHoursFrom());
        parkingDto.setWorkingHoursTo(parking.getWorkingHoursTo());
        parkingDto.setCostPerHour(parking.getCostPerHour());
        parkingDto.setOwnerId(parking.getOwner().getId());
        return parkingDto;
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public int getCostPerHour() {
        return costPerHour;
    }

    public void setCostPerHour(int costPerHour) {
        this.costPerHour = costPerHour;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
}
