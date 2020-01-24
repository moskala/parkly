package pw.react.backend.parklybackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import pw.react.backend.parklybackend.dao.UserRepository;
import pw.react.backend.parklybackend.model.User;

import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    private static final String SECURITY_HEADER = "security-header";
    private final String SECURITY_HEADER_VALUE = "secureParkly";

    private static final String USER_NAME="user_name";
    private static final String USER_TOKEN="user_token";

    private UserRepository userRepository;

    SecurityServiceImpl() { /*Needed only for initializing spy in unit tests*/}

    @Autowired
    SecurityServiceImpl(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public boolean isAuthenticated(HttpHeaders headers) {
        return true;
    }

    @Override
    public boolean isAuthorized(HttpHeaders headers) {
        if (headers == null) {
            return false;
        }
        if(!headers.containsKey(USER_NAME)) return false;
        Optional<User> user = userRepository.findByUserName(headers.getFirst(USER_NAME));
        if(user.isPresent()) {
            return headers.containsKey(USER_TOKEN) && (user.get().getUserToken()).equals(headers.getFirst(USER_TOKEN));
        }
        else return false;
    }
}
