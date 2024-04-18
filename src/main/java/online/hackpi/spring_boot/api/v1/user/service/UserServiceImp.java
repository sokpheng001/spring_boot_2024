package online.hackpi.spring_boot.api.v1.user.service;

import lombok.RequiredArgsConstructor;
import online.hackpi.spring_boot.api.v1.role.repository.RoleRepository;
import online.hackpi.spring_boot.api.v1.user.mapstruct.UserMapstruct;
import online.hackpi.spring_boot.api.v1.user.model.User;
import online.hackpi.spring_boot.api.v1.user.model.dto.UserDto;
import online.hackpi.spring_boot.api.v1.user.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapstruct userMapstruct;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDto insertNewUser(User user){
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        if(user.getRoles().isEmpty()){
            user.getRoles().add(roleRepository.findRoleByRoleName("USER"));
        }
        user.setUuid(UUID.randomUUID().toString());
        user.setIsDeleted(false);
        user.setIsVerified(false);

        return userMapstruct.mapFromUserToUserDto(userRepository.save(user));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapstruct.mapFromUserToUserDto(
//                find user if the isVerified equals true & isDeleted equals false
                 userRepository.findUsersByIsVerifiedAndIsDeleted(true,false)
        );
    }

    @Override
    public UserDto getUserByUuid(String uuid) {
        return userMapstruct.mapFromUserToUserDto(userRepository.findUserByUuidAndIsDeletedAndIsVerified(uuid,false, true).orElseThrow());
    }

    @Override
    public UserDto verifyUser(Boolean verifiedStatus, String email) {
        User user = userRepository.findUserByUserEmail(email).orElseThrow();
        user.setIsVerified(verifiedStatus);
        userRepository.save(user);
        return userMapstruct.mapFromUserToUserDto(user);
    }
}
