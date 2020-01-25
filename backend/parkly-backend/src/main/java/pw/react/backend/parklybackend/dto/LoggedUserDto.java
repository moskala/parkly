package pw.react.backend.parklybackend.dto;

public class LoggedUserDto {

    private String userToken;
    private long id;

    public static LoggedUserDto EMPTY = new LoggedUserDto();

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
