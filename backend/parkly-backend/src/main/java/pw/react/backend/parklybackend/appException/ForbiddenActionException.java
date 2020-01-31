package pw.react.backend.parklybackend.appException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ForbiddenActionException extends RuntimeException  {
    public ForbiddenActionException(String message) {
        super(message);
    }

}
