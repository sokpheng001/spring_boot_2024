package online.hackpi.spring_boot.api.v1.user.repository;

import online.hackpi.spring_boot.api.v1.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findUsersByIsVerifiedAndIsDeleted(Boolean isVerified, Boolean isDeleted);
}
