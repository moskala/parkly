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
    private int number;
    private int spotsNumber;
    private int opens;
    private int closes;
    private int price;
    private long ownerId;

    public static ParkingDto valueFrom(Parking parking){
        ParkingDto parkingDto = new ParkingDto();
        parkingDto.setId(parking.getId());
        parkingDto.setCity(parking.getCity());
        parkingDto.setStreet(parking.getStreet());
        parkingDto.setNumber(parking.getStreetNumber());
        parkingDto.setOpens(parking.getWorkingHoursFrom());
        parkingDto.setCloses(parking.getWorkingHoursTo());
        parkingDto.setPrice(parking.getCostPerHour());
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

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSpotsNumber() {
        return spotsNumber;
    }

    public void setSpotsNumber(int spotsNumber) {
        this.spotsNumber = spotsNumber;
    }

    public int getOpens() {
        return opens;
    }

    public void setOpens(int opens) {
        this.opens = opens;
    }

    public int getCloses() {
        return closes;
    }

    public void setCloses(int closes) {
        this.closes = closes;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
