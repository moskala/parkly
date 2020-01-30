package pw.react.backend.parklybackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import pw.react.backend.parklybackend.appException.InvalidArgumentException;
import pw.react.backend.parklybackend.appException.ResourceNotFoundException;
import pw.react.backend.parklybackend.dao.ParkingOwnerRepository;
import pw.react.backend.parklybackend.dao.ServiceUserRepository;
import pw.react.backend.parklybackend.dto.LoggedUserDto;
import pw.react.backend.parklybackend.model.Parking;
import pw.react.backend.parklybackend.model.ParkingOwner;
import pw.react.backend.parklybackend.model.ServiceUser;

import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    private static final String USER_NAME="User-name";
    private static final String USER_TOKEN="User-token";
    private static final String USER_EMAIL="Email";
    private static final String USER_PASSWORD="Password";


    private ServiceUserRepository serviceUserRepository;
    private ParkingOwnerRepository parkingOwnerRepository;


    SecurityServiceImpl() { }

    @Autowired
    SecurityServiceImpl(ServiceUserRepository repository, ParkingOwnerRepository parkingOwnerRepository) {
        this.serviceUserRepository = repository;
        this.parkingOwnerRepository = parkingOwnerRepository;
    }

    @Override
    public boolean isAuthenticated(HttpHeaders headers) {

        if (headers.containsKey(USER_EMAIL) && headers.containsKey(USER_PASSWORD)) {
            String email = headers.getFirst(USER_EMAIL);
            String password = headers.getFirst(USER_PASSWORD);
            Optional<ParkingOwner> parkingOwner = parkingOwnerRepository.findByEmail(email);
            if (parkingOwner.isPresent() && parkingOwner.get().getPassword().equals(password)) {
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
    public Optional<LoggedUserDto> authenticateParkingOwner(HttpHeaders headers) {

        if(headers.containsKey(USER_NAME) && isAuthenticated(headers)){
            String user_name = headers.getFirst(USER_NAME);
            Optional<ServiceUser> user = serviceUserRepository.findByUserName(user_name);
            if(!user.isPresent()) throw new ResourceNotFoundException("Service User do not exists");
            Optional<ParkingOwner> parkingOwner = parkingOwnerRepository.findByEmail(headers.getFirst(USER_EMAIL));
            LoggedUserDto dto = new LoggedUserDto();
            dto.setId(parkingOwner.get().getId());
            dto.setUserToken(user.get().getUserToken());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> authorizeBookly(HttpHeaders headers) {

        if(!headers.containsKey(USER_NAME)) Optional.empty();

        Optional<ServiceUser> user = serviceUserRepository.findByUserName(headers.getFirst(USER_NAME));
        if(user.isPresent()) {
            return Optional.of(user.get().getUserToken());
        }
        else return Optional.empty();
    }
}
