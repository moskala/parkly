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
}