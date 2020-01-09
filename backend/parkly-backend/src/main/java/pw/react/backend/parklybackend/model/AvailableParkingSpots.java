package pw.react.backend.parklybackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "AvailableParkingSpots")
public class AvailableParkingSpots {

    public static Parking EMPTY = new Parking();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private long id;

    @NotNull
    @Column(name="spotNumber")
    private int spotNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parking_FK", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @NotNull
    private Parking parking;    //czy trzeba getter i setter?

    @Column(name="dateFrom")
    private Date dateFrom;

    @Column (name="dateTo")
    private Date dateTo;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(int spotNumber) {
        this.spotNumber = spotNumber;
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
}
