package online.hackpi.spring_boot.api.v1.user;

import online.hackpi.spring_boot.api.v1.user.model.User;
import online.hackpi.spring_boot.api.v1.user.model.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto insertNewUser(User user);
    List<UserDto> getAllUsers();
}
