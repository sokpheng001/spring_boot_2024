package online.hackpi.spring_boot.api.v1.user.repository;

import online.hackpi.spring_boot.api.v1.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findUsersByIsVerifiedAndIsDeleted(Boolean isVerified, Boolean isDeleted);
    Optional<User> findUserByName(String name);
    Optional<User> findUserByUserEmail(String email);
    Optional<User> findUserByUuidAndIsDeletedAndIsVerified(String uuid, boolean idDeleted, boolean isVerified);
}
