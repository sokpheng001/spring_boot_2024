package online.hackpi.spring_boot.api.v1.user.service;

import online.hackpi.spring_boot.api.v1.user.model.User;
import online.hackpi.spring_boot.api.v1.user.model.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto insertNewUser(User user);
    List<UserDto> getAllUsers();
    UserDto getUserByUuid(String uuid);
    UserDto verifyUser(Boolean verifiedStatus, String email);
}
