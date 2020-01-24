package pw.react.backend.parklybackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import pw.react.backend.parklybackend.appException.ResourceNotFoundException;
import pw.react.backend.parklybackend.dao.ParkingOwnerRepository;
import pw.react.backend.parklybackend.dao.ServiceUserRepository;
import pw.react.backend.parklybackend.model.ParkingOwner;
import pw.react.backend.parklybackend.model.ServiceUser;

import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    private static final String USER_NAME="user_name";
    private static final String USER_TOKEN="user_token";
    private static final String USER_EMAIL="email";
    private static final String USER_PASSWORD="password";


    private ServiceUserRepository serviceUserRepository;
    private ParkingOwnerRepository parkingOwnerRepository;


    SecurityServiceImpl() { }

    @Autowired
    SecurityServiceImpl(ServiceUserRepository repository, ParkingOwnerRepository parkingOwnerRepository) {
        this.serviceUserRepository = repository;
        this.parkingOwnerRepository = parkingOwnerRepository;
    }

    public boolean isAuthenticated(HttpHeaders headers) {

        if (headers.containsKey(USER_EMAIL) && headers.containsKey(USER_PASSWORD)) {
            String email = headers.getFirst(USER_EMAIL);
            String password = headers.getFirst(USER_PASSWORD);
            Optional<ParkingOwner> parkingOwner = parkingOwnerRepository.findByEmail(email);
            if (parkingOwner.isPresent() && parkingOwner.get().equals(password)) {
                return true;
            } else return false;
        }
        return false;
    }

    @Override
    public boolean isAuthorized(HttpHeaders headers) {
        if (headers == null) {
            return false;
        }
        if(!headers.containsKey(USER_NAME)) return false;
        Optional<ServiceUser> user = serviceUserRepository.findByUserName(headers.getFirst(USER_NAME));
        if(user.isPresent()) {
            return headers.containsKey(USER_TOKEN) && (user.get().getUserToken()).equals(headers.getFirst(USER_TOKEN));
        }
        else return false;
    }

    @Override
    public Optional<String> authenticateParkingOwner(HttpHeaders headers) {

        if(headers.containsKey(USER_NAME) && isAuthenticated(headers)){
            Optional<ServiceUser> user = serviceUserRepository.findByUserName(headers.getFirst(headers.getFirst(USER_NAME)));
            if(!user.isPresent()) throw new ResourceNotFoundException("Service User do not exists");
            return Optional.of(user.get().getUserToken());
        }
        return Optional.empty();
    }
}
