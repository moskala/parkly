package pw.react.backend.parklybackend.service;

import org.springframework.http.HttpHeaders;
import pw.react.backend.parklybackend.dto.LoggedUserDto;

import java.util.Optional;

public interface SecurityService {

    boolean isAuthorized(HttpHeaders headers);
    Optional<LoggedUserDto> authenticateParkingOwner(HttpHeaders headers);
    Optional<String> authorizeBookly(HttpHeaders headers);
}
