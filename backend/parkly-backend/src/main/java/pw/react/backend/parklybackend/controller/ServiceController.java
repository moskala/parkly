package pw.react.backend.parklybackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.react.backend.parklybackend.service.SecurityService;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "/service")
public class ServiceController {

    private SecurityService securityService;

    @Autowired
    public ServiceController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping(path = "/login")
    public ResponseEntity<String> loginService(@RequestHeader HttpHeaders headers) {

        Optional<String> token = securityService.authorizeBookly(headers);
        if(token.isPresent()){
            return ResponseEntity.ok(token.get());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED SERVICE");
    }
}
