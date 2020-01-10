package pw.react.backend.parklybackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Entity
@Table(name = "parkingOwner")

//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ParkingOwner implements Serializable {

    private static final long serialVersionUID = -6783504532088859179L;

    public static ParkingOwner EMPTY = new ParkingOwner();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Column(name = "firstName")
    private String firstName;
    @NotNull
    @Column(name = "lastName")
    private String lastName;
    @NotNull
    @Email
    @Column(name="email")
    private String email;
    @NotNull

    @Column(name = "phoneNumber")
    private long phoneNumber;
    @NotNull
    @Column(name="hashPassword")
    private String hashPassword;



    public void setId(long id) {
        this.id = id;
    }




    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

}
