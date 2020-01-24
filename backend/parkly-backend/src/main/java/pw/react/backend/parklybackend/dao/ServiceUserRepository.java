package pw.react.backend.parklybackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.parklybackend.model.ServiceUser;

import java.util.Optional;

public interface ServiceUserRepository extends JpaRepository<ServiceUser, Long>{

    Optional<ServiceUser> findByUserName(String userName);
}