package pw.react.backend.parklybackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ParkingSpot")
public class ParkingSpot {

    public static ParkingSpot EMPTY = new ParkingSpot();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private long spotId;

    @NotNull
    @Column(name="spotNumber")
    private int spotNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parkingId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @NotNull
    private Parking parking;

    public long getSpotId() {
        return spotId;
    }

    public void setSpotId(long spotId) {
        this.spotId = spotId;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(int spotNumber) {
        this.spotNumber = spotNumber;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }
}
