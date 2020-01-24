package pw.react.backend.parklybackend.service;

import org.springframework.http.HttpHeaders;

import java.util.Optional;

public interface SecurityService {

    boolean isAuthorized(HttpHeaders headers);
    Optional<String> authenticateParkingOwner(HttpHeaders headers);
}
