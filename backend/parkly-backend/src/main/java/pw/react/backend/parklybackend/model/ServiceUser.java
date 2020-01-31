package pw.react.backend.parklybackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "ServiceUser")
public class ServiceUser implements Serializable {

    public static ServiceUser EMPTY = new ServiceUser();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private long id;

    @Column(name = "userName")
    private String userName;

    @Column(name = "userToken")
    private String userToken;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
