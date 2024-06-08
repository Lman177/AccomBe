package usth.edu.accommodationbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import usth.edu.accommodationbooking.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    void deleteByEmail(String email);

    Optional<User> findByEmail(String email);

}