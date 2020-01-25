package pw.react.backend.parklybackend.dto;

public class PossibleParkingReservationDto {

    public static PossibleParkingReservationDto EMPTY = new PossibleParkingReservationDto();

    private long parkingId;
    private String city;
    private String street;
    private int streetNumber;
    private int totalCost;

    public PossibleParkingReservationDto(){ }

    public PossibleParkingReservationDto(long parkingId, String city, String street, int streetNumber, int totalCost){
        this.parkingId = parkingId;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
        this.totalCost = totalCost;
    }

    public long getParkingId() {
        return parkingId;
    }

    public void setParkingId(long parkingId) {
        this.parkingId = parkingId;
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

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
}
