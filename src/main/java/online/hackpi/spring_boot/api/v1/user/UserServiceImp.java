package online.hackpi.spring_boot.api.v1.user;

import lombok.RequiredArgsConstructor;
import online.hackpi.spring_boot.api.v1.role.repository.RoleRepository;
import online.hackpi.spring_boot.api.v1.user.mapstruct.UserMapstruct;
import online.hackpi.spring_boot.api.v1.user.model.User;
import online.hackpi.spring_boot.api.v1.user.model.dto.UserDto;
import online.hackpi.spring_boot.api.v1.user.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapstruct userMapstruct;
    @Override
    public User insertNewUser(User user) {
        if(user.getRoles().isEmpty()){
            user.getRoles().add(roleRepository.findRoleByRoleName("ROLE_USER"));
        }
        user.setUuid(UUID.randomUUID().toString());
        user.setIsDeleted(false);
        user.setIsVerified(false);
        return userRepository.save(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapstruct.mapFromUserToUserDto(
//                find user if the isVerified equals true & isDeleted equals false
                 userRepository.findUsersByIsVerifiedAndIsDeleted(true,false)
        );
    }
}
