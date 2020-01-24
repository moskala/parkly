package pw.react.backend.parklybackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.parklybackend.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUserName(String userName);
}